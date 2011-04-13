package org.tagaprice.shared.rpc.categorymanagement;

import java.util.List;

import org.tagaprice.shared.entities.categorymanagement.Category;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ICategoryServiceAsync {

	void getCategory(String id, String revision, AsyncCallback<Category> callback);

	void getCategory(String id, AsyncCallback<Category> callback);

	void getCategoryChilds(String id, AsyncCallback<List<Category>> callback);
}
