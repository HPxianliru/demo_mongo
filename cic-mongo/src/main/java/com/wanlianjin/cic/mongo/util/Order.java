package com.wanlianjin.cic.mongo.util;

import com.mongodb.BasicDBObject;

/**
 *@comment 排序对象 
 *@author jingjiwu
 *@date 2016年4月28日 上午10:06:23
 *@version 1.0.0
 */
public class Order {
	
	private BasicDBObject orderObject = new BasicDBObject();
	
	/**
	 * 按某个字段升序
	 * @param field 要排序的字段
	 * @return 排序对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月28日 上午10:06:36
	 * @since 1.0.0
	 */
	public Order asc(String field){
		return setField(field,1);
	}
	
	/**
	 * 按某个字段降序
	 * @param field 要排序的字段
	 * @return 排序对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月28日 上午10:06:56
	 * @since 1.0.0
	 */
	public Order desc(String field){
		return setField(field,-1);
	}
	
	public BasicDBObject getOrderObject() {
		return orderObject;
	}

	/**
	 * 设置字段排序方式
	 * @param field 要排序的字段
	 * @param orderType 排序类型
	 * @return 排序对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月28日 上午10:09:10
	 * @since 1.0.0
	 */
	private Order setField(String field,int orderType) {
		orderObject.append(field, orderType);
		return this;
	}
}
