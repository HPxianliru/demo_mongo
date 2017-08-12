package com.wanlianjin.cic.mongo.util;

import java.util.List;
import java.util.regex.Pattern;

import org.bson.conversions.Bson;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;

/**
 *@comment 查询对象
 *@author jingjiwu
 *@date 2016年4月28日 上午10:09:48
 *@version 1.0.0
 */
public class Query {

	private String[] queryFields;
	private Order order;
	private int skip = 0;
	private int limit = 0;
	private BasicDBObject queryObject = new BasicDBObject();
	private String field;

	public Query() {
	}
	
	/**
	 * 设置查询返回的字段，find返回map的方法可用
	 * @param field 要返回的字段
	 * @return 查询对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 下午4:27:12
	 * @since 1.0.0
	 */
	public Query queryFields(String...field){
		queryFields = field;
		return this;
	}

	public String[] getQueryFields() {
		return queryFields;
	}

	/**
	 * 指定第一个查询条件的字段
	 * @param field 要查询的字段
	 */
	public Query(String field) {
		this.field = field;
	}

	public Query and(String field) {
		this.field = field;
		return this;
	}

	public Query eq(Object value) {
		return setField(value);
	}

	public Query gt(Object value) {
		return setField(QueryOperators.GT, value);
	}

	public Query gte(Object value) {
		return setField(QueryOperators.GTE, value);
	}

	public Query lt(Object value) {
		return setField(QueryOperators.LT, value);
	}

	public Query lte(Object value) {
		return setField(QueryOperators.LTE, value);
	}

	public Query in(Object[] value) {
		return setField(QueryOperators.IN, value);
	}

	/**
	 * 包含，相当于模糊查询
	 * @param value 要查询的值
	 * @return 查询对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月28日 上午10:11:35
	 * @since 1.0.0
	 */
	public Query in(String value) {
		Pattern pattern = Pattern.compile(String.format("^.*%s.*$", value), Pattern.CASE_INSENSITIVE);
		queryObject.append(this.field, pattern);
		return this;
	}

	public Query nin(Object[] value) {
		return setField(QueryOperators.NIN, value);
	}

	public Query ne(Object value) {
		return setField(QueryOperators.NE, value);
	}

	public Query mod(Object[] value) {
		return setField(QueryOperators.MOD, value);
	}

	public Query all(Object[] value) {
		return setField(QueryOperators.ALL, value);
	}

	public Query exists(boolean exits) {
		return setField(QueryOperators.EXISTS, exits);
	}

	public Query nor(Query... query) {
		return setFields(QueryOperators.NOR, query);
	}

	public Query or(Query... query) {
		return setFields(QueryOperators.OR, query);
	}

	public Query and(Query... query) {
		return setFields(QueryOperators.AND, query);
	}

	public Bson getQueryObject() {
		return queryObject;
	}

	public Bson getOrderObject() {
		return order.getOrderObject();
	}

	public Query skip(int skip) {
		this.skip = skip;
		return this;
	}

	public Query limit(int limit) {
		this.limit = limit;
		return this;
	}

	public int getSkip() {
		return skip;
	}

	public int getLimit() {
		return limit;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	private Query setFields(String operate, Query... query) {
		List<Bson> objects = Lists.newArrayList();
		for (Query temp : query) {
			objects.add(temp.getQueryObject());
		}
		queryObject.append(operate, objects);
		return this;
	}

	private Query setField(String operate, Object value) {
		queryObject.append(this.field, new BasicDBObject(operate, value));
		return this;
	}
	
	private Query setField(Object value){
		queryObject.append(this.field, value);
		return this;
	}

	public static void main(String[] args) {
		Query q1 = new Query("name").eq("jingjiwu").and("age").gt(10);
		System.out.println(q1.getQueryObject());

		Query q2 = new Query().or(new Query("name").eq("jingjiwu"), new Query("age").gt(10));
		System.out.println(q2.getQueryObject());

		Query q3 = new Query().and(new Query("name").eq("jingjiwu"), new Query("age").gt(10));
		System.out.println(q3.getQueryObject());

		Query q4 = new Query().nor(new Query("name").eq("jingjiwu"), new Query("age").gt(10));
		System.out.println(q4.getQueryObject());
		
		Query q5 = new Query("_id").eq("123");
		q5.or(new Query("c").exists(false), new Query("age").gt(10));
		System.out.println(q5.getQueryObject());
	}
}
