package com.wanlianjin.cic.mongo.util;

import java.util.Arrays;
import java.util.List;

/**
 * mongodb client build options databaseName,userName,password
 * parameters...
 * 
 * @comment
 * @author jingjiwu
 * @date 2016年4月15日 下午1:55:17
 * @version 1.0.0
 */
public class BuildOptions {
	private List<String> hosts;
	private String databaseName;
	private String userName;
	private String password;
	/**
	 * 最大等待时间(毫秒)
	 */
	private int maxWaitTime = 3000;
	/**
	 * 最大连接数
	 */
	private int maxConnectionsPerHost = 20;
	/**
	 * 最大等待连接数
	 */
	private int threadsAllowedToBlockForConnectionMultiplier = 5;
	private final int MAX_THREADS_ALLOWED_BLOCK = 10;
	

	public List<String> getHosts() {
		return hosts;
	}

	/*public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}*/
	
	/**
	 *设置mongodb主机和端口
	 * @param hosts 格式为host:port,host:port...
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年6月29日 下午4:49:29
	 * @since 1.0.0
	 */
	public void setHosts(String hosts){
		this.hosts = Arrays.asList(hosts.split(","));
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
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

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public int getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}

	public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier > MAX_THREADS_ALLOWED_BLOCK
				? MAX_THREADS_ALLOWED_BLOCK : threadsAllowedToBlockForConnectionMultiplier;
	}
}
