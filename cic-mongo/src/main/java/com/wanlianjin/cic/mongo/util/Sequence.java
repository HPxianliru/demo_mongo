package com.wanlianjin.cic.mongo.util;

/**
 * 自动序列号升级接口
 *@comment  
 *@author jingjiwu
 *@date 2016年4月28日 上午10:13:45
 *@version 1.0.0
 */
public interface Sequence {

	/**
	 * 到指定的集合中取一个序列
	 * @param collectionName 集合名字
	 * @return 序列
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月28日 上午10:13:57
	 * @since 1.0.0
	 */
	public int getSequence(String collectionName);

	/**
	 * 到默认的集合中取一个序列
	 * @return 序列
	 * @exception
	 * @Author jingjiwu
	 * @Date 2016年4月28日 上午10:14:20
	 * @since 1.0.0
	 */
	public int getSequence();
}
