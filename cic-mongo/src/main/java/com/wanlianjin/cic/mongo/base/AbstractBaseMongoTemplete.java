/**
 * Project Name:cic-server
 * File Name:AbstractBaseMongoTemplete.java
 * Package Name:com.wanlianjin.common.mongo.util
 * Date:2017-1-9上午10:38:56
 * Copyright (c) 2017
 *
 */

package com.wanlianjin.cic.mongo.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * ClassName:AbstractBaseMongoTemplete
 * 
 * @Description :
 * @Date: 2017-1-9 上午10:38:56
 * @author ychen
 * @version
 */
public abstract class AbstractBaseMongoTemplete implements ApplicationContextAware {

	protected MongoTemplate mongoTemplate;

	/**
	 * @Description 根据配置文件设置mongoTemplate
	 * @param mongoTemplate
	 */
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		MongoTemplate mongoTemplate = applicationContext.getBean("mongoTemplate", MongoTemplate.class);
		setMongoTemplate(mongoTemplate);
	}
}
