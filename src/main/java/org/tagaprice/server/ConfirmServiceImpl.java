package org.tagaprice.server;

import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IUserDao;
import org.tagaprice.server.rpc.InitServlet;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ConfirmServiceImpl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IUserDao _userDao;
	
	public ConfirmServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		
		_userDao = daoFactory.getUserDao();
	
	}
	
	
	protected void doGet(javax.servlet.http.HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException ,java.io.IOException {

		String info="";
		PrintWriter out = response.getWriter();
		String conf = request.getParameter("conf").trim();
		String mail = request.getParameter("mail").trim();
		
		if(!conf.isEmpty() && !mail.isEmpty()){
			User user = _userDao.getByMail(mail);
			
			if(user!=null){
				if(user.isConfirmed()==false){
					try {
						if(conf.equals(md5(mail+user.getConfirmSalt()))){
							user.setConfirmed(true);
							_userDao.update(user);
								//info = "You are now registered. Please go back to your (open) TagAPrice window.";
								response.sendRedirect("#start:/null/redirect/true");
							return;
						}else{
							info="something went wrong";
						}
					} catch (NoSuchAlgorithmException e) {
						info="no";
					} catch (DaoException e) {
						info="no";
					}
				}else{
					info="You are now registered. Please go back to your (open) TagAPrice window.";
				}
			}else{
				out.print("something went wrong");
			}
			
			
		}else{
			out.print("something went wrong");
		}
		
		
		out.print(info);
		
	};
	
	public String md5(String in) throws NoSuchAlgorithmException {
		// calculate hash
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(in.getBytes());
		byte[] hash = md5.digest();
		StringBuffer rc = new StringBuffer();
		for (int i=0; i<hash.length; i++) {
			String hex = "0"+Integer.toHexString(0xFF & hash[i]);
			if (hex.length()>2) hex = hex.substring(hex.length()-2);
			rc.append(hex);
		}

		return rc.toString();
	}
}
