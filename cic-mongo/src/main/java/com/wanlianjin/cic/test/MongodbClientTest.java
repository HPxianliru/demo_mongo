//package com.wanlianjin.cic.test;
//import static org.junit.Assert.assertEquals;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.google.common.collect.ImmutableMap;
//import com.google.common.collect.Lists;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={ApplicationConfig.class})
//public class MongodbClientTest {
//
//	@Autowired
//	private MongoTemplate mongoTemplate ;
//	private String collectionName="test";
//	@Test
//	public void testInsertOne(){
//		try {
//			User user = new User();
//			user.setUserName("jingjiwu");
//			user.setAge(20);
//			mongoTemplate.insertOne(collectionName, user);
//			mongoTemplate.insertOne(collectionName,ImmutableMap.of("_id", (Object)"value1", "age", (Object)20,"name","wanlianjin"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testInsertMany(){
//		try {
//			List<User> users = Lists.newArrayList();
//			for(int i=1;i<=10;i++){
//				User user = new User();
//				user.setUserName("jingjiwu"+i);
//				user.setAge(20);
//				users.add(user);
//			}
//			mongoTemplate.insertMany(collectionName, users);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	@Test
//	public void testFindOrderSkipLimit(){
//		Query query = new Query("name").in("jingjiwu").skip(0).limit(5);
//		query.setOrder(new Order().desc("name"));
//		try {
//			List<User> users = mongoTemplate.find(collectionName, query, User.class);
//			for(User user:users){
//				System.out.println(user.toString());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testFind(){
//		Query query = new Query("name").eq("jingjiwu").and("age").eq(20);
//		try {
//			List<User> users = mongoTemplate.find(collectionName, query, User.class);
//			for(User user:users){
//				System.out.println(user.toString());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testFindMap(){
//		Query query = new Query("name").eq("jingjiwu2").and("age").eq(20);
//		try {
//			List<Map<String,Object>> users = mongoTemplate.find(collectionName, query);
//			for(Map<String,Object> user:users){
//				System.out.println(user.get("name"));
//				System.out.println(user.get("age"));
//			}
//			
//			for(Map<String,Object> user:mongoTemplate.findAll(collectionName)){
//				System.out.println(user.get("name"));
//				System.out.println(user.get("age"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testFindMapField(){
//		Query query = new Query("name").in("jingjiwu");
//		query.queryFields("name");
//		for(Map<String,Object> user:mongoTemplate.find(collectionName, query)){
//			System.out.println(user.get("name"));
//		}
//	}
//	
//	@Test
//	public void testFindAndUpdate(){
//		try {
//			Query query = new Query("name").eq("jingjiwu1");
//			Update update = new Update().inc("age");
//			User user = mongoTemplate.findOneAndUpdate(collectionName, query, update, User.class);
//			System.out.println(user.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testFindAndDelete(){
//		try {
//			Query query = new Query("name").eq("jingjiw");
//			User user = mongoTemplate.findOneAndDelete(collectionName, query,User.class);
//			System.out.println(user.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void update(){
//		try {
//			Query query = new Query("name").eq("jingjiwu");
//			Update update = new Update().set("name", "jingjiwu-1").set("age", 10);
//			mongoTemplate.updateOne(collectionName, query, update);
//			
//			Query query2 = new Query("name").eq("jingjiwu-1");
//			List<User> users = mongoTemplate.find(collectionName, query2, User.class);
//			for(User user:users){
//				System.out.println(user.toString());
//			}
//			
//			Update u2 = new Update().inc("age",5);
//			mongoTemplate.updateOne(collectionName, query2, u2);
//			
//			users = mongoTemplate.find(collectionName, query2, User.class);
//			for(User user:users){
//				System.out.println(user.toString());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void delete(){
//		Query query = new Query("name").eq("jingjiwu-1");
//		mongoTemplate.deleteOne(collectionName, query);
//	}
//	
//	@Test
//	public void testFindById() throws Exception{
//		User user = mongoTemplate.findOneById(collectionName, 21,User.class);
//		assertEquals("jingjiwu",user.getUserName());
//		
//		Map<String,Object> map = mongoTemplate.findOneById(collectionName, "value1");
//		assertEquals("wanlianjin",map.get("name"));
//	}
//	
//	@Test 
//	public void testUpdate() throws Exception{
//		User user = new User();
//		user.setUserName("jingjiwu-new-3");
//		user.setAge(23);
//		mongoTemplate.updateOne(collectionName, 22, user);
//		
//		Map map = ImmutableMap.of("age", (Object)21,"name","wanlianjin-3");
//		mongoTemplate.updateOne(collectionName, "value1", map);
//	}
//	
//	@Test 
//	public void testUpdateOrInsert() throws Exception{
//		User user = new User();
//		user.setUserName("jingjiwu-new-3");
//		user.setAge(23);
//		mongoTemplate.updateOneOrInsert(collectionName, 22, user);
//		
//		Map map = ImmutableMap.of("age", (Object)21,"name","wanlianjin-3");
//		mongoTemplate.updateOneOrInsert(collectionName, "value1", map);
//	}
//}
