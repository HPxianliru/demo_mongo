/**
 * Project Name:cic-server
 * File Name:UserModel.java
 * Package Name:com.wanlianjin.common.mongo.model
 * Date:2017-1-9上午10:44:08
 * Copyright (c) 2017
 *
 */

package com.wanlianjin.cic.mongo.model;

import java.io.Serializable;

/**
 * ClassName:UserModel
 * 
 * @Description :
 * @Date: 2017-1-9 上午10:44:08
 * @author ychen
 * @version
 */
public class UserModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String _id;
	private String userName;
	private String password;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
