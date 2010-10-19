package org.tagaprice.server.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.Mail;
import org.tagaprice.shared.LocalAccountData;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class LocalAccountDAO implements DAOClass<LocalAccountData> {
	private DBConnection db;
	private AccountDAO accountDAO;
	
	public LocalAccountDAO(DBConnection db) {
		this.db=db;
		accountDAO = new AccountDAO(db);
	}
	
	public boolean isEmailAvailable(String email) throws SQLException, NotFoundException, NotFoundException{
		if(!email.trim().matches(".+@.+\\.[a-z]+")){
			return false;
		}
		
		
		String sql = "SELECT *  FROM account WHERE (mail = ?)";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setString(1, email);
		ResultSet res = pstmt.executeQuery();
		
		if(!res.next()){
			return true;
		}
		return false;
	}
	
	public boolean isUsernameAvailable(String username) throws SQLException, NotFoundException, NotFoundException {
		String sql = "" +
				"SELECT * FROM account " +
				"INNER JOIN entity " +
				"ON (entity.ent_id = account.uid) " +
				"INNER JOIN entityrevision " +
				"ON (entity.current_revision = entityrevision.rev " +
				"AND entity.ent_id = entityrevision.ent_id) " +
				"WHERE (entityrevision.title = ?)";
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setString(1, username);
		ResultSet res = pstmt.executeQuery();
		
		if(!res.next()){
			return true;
		}
		return false;
	}
	
	public boolean confirm(String confirm) throws SQLException, NotFoundException, NotFoundException{
		String sql = "" +
				"UPDATE account " +
				"SET locked='false' " +
				"WHERE (uid = " +
					"(SELECT uid FROM confirmaccount " +
					"WHERE (confirm=?) " +
					"AND (confirm_date BETWEEN (NOW() - INTERVAL '1 day') AND NOW())))";
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setString(1, confirm);	
		
		if(pstmt.executeUpdate()==1) 
			return true;
		
		return false;
	}
	
	
	@Override
	public void get(LocalAccountData account) throws SQLException, NotFoundException {
		// password won't be set anyway
		accountDAO.get(account);
	}

	@Override
	public void save(LocalAccountData account) throws SQLException,
			NotFoundException, RevisionCheckException, InvalidLocaleException {
		accountDAO.save(account);
		
		if (account.getRev() == 1) {
			PreparedStatement pstmt = db.prepareStatement("INSERT INTO localAccount (uid, password, salt) VALUES (?, md5(?||?), ?)");
			String salt = LoginDAO.generateSalt(10);
			pstmt.setLong(1, account.getId());
			pstmt.setString(2, account.getPassword());
			pstmt.setString(3, salt);
			pstmt.setString(4, salt);
			pstmt.executeUpdate();
			
			//Add confirmHash
			PreparedStatement pstmt2 = db.prepareStatement("INSERT INTO confirmAccount (uid, confirm) VALUES (?, md5(?))");
			String confirmationString = LoginDAO.generateSalt(10);
			pstmt2.setLong(1, account.getId());
			pstmt2.setString(2, confirmationString);
			pstmt2.executeUpdate();
			
			// send confirmation mail
			// TODO the confirmation mail shoudln't be sent from a DAO class (or should it?)
			String msg = "You've just registered at tagaprice.org\n"
				+"Please open the following link to confirm your registration"
				+"http://tagaprice.org/#user/confirm/"+confirmationString;
			
			try {
				HashMap<String, String> replacements = new HashMap<String, String>();
				replacements.put("confirmId", confirmationString);
				Mail.getInstance().send("regConfirm", new InternetAddress(account.getMail()), new HashMap<String, String>());
			}
			catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (account.getRev() < 1) throw new RevisionCheckException("invalid revision: "+account.getRev());
		else if (account.getPassword() != null) {
			// TODO save the password (if changed)
			PreparedStatement pstmt = db.prepareStatement("UPDATE localAccount SET password = md5(?||salt) WHERE uid = ?);");
			pstmt.setString(1, account.getPassword());
			pstmt.setLong(2, account.getId());
			pstmt.executeUpdate();
		}
	}
}
