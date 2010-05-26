/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: tagaprice
 * Filename: JsonSerializer.java
 * Date: 19.05.2010
*/
package org.tagaprice.server.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.tagaprice.shared.Currency;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyList;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.serializer.Serializer;

public class JsonSerializer extends Serializer {
	private OutputStream out;
	private boolean firstElem = true;
	
	public JsonSerializer(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void put(Currency currency) throws IOException {
		writeHead();
		
		writeVar("id", currency.getId(), true);
		writeVar("name", currency.getName(), true);
		
		writeTail();
	}

	@Override
	public void put(Price price) throws IOException {
		writeHead();
		
		writeVar("price", price.getPrice(), true);
		writeVar("currency", price.getCurrency(), true);
		
		writeTail();
	}
	
	@Override
	public void put(ProductData product) throws IOException {
		writeHead();
		
		writeVar("id", product.getId(), true);
		writeVar("name", product.getName(), true);
		writeVar("brandId", product.getBrandId(), false);
		writeVar("typeId", product.getTypeId(), true);
		writeVar("imageSrc", product.getImageSrc(), true);
		writeVar("price", product.getPrice(), true);
		writeVar("quantity", product.getQuantity(), false);
		writeVar("rating", product.getRating(), false);
		writeVar("progress", product.getProgress(), true);
		writeVar("hasReceipt", product.hasReceipt(), true);

		/// TODO introduce the corresponding EntitySerializer-function
		writeVar("properties", product.getProperties(), false);
		
		writeTail();
	}
	
	@Override
	public void put(PropertyData property) throws IOException {
		writeHead();
		writeVar("name", property.getName(), true);
		writeVar("title", property.getTitle(), true);
		writeVar("value", property.getValue(), true);
		writeVar("unit", property.getUnit(), false);
		writeTail();
	}
	
	@Override
	public void put(PropertyList propertyList) throws IOException {
		Iterator<PropertyData> it = propertyList.iterator();
		
		writeListHead();
		while (it.hasNext()) {
			put(it.next());
		}
		writeListTail();
	}

	@Override
	public void put(Quantity quantity) throws IOException {
		writeHead();
		writeVar("quantity", quantity.getQuantity(), true);
		writeVar("unit", quantity.getUnit(), true);
		writeTail();
	}

	@Override
	public void put(ReceiptData receipt) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void put(ServerResponse response) throws IOException {
		writeHead();
		writeVar("status", response.getStatusName(), true);
		if (response.getResponse() != null) {
			writeVar("type", response.getResponse().getSerializeName(), true);
			writeVar("response", response.getResponse(), false);
		}
		writeTail();
	}
	
	@Override
	public void put(ShopData shop) throws IOException {
		writeHead();
		writeVar("id", shop.getId(), true);
		writeVar("name", shop.getName(), true);
		writeVar("rating", shop.getRating(), false);
		writeVar("progress", shop.getProgress(), true);
		// TODO add other properties
		writeVar("properties", shop.getProperties(), false);
		writeTail();
	}

	@Override
	public void put(Unit unit) throws IOException {
		writeHead();
		writeVar("id", unit.getId(), true);
		writeVar("name", unit.getName(), true);
		
		if (unit.getFallbackId() > 0) {
			writeVar("siUnit", unit.getFallbackId(), true);
			writeVar("factor", unit.getFactor(), true);
		}
		writeTail();
	}
	
	protected void writeHead() throws IOException {
		out.write("{\n".getBytes());
		firstElem = true;
	}
	
	protected void writeTail() throws IOException {
		out.write("\n}".getBytes());
		firstElem = false;
	}
	
	protected void writeListHead() throws IOException {
		out.write("[\n".getBytes());
		firstElem = true;
	}
	
	protected void writeListTail() throws IOException {
		out.write("\n]".getBytes());
		firstElem = false;
	}
	
	private void writeVarName(String name) throws IOException {
		if (!firstElem) out.write(",\n".getBytes());
		out.write("\"".getBytes());
		out.write(name.getBytes());
		out.write("\": ".getBytes());
		firstElem = false;
	}
	
	protected void writeVar(String name, String value, boolean writeIfNull) throws IOException {
		if (writeIfNull || value != null) {
			writeVarName(name);
			if (value != null) {
				out.write("\"".getBytes());
				out.write(value.getBytes());
				out.write("\"".getBytes());
			}
			else out.write("null".getBytes());
		}
	}
	
	protected void writeVar(String name, long value, boolean writeIfNeg) throws IOException {
		if (writeIfNeg || value >= 0) {
			writeVarName(name);
			out.write(new Long(value).toString().getBytes());
		}
	}
	
	protected void writeVar(String name, double value, boolean writeIfNaN) throws IOException {
		if (writeIfNaN || value != Double.NaN) {
			writeVarName(name);
			out.write(new Double(value).toString().getBytes());
		}
	}
	
	protected void writeVar(String name, boolean value, boolean writeIfFalse) throws IOException {
		if (writeIfFalse || value) {
			writeVarName(name);
			out.write((value ? "true" : "false").getBytes());
		}
	}
	
	protected void writeVar(String name, Serializable value, boolean writeIfNull) throws IOException {
		if (writeIfNull || value != null) {
			writeVarName(name);
			if (value != null) putAny(value);
			else out.write("null".getBytes());
		}
	}
}
