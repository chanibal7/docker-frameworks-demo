package com.mongodb.service;

import java.net.UnknownHostException;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

@Path("mongoDBService")
public class MongoDBService {

	@GET
	@Path("/mongoDBOperation")
	@Produces(MediaType.APPLICATION_XML)
	public String mongodbTest() throws InterruptedException {

		try {

			System.out
			.println("CRUD OPeration begins on MongoDB");
			/**** Connect to MongoDB ****/
			MongoClient mongo = new MongoClient("localhost", 27017);

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("testdb");

			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("user");

			/**** Insert ****/
			// create a document to store key and value
			BasicDBObject document = new BasicDBObject();
			document.put("name", "Jack");
			document.put("age", 30);
			document.put("createdDate", new Date());
			table.insert(document);
			System.out
			.println("****************** Done with inserting Document **********************");

			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", "Jack");

			DBCursor cursor = table.find(searchQuery);
			
			System.out
			.println("------------------ Search for inserting Document ----------------------");
			Thread.sleep(1000);
			while (cursor.hasNext()) {
				System.out.println("Document:" +cursor.next());
			}
			
			System.out
			.println("------------------ Done with Search of insereted Document --------------");
			/**** Update ****/
			// search document where name="docker" and update it with new values
			BasicDBObject query = new BasicDBObject();
			query.put("name", "Jack");

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("name", "Jack-updated");

			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);

			table.update(query, updateObj);
			Thread.sleep(1000);
			System.out
			.println("****************** Done with Update of Document **********************");

			/**** Find and display ****/
			BasicDBObject searchQuery2 = new BasicDBObject().append("name",
					"Jack-updated");

			DBCursor cursor2 = table.find(searchQuery2);
			
			System.out
			.println("------------------ Search for Update Document ----------------------");
			Thread.sleep(1000);
			while (cursor2.hasNext()) {
				System.out.println(cursor2.next());
			}
			System.out
			.println("------------------ Done with Search of Update Document --------------");
			
			/**** Done ****/
			System.out
			.println("CRUD Operation end on MongoDB");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}

		return "sucess";

	}
}
