package org.tagaprice.shared.entities;

public class LocalAccount extends Account {

	/**
	 * Contains all important information to represent a
	 * LocalAccount. 
	 */
	private static final long serialVersionUID = 1L;
	private String password;
	
	/**
	 * default constructor (used for serialization)
	 */
	public LocalAccount() {
		super();
	}
	
	
	/**
	 * constructor for querying a LocalAccount's current revision 
	 * @param id Product ID
	 */
	public LocalAccount(long id) {
		super(id);
	}
	
	/**
	 * query a specific LocalAccount revision
	 * @param id user ID
	 * @param rev revision
	 */
	public LocalAccount(long id, int rev) {
		super(id, rev);
	}
	
	
	/**
	 * constructor for creating a new LocalAccount
	 * @param title
	 * @param localeId
	 * @param creatorId
	 * @param password
	 * @param email
	 * @param language
	 * @param street
	 * @param zip
	 * @param county
	 * @param country
	 * @param latitude
	 * @param longitude
	 */
	public LocalAccount(
			String title, 
			int localeId,
			Long creatorId, 
			String mail,
			String password,
			Address address) {
		super(title, localeId, mail, address);
		
		this.password = password;
	}
	
	/**
	 * constructor for saving existing Project
	 * @param id
	 * @param rev
	 * @param title
	 * @param creatorId
	 * @param password
	 * @param email
	 * @param language
	 * @param street
	 * @param zip
	 * @param county
	 * @param country
	 * @param latitude
	 * @param longitude
	 */
	public LocalAccount(
			long id, 
			int rev,
			String title, 
			long revCreatorId,
			String mail, 
			String password, 
			Address address){
		super(id, rev, title, revCreatorId, mail, address);	
		
		this.password = password;
	}


	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = true;
		
		if (o instanceof LocalAccount) {
			LocalAccount a = (LocalAccount) o;
			if (!super.equals(a)) rc = false;
		}
		else rc = false;
		
		return rc;
	}

	@Override
	public String getSerializeName() {
		return "localAccount";
	}
	
	
}
