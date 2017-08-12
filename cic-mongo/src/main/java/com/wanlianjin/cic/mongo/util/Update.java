package com.wanlianjin.cic.mongo.util;

import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;

/**
 * 更新对象
 *@comment  
 *@author jingjiwu
 *@date 2016年4月28日 上午10:14:35
 *@version 1.0.0
 */
public class Update {
	private BasicDBObject updateObject = new BasicDBObject();
	
	public Update set(String field,Object value){
		return setField("$set",field, value);
	}
	
	public Update setNotNull(String field,Object value){
		if(value!=null){
			return setField("$set",field, value);
		}else{
			return this;
		}
	}
	
	public Update unSet(String field){
		return setField("$unset",field, 1);
	}
	
	public Update inc(String field){
		return setField("$inc",field, 1);
	}
	
	public Update inc(String field,int value){
		return setField("$inc",field, value);
	}
	
	public Update addToSet(String field,Object value){
		return setField("$addToSet",field, value);
	}
	
	public Update addToSetList(String field, List<?> values){
		return setField("$addToSet",field, new BasicDBObject("$each", values));
	}
	
	public Update push(String field,Object...values){
		return setField("$pushAll",field, values);
	}
	
	public Update pull(String field,Object... values){
		return setField("$pullAll",field, values);
	}
	
	public Bson getUpdateObject(){
		return updateObject;
	}
	
	private Update setField(String operate,String field, Object value) {
		BasicDBObject temp = (BasicDBObject) updateObject.get(operate);
		if(temp!=null){
			temp.append(field, value);
		}else{
			updateObject.append(operate, new BasicDBObject(field,value));
		}
		return this;
	}

	private Update setField(String operate,String field, Object... values) {
		updateObject.append(operate, new BasicDBObject(field,values));
		return this;
	}
	
	public static void main(String[] args){
		Update u1 = new Update().set("name", "jingjiwu").set("age", 20);
		System.out.println(u1.getUpdateObject());
		
		Update u2 = new Update().unSet("name").unSet("age");
		System.out.println(u2.getUpdateObject());
		
		Update u3 = new Update().inc("age").inc("num", 2);
		System.out.println(u3.getUpdateObject());
		
		Update u32 = new Update().inc("age");
		System.out.println(u32.getUpdateObject());
		
		//Update u4 = new Update().addToSet("name", "jingjiwu","jingjiwu2");
		//System.out.println(u4.getUpdateObject());
		
		Update u5 = new Update().push("name", "jingjiwu","jingjiwu2");
		System.out.println(u5.getUpdateObject());
		
		Update u6 = new Update().pull("name", "jingjiwu","jingjiwu2");
		System.out.println(u6.getUpdateObject());
		
		Update u7 = new Update().inc("age").set("_id", 1);
		System.out.println(u7.getUpdateObject());
	}
}
