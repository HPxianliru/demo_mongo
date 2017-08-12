/**
 * Project Name:cic-mongo
 * File Name:Test.java
 * Package Name:com.wanlianjin.cic.test
 * Date:2017-1-12上午10:10:26
 * Copyright (c) 2017
 *
 */

package com.wanlianjin.cic.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.CannotGetMongoDbConnectionException;
import org.springframework.data.mongodb.core.MongoDbUtils;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * ClassName:Test
 * 
 * @Description :
 * @Date: 2017-1-12 上午10:10:26
 * @author ychen
 * @version
 */
public class Test {
	public static void main(String[] args) throws UnknownHostException {
        String url = "localhost";
        int port = 27017;

        MongoClient client = null;

        client = new MongoClient(url,port);


		ServerAddress serverAddress = new ServerAddress(url, port);
		List<ServerAddress> seeds = new ArrayList<ServerAddress>();
		boolean add = seeds.add(serverAddress);
		MongoCredential credentials = MongoCredential
				.createScramSha1Credential("wlj", "wlTest", "123456".toCharArray());
		List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		credentialsList.add(credentials);
		client = new MongoClient(seeds, credentialsList);

		MongoDatabase db = client.getDatabase("wlTest");
		// MongoIterable<Document> collections=db.listCollections();
		MongoCollection<Document> collection = db.getCollection("userModel");
		List<Document> foundDocument = collection.find().into(new ArrayList<Document>());
		System.out.println(foundDocument);
	}
}
