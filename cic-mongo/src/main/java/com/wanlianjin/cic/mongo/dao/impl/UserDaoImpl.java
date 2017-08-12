/**
 * Project Name:cic-mongo
 * File Name:UserDaoImpl.java
 * Package Name:com.wanlianjin.cic.mongo.dao.impl
 * Date:2017-1-9上午11:24:47
 * Copyright (c) 2017
 *
 */

package com.wanlianjin.cic.mongo.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.wanlianjin.cic.mongo.base.AbstractBaseMongoTemplete;
import com.wanlianjin.cic.mongo.dao.UserDao;
import com.wanlianjin.cic.mongo.model.UserModel;

/**
 * ClassName:UserDaoImpl
 * 
 * @Description :
 * @Date: 2017-1-9 上午11:24:47
 * @author ychen
 * @version
 */
public class UserDaoImpl extends AbstractBaseMongoTemplete implements UserDao {

	private final String collectionName = "userModel";
	private final String collectionKey1 = "userName";
	private final String collectionKey2 = "password";
	/**
	 * 查询所有数据
	 * 
	 * @author：ychen
	 * @Title: findAll
	 * @Description: TODO
	 * @param @return
	 * @throws
	 */
	@Override
	public List<UserModel> findAll() {
		// 需要设置集合对应的实体类和相应的集合名，从而查询结果直接映射
		
		List<UserModel> userList = mongoTemplate.findAll(UserModel.class, collectionName);
		return userList;
	}

	/**
	 * 新增数据
	 * 
	 * @author：ychen
	 * @Title: insertUser
	 * @Description: TODO
	 * @param @param user
	 * @throws
	 */
	@Override
	public void insertUser(UserModel user) {
		// 设置需要插入到数据库的文档对象
		DBObject object = new BasicDBObject();
		object.put(collectionKey1, user.getUserName());
		object.put(collectionKey2, user.getPassword());
		mongoTemplate.insert(object, collectionName);
	}

	/**
	 * 按条件删除数据
	 * 
	 * @author：ychen
	 * @Title: removeUser
	 * @Description: TODO
	 * @param @param userName
	 * @throws
	 */
	@Override
	public void removeUser(String userName) {
		// 设置删除条件，如果条件内容为空则删除所有
		Query query = new Query();
		Criteria criteria = new Criteria(collectionKey1);
		criteria.is(userName);
		query.addCriteria(criteria);
		mongoTemplate.remove(query, collectionName);
	}

	/**
	 * 修改数据
	 * 
	 * @author：ychen
	 * @Title: updateUser
	 * @Description: TODO
	 * @param @param user
	 * @throws
	 */
	@Override
	public void updateUser(UserModel user) {
		
		// 设置修改条件
		Query query = new Query();
		Criteria criteria = new Criteria(collectionKey1);
		criteria.is(user.getUserName());
		query.addCriteria(criteria);
		// 设置修改内容
		Update update = Update.update(collectionKey2, user.getPassword());
		// 参数：查询条件，更改结果，集合名
		mongoTemplate.updateFirst(query, update, collectionName);
	}

	/**
	 * 根据条件查询
	 * 
	 * @author：ychen
	 * @Title: findForRequery
	 * @Description: TODO
	 * @param @param userName
	 * @throws
	 */
	@Override
	public List<UserModel> findForRequery(String userName) {
		Query query = new Query();
		Criteria criteria = new Criteria(userName);
		criteria.is(userName);
		query.addCriteria(criteria);
		// 查询条件，集合对应的实体类，集合名
		List<UserModel> userList = mongoTemplate.find(query, UserModel.class, collectionName);
		return userList;
	}

}
