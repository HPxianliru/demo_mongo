package com.wanlianjin.cic.mongo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.UpdateOptions;

/**
 * mongodb client
 * 提供insert,update,delete,find
 * @comment
 * @author jingjiwu
 * @date 2016年4月15日 上午9:16:32
 * @version 1.0.4
 */
public class MongoTemplate{
	private final String ID = "_id";
	private MongoSession session = null;
	private Sequence sequence = null;
	private static ObjectMapper objectMapper = new ObjectMapper();
	public MongoTemplate(MongoSession session){
		this.session = session;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}


	/**
	 * 向指定的集合插入一条记录
	 * @param collectionName 集合的名字
	 * @param fields 多个字段值的map集合
	 * @exception @Author jingjiwu
	 * @Date 2016年4月15日 下午5:53:15
	 * @since 1.0.0
	 */
	public void insertOne(String collectionName, Map<String, Object> fields) {
		Document document = new Document(fields);
		setDocumentId(collectionName,document);
		session.getCollection(collectionName).insertOne(new Document(fields));
		
	}
	
	/**
	 * 向指定的集合插入一条记录 
	 * @param collectionName 集合的名字
	 * @param e 插入的对象
	 * @throws Exception
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 上午9:42:32
	 * @since 1.0.0
	 */
	public <E> void insertOne(String collectionName,E e) throws Exception{
		Document document = Document.parse(objectMapper.writeValueAsString(e));
		setDocumentId(collectionName, document);
		session.getCollection(collectionName).insertOne(document);
	}
	
	/**
	 * 向指定的集合插入多条记录 
	 * @param collectionName 集合的名字
	 * @param fields 多个字段值的map集合
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 上午9:43:00
	 * @since 1.0.0
	 */
	public void insertMany(String collectionName,List<Map<String,Object>> fields){
		for(Map<String,Object> field:fields){
			insertOne(collectionName,field);
		}
	}

	/**
	 * 向指定的集合插入多条记录 
	 * @param collectionName 集合的名字
	 * @param cs 待插入对象的集合
	 * @throws Exception
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 上午9:43:29
	 * @since 1.0.0
	 */
	public <E> void insertMany(String collectionName,Collection<E> cs) throws Exception{
		for(E e:cs){
			insertOne(collectionName, e);
		}
	}
	
	/**
	 * 根据条件查询记录
	 * @param collectionName 集合的名字
	 * @param query 查询对象
	 * @param clazz 类型
	 * @return 结果集
	 * @throws Exception
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 上午9:43:59
	 * @since 1.0.0
	 */
	public <E> List<E> find(String collectionName,Query query,Class<E> clazz) throws Exception{
		List<E> result = Lists.newArrayList();
		for(Document document:findIterator(collectionName, query)){
			result.add(objectMapper.readValue(document.toJson(),clazz));
		}
		return result;
	}

	/**
	 * 根据条件查询记录,返回map对象数组
	 * @param collectionName 集合的名字
	 * @param query 查询对象
	 * @return 结果集
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 下午3:02:33
	 * @since 1.0.0
	 */
	public List<Map<String,Object>> find(String collectionName,Query query){
		List<Map<String,Object>> result = Lists.newArrayList();
		for(Document document:findIterator(collectionName, query)){
			if(query.getQueryFields()==null){
				result.add(document);
			}else{
				Map<String,Object> map = Maps.newHashMap();
				for(String field:query.getQueryFields()){
					if(document.containsKey(field)){
						map.put(field, document.get(field));
					}
				}
				map.put(ID, document.get(ID));
				result.add(map);
			}
		}
		return result;
	}
	
	/**
	 * 根据条件查询记录，仅一条
	 * @param collectionName 集合的名字
	 * @param query 查询对象
	 * @param clazz 类型
	 * @return 结果
	 * @throws Exception
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年5月25日 下午2:24:30
	 * @since 1.0.0
	 */
	public <E> E findOne(String collectionName,Query query,Class<E> clazz) throws Exception{
		for(Document document:findIterator(collectionName, query)){
			return objectMapper.readValue(document.toJson(),clazz);
		}
		return null;
	}
	
	/**
	 * 根据条件查询记录，返回map对象,仅一条
	 * @param collectionName 集合的名字
	 * @param query 查询对象
	 * @return  结果
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年5月25日 下午2:25:10
	 * @since 1.0.0
	 */
	public Map<String,Object> findOne(String collectionName,Query query){
		List<Map<String,Object>> result = this.find(collectionName,query);
		if(result.size()>0)
			return result.get(0);
		else 
			return null;
	}
	
	/**
	 * 查询集合所有对象
	 * @param collectionName 集合的名字
	 * @param clazz 类型
	 * @return 结果集
	 * @throws Exception
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 上午9:44:33
	 * @since 1.0.0
	 */
	public <E> List<E> findAll(String collectionName,Class<E> clazz) throws Exception{
		List<E> result = Lists.newArrayList();
		for(Document document:session.getCollection(collectionName).find()){
			result.add(objectMapper.readValue(document.toJson(),clazz));
		}
		return result;
	}
	
	/**
	 * 以map的方式返回所有数据
	 * @param collectionName 集合的名字
	 * @return 结果集
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 下午3:09:34
	 * @since 1.0.0
	 */
	public List<Map<String,Object>> findAll(String collectionName){
		List<Map<String,Object>> result = Lists.newArrayList();
		for(Document document:session.getCollection(collectionName).find()){
			result.add(document);
		}
		return result;
	}
	
	/**
	 * 以json的方式返回所有数据
	 * @param collectionName 集合的名字
	 * @return 结果集
	 * @exception
	 * @Author denghao
	 * @Date 2016年5月25日 下午2:31:13
	 * @since 1.0.0
	 */
	public List<String> findAllJson(String collectionName,Query query){
		List<String> list=new ArrayList<String>();
		for(Document document:findIterator(collectionName, query)){
			list.add(document.toJson());
		}
		return list;
	}
	
	/**
	 * 根据ID条件查询记录，仅一条
	 * @param collectionName 集合的名字
	 * @param query 查询对象
	 * @param clazz 类型
	 * @return 结果
	 * @throws Exception
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年5月25日 下午2:24:30
	 * @since 1.0.0
	 */
	public <E> E findOneById(String collectionName,Object id,Class<E> clazz) throws Exception{
		Query query = new Query("_id").eq(id);
		return this.findOne(collectionName, query,clazz);
	}
	
	/**
	 * 根据ID条件查询记录，返回map对象,仅一条
	 * @param collectionName 集合的名字
	 * @param query 查询对象
	 * @return  结果
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年5月25日 下午2:25:10
	 * @since 1.0.0
	 */
	public Map<String,Object> findOneById(String collectionName,Object id){
		Query query = new Query("_id").eq(id);
		return this.findOne(collectionName, query);
	}
	
	/**
	 * 根据ID条件查询记录，返回map对象,仅一条
	 * @param collectionName 集合的名字
	 * @param id 对象id
	 * @param returnField 查询返回哪些字段
	 * @return  结果
	 * @exception
	 * @Author WangShuo
	 * @Date 2016年9月23日 上午11:32:15
	 * @since 1.0.0
	 */
	public Map<String, Object> findOneById(String collectionName, Object id, String... returnField){
		Query query = new Query("_id").eq(id);
		query.queryFields(returnField);
		return findIterator(collectionName, query).first();
	}
	
	/**
	 * 根据ID条件查询记录
	 * @param collectionName 集合的名字
	 * @param id 对象id
	 * @param clazz 返回对象的类对象类型
	 * @param returnField 查询返回哪些字段
	 * @return
	 * @throws Exception
	 * @exception
	 * @Author WangShuo
	 * @Date 2016年9月23日 下午2:43:37
	 * @since 1.0.0
	 */
	public <E> E findOneById(String collectionName,Object id,Class<E> clazz, String... returnField) throws Exception{
		Query query = new Query("_id").eq(id);
		query.queryFields(returnField);
		Document document = findIterator(collectionName, query).first();
		return objectMapper.readValue(document.toJson(), clazz);
	}
	
	/**
	 * 查询更新，并返回更新后的数据 
	 * @param collectionName 集合名
	 * @param query 查询对象
	 * @param update 更新对象
	 * @param clazz 对象类型
	 * @return 更新后的结果
	 * @throws Exception
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 上午9:50:10
	 * @since 1.0.0
	 */
	public <E> E findOneAndUpdate(String collectionName,Query query,Update update,Class<E> clazz) throws Exception{
		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
		options.upsert(true);
		options.returnDocument(ReturnDocument.AFTER);
		Document document = session.getCollection(collectionName).findOneAndUpdate(query.getQueryObject(), 
				update.getUpdateObject(),options);
		return objectMapper.readValue(document.toJson(),clazz);
	}
	
	/**
	 * 查询更新，并返回更新后的数据 
	 * @param collectionName 集合名
	 * @param query 查询对象
	 * @param update 更新对象
	 * @param clazz 对象类型
	 * @param upsert 为true时，表示查询不到数据就添加，为false，表示查询不到就什么都不做。
	 * @param returnAfter 为true，表示返回更新之后的值，为false表示返回更新之前的值。
	 * @return 
	 * @throws Exception
	 * @exception
	 * @Author PC
	 * @Date 2016年9月5日 下午4:19:03
	 * @since 1.0.0
	 * @see
	 */
	public <E> E findOneAndUpdate(String collectionName,Query query,Update update,boolean upsert, boolean returnAfter,Class<E> clazz) throws Exception{
		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
		options.upsert(upsert);
		if(returnAfter){
			options.returnDocument(ReturnDocument.AFTER);
		}
		Document document = session.getCollection(collectionName).findOneAndUpdate(query.getQueryObject(), 
				update.getUpdateObject(),options);
		return objectMapper.readValue(document.toJson(),clazz);
	}
	
	/**
	 * 查询更新，并返回数据. 通过设置
	 * @param collectionName 集合名
	 * @param query 查询对象
	 * @param update 更新对象
	 * @param upsert 为true时，表示查询不到数据就添加，为false，表示查询不到就什么都不做。
	 * @param returnAfter 为true，表示返回更新之后的值，为false表示返回更新之前的值。
	 * @param returnField 需要返回数据的字段。
	 * @return Map, key为字段名，value为字段值。
	 * @throws Exception
	 * @exception
	 * @Author WangShuo
	 * @Date 2016年9月22日 下午2:32:15
	 * @since 1.0.4
	 * @see
	 */
	public Map<String, Object> findOneAndUpdate(String collectionName,Query query,Update update,boolean upsert, boolean returnAfter) throws Exception{
		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
		options.upsert(upsert);
		if(returnAfter){
			options.returnDocument(ReturnDocument.AFTER);
		}
		options.projection(this.keyFields(query.getQueryFields()));
		return session.getCollection(collectionName).findOneAndUpdate(query.getQueryObject(), 
				update.getUpdateObject(),options);
	}
	
	/**
	 * 查询删除，并返回删除后的数据
	 * @param collectionName 集合名
	 * @param query 查询对象
	 * @param clazz 对象类型
	 * @return 删除后对象
	 * @throws Exception
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 上午9:49:53
	 * @since 1.0.0
	 */
	public <E> E findOneAndDelete(String collectionName,Query query,Class<E> clazz) throws Exception{
		E e = null;
		Document document = session.getCollection(collectionName).findOneAndDelete(query.getQueryObject());
		if(document!=null){
			e =  objectMapper.readValue(document.toJson(),clazz);
		}
		return e;
	}
	
	/**
	 * 根据查询条件删除一条记录
	 * @param collectionName 集合名
	 * @param query 查询对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月18日 下午2:32:43
	 * @since 1.0.0
	 */
	public void deleteOne(String collectionName,Query query){
		session.getCollection(collectionName).deleteOne(query.getQueryObject());
	}
	
	/**
	 * 根据查询条件删除多条记录
	 * @param collectionName 集合名
	 * @param query 查询对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月18日 下午2:32:56
	 * @since 1.0.0
	 */
	public void deleteMany(String collectionName,Query query){
		session.getCollection(collectionName).deleteMany(query.getQueryObject());
	}
	
	/**
	 * 更新一条记录，包括set,unset,inc,pushAll,pullAll,addToSet
	 * @param collectionName 集合名
	 * @param query 查询对象
	 * @param update 更新对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月18日 下午2:33:17
	 * @since 1.0.0
	 */
	public void updateOne(String collectionName,Query query,Update update){
		updateOne(collectionName, query.getQueryObject(), update.getUpdateObject(),false);
	}
	
	/**
	 * 更新一条记录,如果不存在就新插入一条记录
	 * @param collectionName 集合名
	 * @param query 查询对象
	 * @param update 更新对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月18日 下午2:33:17
	 * @since 1.0.0
	 */
	public void updateOneOrInsert(String collectionName,Query query,Update update){
		updateOne(collectionName, query.getQueryObject(), update.getUpdateObject(), true);
	}
	
	/**
	 * 更新多条记录
	 * @param collectionName 集合名
	 * @param query 查询对象
	 * @param update 更新对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月18日 下午2:33:48
	 * @since 1.0.0
	 */
	public void updateMany(String collectionName,Query query,Update update){
		updateMany(collectionName,query.getQueryObject(), update.getUpdateObject(),false);
	}
	
	/**
	 * 更新多条记录,如果不存在就新插入记录
	 * @param collectionName 集合名
	 * @param query 查询对象
	 * @param update 更新对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月18日 下午2:33:48
	 * @since 1.0.0
	 */
	public void updateManyOrInsert(String collectionName,Query query,Update update){
		updateMany(collectionName,query.getQueryObject(), update.getUpdateObject(),true);
	}
	
	/**
	 * 根据ID查询，并更新整个文档
	 * @param collectionName 集合名
	 * @param id 更新数据的ID
	 * @param e 待更新数据
	 * @throws Exception
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年6月29日 下午3:15:25
	 * @since 1.0.0
	 */
	public <E> void updateOne(String collectionName,Object id,E e)throws Exception{
		Bson update = new BasicDBObject("$set", Document.parse(objectMapper.writeValueAsString(e)));
		updateOneById(collectionName, id, update,false);
	}
	
	/**
	 * 根据ID查询，并更新整个文档,如果不存在新插入该文档
	 * @param collectionName 集合名
	 * @param id 更新数据的ID
	 * @param e 待更新数据
	 * @throws Exception
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年6月29日 下午3:15:25
	 * @since 1.0.0
	 */
	public <E> void updateOneOrInsert(String collectionName,Object id,E e)throws Exception{
		Bson update = new BasicDBObject("$set", Document.parse(objectMapper.writeValueAsString(e)));
		updateOneById(collectionName, id, update,true);
	}
	
	
	/**
	 * 根据ID查询，并更新整个文档
	 * @param collectionName 集合名
	 * @param id 更新数据的ID
	 * @param fields 待更新数据
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年6月29日 下午3:24:18
	 * @since 1.0.0
	 */
	public void updateOne(String collectionName,Object id,Map<String,Object>fields){
		Bson update = new BasicDBObject("$set", fields);
		updateOneById(collectionName, id, update,false);
	}
	
	/**
	 * 根据ID查询，并更新整个文档,如果不存在新插入该文档
	 * @param collectionName 集合名
	 * @param id 更新数据的ID
	 * @param fields 待更新数据
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年6月29日 下午3:24:18
	 * @since 1.0.0
	 */
	public void updateOneOrInsert(String collectionName,Object id,Map<String,Object>fields){
		Bson update = new BasicDBObject("$set", fields);
		updateOneById(collectionName, id, update,true);
	}
	
	

	
	/**
	 * 设置document的_id
	 * @param e
	 * @param document
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 下午6:01:49
	 * @since 1.0.0
	 */
	private <E> void setDocumentId(String collection, Document document) {
		if(sequence!=null){
			document.append(ID,sequence.getSequence(collection));
		}
	}
	
	/**
	 * 设置document的_id
	 * @param document
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 下午6:05:29
	 * @since 1.0.0
	 */
	/*private void setDocumentId(Document document) {
		if(sequence!=null){
			document.append(ID,sequence.getSequence());
		}
	}*/
	
	/**
	 * 根据查询条件返回一个查询Iterable
	 * @param collectionName 集合名
	 * @param query 查询对象
	 * @return Iterable对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 下午3:01:00
	 * @since 1.0.0
	 */
	private FindIterable<Document> findIterator(String collectionName, Query query) {
		FindIterable<Document> iterator = session.getCollection(collectionName).find(query.getQueryObject());
		if(query.getSkip()>0){
			iterator.skip(query.getSkip());
		}
		if(query.getLimit()>0){
			iterator.limit(query.getLimit());
		}
		if(null!=query.getOrder()){
			iterator.sort(query.getOrderObject());
		}
		if(null != query.getQueryFields()){
			iterator.projection(this.keyFields(query.getQueryFields()));
		}
		return iterator;
	}
	
	/**
	 * 更新多条记录
	 * @param collectionName 集合名
	 * @param queryFields 查询的字段和相应的值
	 * @param updateFields 更新的字段和相应的值
	 * @param upsert true:没有匹配新增，false:不新增
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月18日 下午3:08:36
	 * @since 1.0.0
	 */
	private void updateMany(String collectionName,Bson queryFields, Bson updateFields,boolean upsert){
		UpdateOptions options = new UpdateOptions();
		options.upsert(upsert);
		session.getCollection(collectionName).updateMany(queryFields, updateFields,options);
	}
	
	/**
	 * 更新一条记录
	 * @param collectionName 集合名
	 * @param queryFields 查询的字段和相应的值
	 * @param updateFields 更新的字段和相应的值
	 * @param upsert true:没有匹配新增，false:不新增
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月18日 下午3:03:44
	 * @since 1.0.0
	 */
	private void updateOne(String collectionName, Bson queryFields, Bson updateFields,boolean upsert) {
		UpdateOptions options = new UpdateOptions();
		options.upsert(upsert);
		session.getCollection(collectionName).updateOne(queryFields, updateFields,options);
	}
	
	/**
	 * 根据ID查询并更新一条记录
	 * @param collectionName 集合名
	 * @param id 查询ID
	 * @param update 待更新的文档
	 * @param upsert true:没有匹配新增，false:不新增
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年6月29日 下午3:52:04
	 * @since 1.0.0
	 */
	private void updateOneById(String collectionName, Object id,Bson update,boolean upsert) {
		Query query = new Query("_id").eq(id);
		updateOne(collectionName, query.getQueryObject(), update, upsert);
	}
	
	private Bson keyFields(String[] returnField){
		if(returnField == null){
			return null;
		}
		BasicDBObject bdo = new BasicDBObject();
		for(String field : returnField){
			bdo.put(field, 1);
		}
		return bdo;
	}
	
}
