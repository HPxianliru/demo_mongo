package com.xlr.demo.entity;

public class LocaConfigure implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//总物理内存
	private long totalPhysicalMemory;
	//使用物理内存
	private long usedPhysicalMemorySize;
	//空闲物理内存
	private long freePhysicalMemory;
	//总交换空间
	private long totalSwapSpaceSize;
	//已用交换空间
	private long usedSwapSpaceSize;
	//空闲交换空间
	private long freeSwapSpaceSize;
	//ip
	private String ip;
	//进程 pid
	private String pId;
	
	public long getTotalPhysicalMemory() {
		return totalPhysicalMemory;
	}
	public void setTotalPhysicalMemory(long totalPhysicalMemory) {
		this.totalPhysicalMemory = totalPhysicalMemory;
	}
	public long getUsedPhysicalMemorySize() {
		return usedPhysicalMemorySize;
	}
	public void setUsedPhysicalMemorySize(long usedPhysicalMemorySize) {
		this.usedPhysicalMemorySize = usedPhysicalMemorySize;
	}
	public long getFreePhysicalMemory() {
		return freePhysicalMemory;
	}
	public void setFreePhysicalMemory(long freePhysicalMemory) {
		this.freePhysicalMemory = freePhysicalMemory;
	}
	public long getTotalSwapSpaceSize() {
		return totalSwapSpaceSize;
	}
	public void setTotalSwapSpaceSize(long totalSwapSpaceSize) {
		this.totalSwapSpaceSize = totalSwapSpaceSize;
	}
	public long getUsedSwapSpaceSize() {
		return usedSwapSpaceSize;
	}
	public void setUsedSwapSpaceSize(long usedSwapSpaceSize) {
		this.usedSwapSpaceSize = usedSwapSpaceSize;
	}
	public long getFreeSwapSpaceSize() {
		return freeSwapSpaceSize;
	}
	public void setFreeSwapSpaceSize(long freeSwapSpaceSize) {
		this.freeSwapSpaceSize = freeSwapSpaceSize;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	@Override
	public String toString() {
		return "LocaConfigure [totalPhysicalMemory=" + totalPhysicalMemory
				+ ", usedPhysicalMemorySize=" + usedPhysicalMemorySize
				+ ", freePhysicalMemory=" + freePhysicalMemory
				+ ", totalSwapSpaceSize=" + totalSwapSpaceSize
				+ ", usedSwapSpaceSize=" + usedSwapSpaceSize
				+ ", freeSwapSpaceSize=" + freeSwapSpaceSize + "]";
	}
	
}
