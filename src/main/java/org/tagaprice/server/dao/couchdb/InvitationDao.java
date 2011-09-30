package org.tagaprice.server.dao.couchdb;

import java.util.Calendar;
import java.util.Date;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Server;
import org.jcouchdb.document.BaseDocument;
import org.jcouchdb.exception.NotFoundException;
import org.svenson.JSONProperty;
import org.tagaprice.server.rpc.LoginServiceImpl;
import org.tagaprice.shared.entities.accountmanagement.User;

public class InvitationDao {
	public static class Invitation extends BaseDocument {
		private String m_requesterId = null;
		private Date m_requestTimestamp = null;
		private String m_userId = null;
		private Date m_useTimestamp = null;

		public Invitation(String code, String requesterId) {
			m_requesterId = requesterId;
			m_requestTimestamp = Calendar.getInstance().getTime();
			setId("inv_"+code);
		}
		
		public Invitation() {}
		
		@JSONProperty(ignoreIfNull=true)
		public String getRequestorId() {
			return m_requesterId;
		}
		
		@JSONProperty(ignoreIfNull=true)
		public Date getRequestTimestamp() {
			return m_requestTimestamp;
		}
		
		@JSONProperty(ignoreIfNull=true)
		public String getUserId() {
			return m_userId;
		}
		
		@JSONProperty(ignoreIfNull=true)
		public Date getUseTimestamp() {
			return m_useTimestamp;
		}
		
		@JSONProperty(ignore=true)
		public boolean isAlreadyUsed() {
			return m_userId != null;
		}


		public void setRequesterId(String requesterId) {
			m_requesterId = requesterId;
		}
		
		public void setRequestTimestamp(Date timestamp) {
			m_requestTimestamp = timestamp;
		}
		
		public void setUserId(String userId) {
			m_userId = userId;
		}
		
		public void setUseTimestamp(Date timestamp) {
			m_useTimestamp = timestamp;
		}

		
		public String getDocType() {
			return "invitation";
		}
		
		public void setDocType(String docType) {
			if (!"invitation".equals(docType)) {
				throw new RuntimeException("Not an invitation document!");
			}
		}
		
	}
	
	Database m_db;
	
	public InvitationDao(CouchDbConfig config) {
		config = config.getStatisticsConfig();
		
		Server server = CouchDbDaoFactory.getServerObject(config);

		m_db = new Database(server, config.getStatisticsDb());
	}
	
	/**
	 * Returns the requested Invitation if it hasn't already been used 
	 * @param key Invitation code
	 * @return Invitation or null if it wasn't found or has already been used
	 */
	private Invitation getInvitation(String key) {
		Invitation rc = null;
		
		try {
			rc = m_db.getDocument(Invitation.class, "inv_"+key);
			if (rc.isAlreadyUsed()) rc = null;
		}
		catch (NotFoundException e) {
			// rc = null; // it already is null
		}
		
		return rc;
	}
	
	/**
	 * Check an invitation code and return true if it is usable
	 * @param key Invitation code
	 * @return True if the key is usable, false otherwise (invalid or already used)
	 */
	public boolean checkKey(String key) {
		return getInvitation(key) != null;
	}
	
	/**
	 * Mark the given invitation code used by <i>user</i> 
	 * @param key Invitation code
	 * @param user User that activated his/her profile with the given key
	 * @return True if the key was used successfully, false if it was invalid or has already been used
	 */
	public boolean useKey(String key, User user) {
		Invitation invitation = getInvitation(key);
		boolean rc = false;
		if (invitation != null) {
			invitation.setUserId(user.getId());
			invitation.setUseTimestamp(Calendar.getInstance().getTime());

			m_db.updateDocument(invitation);
		}
		return rc;
	}
	
	/**
	 * Create an invitation code that the given user can use to invite other people
	 * @param user User that issues the invitation code
	 * @return Invitation code
	 */
	public String generateKey(User user) {
		String code = LoginServiceImpl.generateSalt(8);
		
		Invitation invitation = new Invitation(code, user.getId());
		m_db.createDocument(invitation);
		
		return code;
	}
}
