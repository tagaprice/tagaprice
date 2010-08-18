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
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.RequestError;
import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;

public class JsonSerializer extends Serializer {
	private OutputStream out;
	private boolean firstElem = true;
	
	public JsonSerializer(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void put(Currency currency, boolean annotateType) throws IOException {
		writeHead();
		
		writeEntity(currency);

		writeType(currency, annotateType);
		
		writeTail();
	}
	
	@Override
	public <T extends Serializable> void put(SearchResult<T> list, boolean annotateType) throws IOException {
		Iterator<T> it = list.iterator();
		writeListHead();

		while (it.hasNext()) {
			T p = it.next();
			writeListElem(p, true);
		}

		writeType(list, annotateType);

		writeListTail();
	}

	@Override
	public void put(Price price, boolean annotateType) throws IOException {
		writeHead();
		
		writeVar("price", price.getPrice(), true);
		writeVar("currency", price.getCurrency(), true, annotateType);
		
		writeType(price, annotateType);

		writeTail();
	}
	
	@Override
	public void put(ProductData product, boolean annotateType) throws IOException {
		writeHead();
		
		writeEntity(product);
		if (product.getBrandId() != null) writeVar("brandId", product.getBrandId(), false);
		writeVar("typeId", product.getTypeId(), true);
		writeVar("imageSrc", product.getImageSrc(), true);
		writeVar("price", product.getAvgPrice(), true, annotateType);
		writeVar("quantity", product.getQuantity(), false, annotateType);
		writeVar("rating", product.getRating(), false);
		writeVar("progress", product.getProgress(), true);
		writeVar("hasReceipt", product.hasReceipt(), true);

		/// TODO introduce the corresponding EntitySerializer-function
		writeVar("properties", product.getProperties(), false, annotateType);

		writeType(product, annotateType);

		writeTail();
	}
	
	@Override
	public void put(PropertyData property, boolean annotateType) throws IOException {
		writeHead();
		writeVar("name", property.getName(), true);
		writeVar("value", property.getValue(), true);
		writeVar("unit", property.getUnit(), false, annotateType);

		writeType(property, annotateType);

		writeTail();
	}

	@Override
	public void put(Quantity quantity, boolean annotateType) throws IOException {
		writeHead();
		writeVar("quantity", quantity.getQuantity(), true);
		writeVar("unit", quantity.getUnit(), true, annotateType);

		writeType(quantity, annotateType);

		writeTail();
	}

	@Override
	public void put(ReceiptData receipt, boolean annotateType) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void put(RequestError error, boolean annotateType)  throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void put(ServerResponse response, boolean annotateType) throws IOException {
		writeHead();
		writeVar("status", response.getStatusName(), true);
		if (response.getResponse() != null) {
			writeVar("type", response.getResponse().getSerializeName(), true);
			writeVar("response", response.getResponse(), false, annotateType);
		}

		writeType(response, annotateType);

		writeTail();
	}
	
	@Override
	public void put(ShopData shop, boolean annotateType) throws IOException {
		writeHead();
		writeEntity(shop);
		writeVar("rating", shop.getRating(), false);
		writeVar("progress", shop.getProgress(), true);
		// TODO add other properties
		writeVar("properties", shop.getProperties(), false, annotateType);

		writeType(shop, annotateType);

		writeTail();
	}

	@Override
	public void put(Unit unit, boolean annotateType) throws IOException {
		writeHead();
		writeEntity(unit);
		
		if (unit.getStandardId() != null) {
			writeVar("siUnit", unit.getStandardId(), true);
			writeVar("factor", unit.getFactor(), true);
		}
		
		writeType(unit, annotateType);

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
	
	protected void writeVar(String name, Serializable value, boolean writeIfNull, boolean annotateType) throws IOException {
		if (writeIfNull || value != null) {
			writeVarName(name);
			if (value != null) putAny(value, annotateType);
			else out.write("null".getBytes());
		}
	}
	
	protected void writeEntity(Entity e) throws IOException {
		writeVar("id", e.getId(), true);
		writeVar("rev", e.getRev(), true);
		writeVar("title", e.getTitle(), true);
		writeVar("localeId", e.getLocaleId(), true);
		writeVar("creatorId", e.getCreatorId(), true);
		writeVar("revCreatorId", e.getRevCreatorId(), true);
	}
	
	protected void writeListElem(Serializable elem, boolean annotateType) throws IOException {
		if (!firstElem) out.write(",\n".getBytes());

		if (elem != null) putAny(elem, annotateType);
		else out.write("null".getBytes());

		firstElem = false;
	}
	
	protected void writeType(Serializable elem, boolean annotateType) throws IOException {
		if (annotateType) {
			writeVar("class", elem.getSerializeName(), true);
		}
	}
	

}
