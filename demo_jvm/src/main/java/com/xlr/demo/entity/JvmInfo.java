package com.xlr.demo.entity;

import java.util.ArrayList;


public class JvmInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//内存信息
	ArrayList<MemoryInfo> memorys = new ArrayList<MemoryInfo>();
	//GC信息
	ArrayList<GcInfo> gcInfos = new ArrayList<GcInfo>();
	//当前毫秒时间
	private long runTime;
	//启动时间yyyy-MM-dd HH:mm:ss
	private String startTime;
	//运行时间
	private long uptime;
	//进程 pid
	private String pId;
	//jvm 名称
	private String jvmName;
	//jvm运营商
	private String jvmOperate;
	//jvm版本
	private String jvmVersion;
	
	private String ip;
	
	public ArrayList<MemoryInfo> getMemorys() {
		return memorys;
	}
	public void setMemorys(ArrayList<MemoryInfo> memorys) {
		this.memorys = memorys;
	}
	public ArrayList<GcInfo> getGcInfos() {
		return gcInfos;
	}
	public void setGcInfos(ArrayList<GcInfo> gcInfos) {
		this.gcInfos = gcInfos;
	}
	public long getRunTime() {
		return runTime;
	}
	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public long getUptime() {
		return uptime;
	}
	public void setUptime(long uptime) {
		this.uptime = uptime;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getJvmName() {
		return jvmName;
	}
	public void setJvmName(String jvmName) {
		this.jvmName = jvmName;
	}
	public String getJvmOperate() {
		return jvmOperate;
	}
	public void setJvmOperate(String jvmOperate) {
		this.jvmOperate = jvmOperate;
	}
	public String getJvmVersion() {
		return jvmVersion;
	}
	public void setJvmVersion(String jvmVersion) {
		this.jvmVersion = jvmVersion;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
	public String toString() {
		return "JvmInfo [memorys=" + memorys + ", gcInfos=" + gcInfos
				+ ", runTime=" + runTime + ", startTime=" + startTime
				+ ", uptime=" + uptime + ", pId=" + pId + ", jvmName="
				+ jvmName + ", jvmOperate=" + jvmOperate + ", jvmVersion="
				+ jvmVersion + "]";
	}
	
}
