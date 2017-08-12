/**
 * Project Name:cic-server
 * File Name:UserDao.java
 * Package Name:com.wanlianjin.common.mongo.dao.impl
 * Date:2017-1-9上午10:43:24
 * Copyright (c) 2017
 *
 */

package com.wanlianjin.cic.mongo.dao;

import java.util.List;

import com.wanlianjin.cic.mongo.model.UserModel;


/**
 * ClassName:UserDao
 * 
 * @Description :
 * @Date: 2017-1-9 上午10:43:24
 * @author ychen
 * @version
 */
public interface UserDao {
	/**
	 * 查询数据
	 * 
	 * @author：ychen
	 * @Title: findAll
	 * @param @return
	 * @return List<UserModel>
	 * @throws
	 */
	public List<UserModel> findAll();

	/**
	 * 新增数据
	 * 
	 * @author：ychen
	 * @Title: insertUser
	 * @param @param user
	 * @return void
	 * @throws
	 */
	public void insertUser(UserModel user);

	/**
	 * 删除数据
	 * 
	 * @author：ychen
	 * @Title: removeUser
	 * @param @param userName
	 * @return void
	 * @throws
	 */
	public void removeUser(String userName);

	/**
	 * 修改数据
	 * 
	 * @author：ychen
	 * @Title: updateUser
	 * @param @param user
	 * @return void
	 * @throws
	 */
	public void updateUser(UserModel user);

	/**
	 * 按条件查询
	 * 
	 * @author：ychen
	 * @Title: findForRequery
	 * @param
	 * @return void
	 * @throws
	 */
	public List<UserModel> findForRequery(String userName);

}
