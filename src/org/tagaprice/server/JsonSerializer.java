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
package org.tagaprice.server;

import java.io.IOException;
import java.io.OutputStream;

import org.tagaprice.shared.Currency;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyList;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;

public class JsonSerializer extends EntitySerializer {
	private OutputStream out;
	private boolean firstElem = true;
	
	public JsonSerializer(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void put(Currency currency) throws IOException {
		writeHead();
		
		writeVar("id", currency.getId());
		writeVar("name", currency.getName());
		
		writeTail();
	}

	@Override
	public void put(Price price) throws IOException {
		writeHead();
		
		writeVar("price", price.getPrice());
		writeVar("currency", price.getCurrency());
		
		writeTail();
	}
	
	@Override
	public void put(ProductData product) throws IOException {
		writeHead();
		
		writeVar("id", product.getId());
		writeVar("name", product.getName());
		writeVar("imageSrc", product.getImageSrc());
		writeVar("price", product.getPrice());
		writeVar("quantity", product.getQuantity());
		writeVar("rating", product.getRating());
		writeVar("progress", product.getProgress());

		/// TODO introduce the corresponding EntitySerializer-function
		writeVar("properties", product.getProperties());
		
		writeTail();
	}
	
	@Override
	public void put(PropertyList propertyList) throws IOException {
		writeHead();
		/// TODO
		writeTail();
	}

	@Override
	public void put(Quantity quantity) throws IOException {
		writeHead();
		writeVar("quantity", quantity.getQuantity());
		writeVar("unit", quantity.getUnit());
		writeTail();
	}

	@Override
	public void put(ReceiptData receipt) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void put(ServerResponse response) throws IOException {
		writeHead();
		writeVar("status", response.getStatusName());
		writeVar("response", response.getEntity());
		writeTail();
	}
	
	@Override
	public void put(ShopData shop) throws IOException {
		writeHead();
		writeVar("id", shop.getId());
		writeVar("name", shop.getName());
		writeVar("rating", shop.getRating());
		writeVar("progress", shop.getProgress());
		// TODO add other properties
		writeVar("properties", shop.getProperties());
		writeTail();
	}

	@Override
	public void put(Unit unit) throws IOException {
		writeHead();
		writeVar("id", unit.getId());
		writeVar("name", unit.getName());
		
		if (unit.getFallbackId() > 0) {
			writeVar("siUnit", unit.getFallbackId());
			writeVar("factor", unit.getFactor());
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
	
	private void writeVarName(String name) throws IOException {
		if (!firstElem) out.write(",\n".getBytes());
		out.write("\"".getBytes());
		out.write(name.getBytes());
		out.write("\": ".getBytes());
		firstElem = false;
	}
	
	protected void writeVar(String name, String value) throws IOException {
		writeVarName(name);
		if (value != null) {
			out.write("\"".getBytes());
			out.write(value.getBytes());
			out.write("\"".getBytes());
		}
		else out.write("null".getBytes());
	}
	
	protected void writeVar(String name, long value) throws IOException {
		writeVarName(name);
		out.write(new Long(value).toString().getBytes());
	}
	
	protected void writeVar(String name, double value) throws IOException {
		writeVarName(name);
		out.write(new Double(value).toString().getBytes());
	}
	
	protected void writeVar(String name, Entity value) throws IOException {
		writeVarName(name);
		if (value != null) putAny(value);
		else out.write("null".getBytes());
	}
}
