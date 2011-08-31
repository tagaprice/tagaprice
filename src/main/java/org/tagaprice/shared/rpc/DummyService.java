package org.tagaprice.shared.rpc;

import java.util.HashMap;

import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("dummyservice")
public interface DummyService extends RemoteService {
  void dummyMethode(Boolean bool, boolean bool2, ReceiptEntry eceiptEntry, HashMap<String, String> dummyMap);
}
