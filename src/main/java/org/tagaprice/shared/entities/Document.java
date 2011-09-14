package org.tagaprice.shared.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.svenson.DynamicProperties;
import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * The {@link Document} class is used to FIND, CREATE and UPDATE different documents. It contains two constructors, one
 * for FIND and one for CREATE an {@link Document}.
 * If you like to UPDATE a {@link Document} you have to FIND a {@link Document} first. The server will set the
 * {@link User} and the RevisionID.
 * Don't change the RevisionID on the client (by hand). Only the server will change it by an UPDATE.
 * 
 */
public class Document implements IsSerializable, DynamicProperties {
	private static final long serialVersionUID = 1L;

	public static enum Type {
		PRODUCT("product"),
		SHOP("shop"),
		PRODUCTCATEGORY("productCategory"),
		SHOPCATEGORY("shopCategory"),
		RECEIPT("receipt"),
		PACKAGE("package"),
		SESSION("session"),
		UNIT("unit"),
		USER("user");

		private final String typeName;

		Type(String value) {
			typeName = value;
		}

		public String getTypeName() {
			return typeName;
		}

		@Override
		public String toString() {
			return getTypeName();
		}
	}

	private String _id = null;
	private String _rev = null;
	private Type _docType = null;
	private String _creatorId = null;
	private Map<String, Object> _properties = new HashMap<String, Object>();
	private Address _address = new Address();
	private ArrayList<Package> _iPackage = new ArrayList<Package>();


	private String _title;

	/// TODO make a dummy RPC class to cover all those types
	@SuppressWarnings("unused")
	private Long _unusedLong = null; // required to allow Long serialization with GWT-RPC
	@SuppressWarnings("unused")
	private BigDecimal _unusedBigDecimal = null;
	@SuppressWarnings("unused")
	private Boolean _unusedBoolean = null;


	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Document() {
	}

	/**
	 * <b>UPDATE and GET</b>
	 * This constructor is used by the server to fetch a {@link Document} after SAVING or FINDING a {@link Document}.
	 * @param creator Creator of the current document revision 
	 * @param id Unique EntityID
	 * @param title The title of the {@link Document}. It must not be null.
	 */
	public Document(String creatorId, String id, String revision, String title) {
		_creatorId = creatorId;
		setId(id);
		setRevision(revision);
		setTitle(title);
	}

	/**
	 * <b>NEW</b>
	 * Is used to create a new {@link Document}
	 * 
	 * @param creator Creator of the current document revision 
	 * @param title
	 *            The title of the {@link Document}. Every {@link Document} needs a title. It must not be null.
	 */
	public Document(String creatorId, String title) {
		this(creatorId, null, null, title);
	}

	/**
	 * Returns the creator of the Entity's current revision
	 * @return This revision's creator UID
	 */
	public String getCreatorId() {
		return _creatorId;
	}

	/**
	 * Get the type name of the entity. This is required for the CouchDB DAO to work.
	 * Just ignore it anywhere else in the application.
	 * @return
	 */
	public String getDocType() {
		return _docType != null ? _docType.toString() : null;
	}

	@JSONProperty(ignore=true)
	public Type getDocTypeEnum() {
		return _docType;
	}
	/**
	 * Set the {@link ASimpleEntity} Id
	 * @return  {@link ASimpleEntity} Id
	 */
	@JSONProperty(value="_id", ignoreIfNull = true)
	public String getId() {
		return _id;
	}
	
	public Object getProperty(String name) {
		return _properties.get(name);
	}

	@JSONProperty(ignore = true)
	public Map<String, Object> getPropertyList(){
		return _properties;
	}

	/**
	 * Returns the Revision of the {@link ASimpleEntity} or null, if it wasn't yet set (e.g. for unsaved {@link ASimpleEntity})
	 * @return Returns the Revision of the {@link ASimpleEntity} or null, if Revision is not set.
	 */
	@JSONProperty(value="_rev", ignoreIfNull = true)
	public String getRevision() {
		return _rev;
	}

	/**
	 * Returns the title
	 * @return the title
	 */
	@JSONProperty(value="title", ignoreIfNull=true)
	public String getTitle() {
		return _title;
	}

	public Set<String> propertyNames() {
		return _properties.keySet();
	}

	/**
	 * Set the document's creator ID
	 * @param creatorId UID of the current revision's creator
	 */
	public void setCreatorId(String creatorId) {
		_creatorId = creatorId;
	}

	/**
	 * This method is required for the CouchDB JSON injector to work.
	 * Just ignore it anywhere else in the application.
	 * 
	 * @param typeName new document Type
	 */
	public void _setDocType(Type type) {
		_docType = type;
	}

	public void setDocType(String typeName) {
		_docType = Type.valueOf(typeName.toUpperCase());
	}

	/**
	 * Set the Entity ID
	 * @param entityId Entity ID
	 */
	public void setId(String entityId) {
		_id = entityId;
	}

	public void setProperty(String name, Object value) {
		_properties.put(name, value);
	}

	/**
	 * Set the Entity revision
	 * @param revision Revision ID
	 */
	public void setRevision(String revision) {
		_rev=revision;
	}

	/**
	 * Set the title
	 * @param title title
	 */
	public void setTitle(String title){
		_title = title;
	}
	
	/**
	 * Set an {@link Address} to the {@link Shop}
	 * @param address {@link Shop} address
	 */
	@JSONProperty(ignore = true)
	public void setAddress(Address address) {
		_address=address;
	}

	/**
	 * @return the address of the {@link Shop}
	 */
	@JSONProperty(ignore = true)
	public Address getAddress() {
		return _address;
	}
	
	/**
	 * Set some {@link Package} to the Product. All included products will be deleted.
	 * 
	 * @param ipackage
	 *            that will be set to the {@link Product}
	 */
	@JSONProperty(ignore = true)
	public void setPackages(List<Package> iPackage) {
		_iPackage.clear();
		for (Package p : iPackage) {
			/*
			if(getDocType().equals("product"))
				p.setProduct(Product.fromDocument(this));
			
			*/
			_iPackage.add(p);
		}

	}
	
	
	/**
	 * Add one {@link Package} to the Product.
	 * 
	 * @param ipackage
	 *            that will be added to the {@link Product}
	 */
	@JSONProperty(ignore = true)
	public void addPackage(Package ipackage) {
		/*
		if(getDocType().equals("product"))
			ipackage.setProduct(Product.fromDocument(this));
		*/
		
		_iPackage.add(ipackage);
	}
	
	/**
	 * Return all Packages includes in a {@link Product}
	 * 
	 * @return all Packages includes in a {@link Product}
	 */
	@JSONProperty(ignore = true)
	public ArrayList<Package> getPackages() {
		return _iPackage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ADocument [id: " + _id + ", rev: "+_rev+", title=" + _title + "]";
	}


	@Override
	public boolean equals(Object otherObject) {
		boolean rc = true;

		if (otherObject instanceof Document) {
			Document other = (Document) otherObject;
			if (!_equals(_id, other._id)) rc = false;
			else if (!_equals(_rev, other._rev)) rc = false;
			else if (!_equals(_creatorId, other._creatorId)) rc = false;
			else if (!_equals(_title, other._title)) rc = false;
		}
		else rc = false;
		return rc;
	}

	/**
	 * Convenience function that checks two objects for equality (and can handle null references)
	 * @param a First object to compare
	 * @param b Second object to compare
	 * @return False if exactly one of them is null, true if both are null, a.equals(b) otherwise 
	 */
	protected static boolean _equals(Object a, Object b) {
		boolean rc = false;
		if (a == null) rc = b == null;
		else rc = a.equals(b);
		return rc;
	}

	protected static boolean _equalArrays(List<?> a, List<?> b) {
		boolean rc = true;

		if ((a == null || b == null) && a != b) rc = false;
		if (a.size() != b.size()) rc = false;
		else {
			for (int i = 0; rc && i < a.size(); i++) {
				rc = _equals(a.get(i), b.get(i));
			}
		}

		return rc;
	}


}
