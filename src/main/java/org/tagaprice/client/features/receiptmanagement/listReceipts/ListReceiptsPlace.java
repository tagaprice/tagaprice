package org.tagaprice.client.gwt.client.features.receiptmanagement.listReceipts;

import org.tagaprice.client.gwt.client.generics.TokenCreator;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ListReceiptsPlace extends Place {
	private static final MyLogger _logger = LoggerFactory.getLogger(ListReceiptsPlace.class);

	public ListReceiptsPlace() {
		// TODO Auto-generated constructor stub
	}

	@Prefix("ListReceipts")
	public static class Tokenizer implements PlaceTokenizer<ListReceiptsPlace>{

		@Override
		public ListReceiptsPlace getPlace(String token) {
			ListReceiptsPlace._logger.log("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);
			if(e.getRoot().equals("show")){
				return new ListReceiptsPlace();
			}
			return null;
		}

		@Override
		public String getToken(ListReceiptsPlace place) {
			ListReceiptsPlace._logger.log("Tokenizer show receipts: id="+place);

			TokenCreator.Imploder t = TokenCreator.getImploder();
			t.setRoot("show");

			return t.getToken();
		}

	}
}
