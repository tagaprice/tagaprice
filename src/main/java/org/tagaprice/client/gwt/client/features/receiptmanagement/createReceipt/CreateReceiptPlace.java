package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt;

import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CreateReceiptPlace extends Place{
	MyLogger logger = LoggerFactory.getLogger(CreateReceiptPlace.class);

	public CreateReceiptPlace() {

	}

	@Prefix("CreateReceipt")
	public static class Tokenizer implements PlaceTokenizer<CreateReceiptPlace> {
		MyLogger logger = LoggerFactory.getLogger(Tokenizer.class);

		@Override
		public CreateReceiptPlace getPlace(String token) {
			logger.log("return CreateReceiptPlace for token " + token);
			return new CreateReceiptPlace();
		}

		@Override
		public String getToken(CreateReceiptPlace place) {
			return "";
		}

	}
}
