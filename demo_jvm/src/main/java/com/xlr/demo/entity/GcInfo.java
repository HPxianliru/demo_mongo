package com.xlr.demo.entity;

public class GcInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//GC名称
	private String gcName;
	//执行次数
	private int executeCount;
	//总计用时 long
	private long TotalTime;
	//内存区名称
	private String mpoolName;
	
	public String getGcName() {
		return gcName;
	}
	public void setGcName(String gcName) {
		this.gcName = gcName;
	}
	public int getExecuteCount() {
		return executeCount;
	}
	public void setExecuteCount(int executeCount) {
		this.executeCount = executeCount;
	}
	public long getTotalTime() {
		return TotalTime;
	}
	public void setTotalTime(long totalTime) {
		TotalTime = totalTime;
	}
	
	public String getMpoolName() {
		return mpoolName;
	}
	public void setMpoolName(String mpoolName) {
		this.mpoolName = mpoolName;
	}
	@Override
	public String toString() {
		return "GcInfo [gcName=" + gcName + ", executeCount=" + executeCount
				+ ", TotalTime=" + TotalTime + ", mpoolName=" + mpoolName + "]";
	}
	
}
