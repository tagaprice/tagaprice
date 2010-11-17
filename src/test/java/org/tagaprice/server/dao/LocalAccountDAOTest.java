package org.tagaprice.server.dao;


import static org.junit.Assert.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.EntityDAOTest.TestDBConnection;
import org.tagaprice.server.dao.postgres.LocalAccountDAO;
import org.tagaprice.server.dao.postgres.LocaleDAO;
import org.tagaprice.shared.entities.LocalAccount;

public class LocalAccountDAOTest {
	private DBConnection db;
	private LocalAccountDAO dao;
	private int localeId;
	private final String pwd = "my secret password";

	@Before
	public void setUp() throws Exception {
		db = new TestDBConnection();
		dao = new LocalAccountDAO(db);
		localeId = new LocaleDAO(db).get("English").getId();
	}

	@After
	public void tearDown() throws Exception {
		db.forceRollback();
	}
	
	@Test
	public void testCreate() throws Exception {
		LocalAccount account = new LocalAccount("testAccount", localeId, null, "mail@foo.invalid", pwd, null);
		dao.save(account);
		LocalAccount a2 = new LocalAccount(account.getId());
		dao.get(a2);
		assertEquals("Accounts don't match!", account, a2);
		
		// check if the password is saved correctly
		PreparedStatement pstmt = db.prepareStatement("SELECT password, salt FROM localAccount WHERE uid = ?");
		pstmt.setLong(1, account.getId());
		ResultSet res = pstmt.executeQuery();
		assertTrue("Error: localAccount wasn't saved!", res.next());
		
		String salt = res.getString("salt");
		String pwdHash = res.getString("password");

		System.out.println("Pwd: "+pwd+"\nSalt: "+salt+"\nHash: "+pwdHash);
        assertEquals(md5(pwd+salt), pwdHash);

	}

	public static String md5(String in) throws NoSuchAlgorithmException {
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
