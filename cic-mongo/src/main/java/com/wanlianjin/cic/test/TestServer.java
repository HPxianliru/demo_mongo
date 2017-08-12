/**
 * Project Name:cic-server
 * File Name:TestServer.java
 * Package Name:com.wanlianjin.cic.aaa
 * Date:2016-11-30上午11:26:26
 * Copyright (c) 2016
 *
 */

package com.wanlianjin.cic.test;

import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.DB;
import com.wanlianjin.cic.mongo.dao.UserDao;
import com.wanlianjin.cic.mongo.model.UserModel;


/**
 * ClassName:TestServer
 * 
 * @Description :
 * @Date: 2016-11-30 上午11:26:26
 * @author ychen
 * @version
 */
public class TestServer {
	
	public static void main(String[] args) {
		
		String[] configFiles = { "classpath:applicationContext.xml",
				"classpath:app-mongo.xml",
				"classpath*:mongo/*.xml"
				};
		ConfigurableApplicationContext appContext;
		appContext = new FileSystemXmlApplicationContext(configFiles);
		
		UserDao ud = (UserDao) appContext.getBean("userService");
		
		List<UserModel> um = ud.findAll();
		
		System.out.println(um);
	}
	
}
