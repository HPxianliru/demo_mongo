package com.wanlianjin.cic.mongo.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * mongo session信息
 *@comment  
 *@author jingjiwu
 *@date 2016年4月28日 上午10:05:59
 *@version 1.0.0
 */
public class MongoSession {
	private static final String SPLIT = "[:]";
	private BuildOptions buildOptions = null;
	private MongoClient client = null;
	private MongoDatabase database = null;

	public MongoSession(BuildOptions buildOptions) throws Exception{
		this.buildOptions = buildOptions;
		validateOptions();
		if (StringUtils.isNotBlank(buildOptions.getUserName()) && StringUtils.isNotBlank(buildOptions.getPassword())) {
			createCredentialClient();
		} else {
			createGeneralClient();
		}
		if(buildOptions.getHosts().size()>1){
			client.setReadPreference(ReadPreference.secondaryPreferred());
		}
		database = client.getDatabase(buildOptions.getDatabaseName());
	}
	
	public MongoSession(List<String> hosts,String databaseName){
		buildOptions = new BuildOptions();
		client = new MongoClient(this.setServerAddress(hosts),getOptions().build());
		database = client.getDatabase(databaseName);
		if(hosts.size()>1){
			client.setReadPreference(ReadPreference.secondary());
		}
	}
	
	public MongoSession(String host,int port,String databaseName){
		buildOptions = new BuildOptions();
		client = new MongoClient(new ServerAddress(host,port),getOptions().build());
		database = client.getDatabase(databaseName);
	}
	
	/**
	 * 释放客户端会话连接
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月18日 下午2:01:21
	 * @since 1.0.0
	 */
	public void release(){
		if(client!=null){
			this.client.close();
			this.client = null;
		}
	}
	
	
	public MongoCollection<Document> getCollection(String collectionName){
		return database.getCollection(collectionName);
	}
	
	public MongoDatabase getDatabase(){
		return database;
	}
	
	public MongoDatabase getDatabase(String databaseName){
		this.database = client.getDatabase(databaseName);
		return database;
	}
	

	/**
	 * 验证mongodb client option的有效性
	 * @exception @Author jingjiwu
	 * @Date 2016年4月15日 下午2:28:17
	 * @since 1.0.0
	 */
	private void validateOptions() throws Exception{
		if (buildOptions == null) {
			throw new Exception("define configure information.");
		}
		if (buildOptions.getHosts()==null || buildOptions.getHosts().size()==0) {
			throw new Exception("mongodb host no definition.");
		}
		try {
			for(String host:buildOptions.getHosts()){
				String[] st = host.trim().split(SPLIT, 2);
				Integer.parseInt(st[1]);
			}
		} catch (Exception e) {
			throw new Exception("mongodb host definition error.");
		}
		if (StringUtils.isBlank(buildOptions.getDatabaseName())) {
			throw new Exception("mongodb database name no definition.");
		}
		if (buildOptions.getMaxWaitTime() <= 0) {
			throw new Exception("invalid maxWaitTime parameters.");
		}
		if (buildOptions.getMaxConnectionsPerHost() <= 0) {
			throw new Exception("invalid maxConnectionsPerHost parameters.");
		}
		if (buildOptions.getThreadsAllowedToBlockForConnectionMultiplier() <= 0) {
			throw new Exception("invalid threadsAllowedToBlockForConnectionMultiplier parameters.");
		}
	}

	/**
	 * 创建一个一般的mongodb client, 不需要用户和密码凭证
	 * @exception @Author jingjiwu
	 * @Date 2016年4月15日 下午2:08:43
	 * @since 1.0.0
	 */
	private void createGeneralClient() {
		client = new MongoClient(this.setServerAddress(buildOptions.getHosts()),getOptions().build());
	}

	/**
	 * 根据凭证创建一个mongodb client
	 * @exception @Author jingjiwu
	 * @Date 2016年4月15日 下午2:11:15
	 * @since 1.0.0
	 */
	private void createCredentialClient() {
		client = new MongoClient(setServerAddress(buildOptions.getHosts()),
				Arrays.asList(createCredential()), getOptions().build());
	}
	
	/**
	 * set hosts
	 * @param hosts 127.0.0.1:12345格式的主机地址
	 * @return ServerAddress对象
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月27日 上午10:18:10
	 * @since 1.0.0
	 */
	private List<ServerAddress> setServerAddress(List<String> hosts){
		List<ServerAddress> serverAddress = Lists.newArrayList();
		for(String host:hosts){
			String[] st = host.trim().split(SPLIT, 2);
			serverAddress.add(new ServerAddress(st[0],Integer.parseInt(st[1])));
		}
		return serverAddress;
	}

	/**
	 * set builder options
	 * @return Builder options
	 * @exception @Author jingjiwu
	 * @Date 2016年4月15日 下午2:12:20
	 * @since 1.0.0
	 */
	private Builder getOptions() {
		Builder options = new MongoClientOptions.Builder();
		options.socketTimeout(buildOptions.getMaxWaitTime());
		options.serverSelectionTimeout(buildOptions.getMaxWaitTime());
		options.maxWaitTime(buildOptions.getMaxWaitTime());
		options.connectionsPerHost(buildOptions.getMaxConnectionsPerHost());
		options.threadsAllowedToBlockForConnectionMultiplier(
				buildOptions.getThreadsAllowedToBlockForConnectionMultiplier());
		return options;
	}

	/**
	 * 根据用户和密码创建凭证
	 * @exception @Author jingjiwu
	 * @Date 2016年4月15日 下午2:15:47
	 * @since 1.0.0
	 */
	private MongoCredential createCredential() {
		return MongoCredential.createCredential(buildOptions.getUserName(), buildOptions.getDatabaseName(),
				buildOptions.getPassword().toCharArray());
	}
}
