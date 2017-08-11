package com.xlr.demo.entity;

public class MemoryInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MemoryInfo() {
	}
	//内存区
	private String vmName;
	//所属内存管理者
	private String vmOwner;
	//初始大小（M）
	private long initialSize;
	//最大上限M
	private long maxSize;
	//已使用大小M
	private long useSize;
	//申请大小
	private long committedSize;
	//使用率
	private double utilizationRate;
	
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getVmOwner() {
		return vmOwner;
	}
	public void setVmOwner(String vmOwner) {
		this.vmOwner = vmOwner;
	}
	
	public long getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(long initialSize) {
		this.initialSize = initialSize;
	}
	public long getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}
	public long getUseSize() {
		return useSize;
	}
	public void setUseSize(long useSize) {
		this.useSize = useSize;
	}
	public long getCommittedSize() {
		return committedSize;
	}
	public void setCommittedSize(long committedSize) {
		this.committedSize = committedSize;
	}
	public double getUtilizationRate() {
		return useSize*100/committedSize;
	}
	public void setUtilizationRate(double utilizationRate) {
		this.utilizationRate = utilizationRate;
	}
	public static void main(String[] args) {
		System.err.println(25*100/100);
	}
	@Override
	public String toString() {
		return "MemoryInfo [vmName=" + vmName + ", vmOwner=" + vmOwner
				+ ", initialSize=" + initialSize + ", maxSize=" + maxSize
				+ ", useSize=" + useSize + ", committedSize=" + committedSize
				+ ", utilizationRate=" + utilizationRate + "]";
	}
	
}
