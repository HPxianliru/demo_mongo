package com.wanlianjin.cic.mongo.util;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

public class MongoSequence implements Sequence {
	
	private MongoSession session = null;
	private final static String SEQ_COLLECTION_NAME = "seq_all";
	public MongoSequence(MongoSession session) {
		this.session = session;
	}
	
	@Override
	public int getSequence(String collectionName) {
		String seq = String.format("%s_seq", collectionName);
		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
		options.upsert(true);
		options.returnDocument(ReturnDocument.AFTER);
		Bson query = new BasicDBObject().append("_id", seq);
		Bson update = new BasicDBObject().append("$inc", new BasicDBObject().append("s", 1));
		Document document = session.getCollection(seq).findOneAndUpdate(query, 
				update,options);
		Number sequence = (Number)document.get("s");
		return sequence.intValue();
	}

	@Override
	public int getSequence() {
		return getSequence(SEQ_COLLECTION_NAME);
	}
}
