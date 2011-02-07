package org.tagaprice.server.service.helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import org.tagaprice.server.dao.helper.CustomWriter;

public class TestdataGenerator {
	public static void main(String[] args) throws IOException {
		PrintStream out = System.out;
		Scanner in = new Scanner(System.in);
		
		out.println("fileName");
		String fileName = in.nextLine();
		
		FileWriter fstream = new FileWriter(fileName);
		CustomWriter sql = new CustomWriter(new BufferedWriter(fstream));
		
		HashMap<String, String> savedProducts = new HashMap<String, String>();
		HashMap<String, String> categories = new HashMap<String, String>();
		
		int i = 1;
		categories.put("Milchprodukte", ""+i++);
		categories.put("Gebäck", ""+i++);
		categories.put("Gemüse", ""+i++);
		categories.put("Fleischprodukte", ""+i++);
		categories.put("Obst", ""+i++);
		categories.put("Fisch", ""+i++);
		categories.put("Süßigkeiten", ""+i++);
		categories.put("Alkoholische Getränke", ""+i++);

		out.println("next categoryId ?");
		String categoryCounterS = in.nextLine();
		int categoryCounter = Integer.parseInt(categoryCounterS);
		
		out.println("next productId ?");
		String productCounterS = in.nextLine();
		int productCounter = Integer.parseInt(productCounterS);
		
		out.println("next receiptId ?");
		String receiptCounterS = in.nextLine();
		int receiptCounter = Integer.parseInt(receiptCounterS);
		
		
		
		out.println("uid");
		String uid = in.nextLine();
//		out.println("mail");
//		String mail = in.nextLine();
//		out.println("password");
//		String password = in.nextLine();
//		String lastLogin = "default";
//		
//		sql.write("INSERT INTO ACCOUNT (uid, email,last_login, password) VALUES ('"+uid+"','"+mail+"','"+lastLogin+"','"+password+"');\n");

		boolean c=true;
		while(c) {

			out.println("start shop");
			out.println("sId");
			String sId = in.nextLine();
			out.println("sTitle");
			String title = in.nextLine();
			out.println("latitude");
			String latitude = in.nextLine();
			out.println("langitude");
			String langitude = in.nextLine();
			
			sql.write("INSERT INTO shop (shop_id, title, latitude, longitude) VALUES ('"+sId+"','"+title+"','"+latitude+"','"+langitude+"');\n");
			
			String ent_id="0";

			c=true;
			while(c) {
				String rId  = ""+receiptCounter;
				receiptCounter++;
				
				out.println("start receipt (id "+rId+")");
				
				sql.write("INSERT INTO receipt (receipt_id, shop_id, created_at, creator) VALUES ('"+rId+"','"+sId+"',default,'"+uid+"');\n");

				c=true;
				while (c) {
					out.println("start receipt entry");
					
					out.println("productTitle");
					String productTitle = in.nextLine();
					
					if(savedProducts.containsKey(productTitle)) {
						out.println("product "+productTitle+" exists. id: "+savedProducts.get(productTitle));
					} else {
						savedProducts.put(productTitle, ""+productCounter);
						ent_id = ""+productCounter;
						productCounter++;
						
						out.println("new product "+productTitle+" "+ent_id);
						
						
						out.println("unit (g/kg/l/ml/p)");
						String unit = in.nextLine();
						if(unit.equals("p"))
							unit = "piece";
						
						out.println("amount");
						String amount = in.nextLine();
						
						out.println("category");
						String category = in.nextLine();
						
						if(categories.containsKey(category)) {
							category = categories.get(category);
						} else {
							categories.put(category, ""+categoryCounter);
							category = categoryCounter+"";
							categoryCounter++;
						}
						
						out.println("imageURL");
						String imageURL = in.nextLine();
						
						
						sql.write("INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('"+ent_id+"','"+1+"','"+productTitle+"',default,'"+uid+"');\n");
						sql.write("INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('"+ent_id+"','"+1+"','"+unit+"','"+amount+"','"+category+"','"+imageURL+"');\n");
						sql.write("INSERT INTO entity(ent_id, locale_id) VALUES ('"+ent_id+"','"+1+"');\n");
						sql.write("INSERT INTO product(ent_id) VALUES ('"+ent_id+"');\n");
					}
					
					out.println("count");
					String count = in.nextLine();
					
					out.println("price");
					String price = in.nextLine();
					
					
					sql.write("INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('"+rId+"','"+ent_id+"','"+1+"','"+count+"','"+price+"');\n");
					

					out.println("continue? (y/n)");
					c = in.nextLine().equals("y");

				}

				out.println("continue? (y/n)");
				c = in.nextLine().equals("y");
			}
			
			out.println("continue? (y/n)");
			c = in.nextLine().equals("y");
		}
		
		sql.close();
	}

}
