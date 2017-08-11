package com.xlr.demo.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class AddressUtils {

	private static String localIP;
	private static Set<String> localAddresses = new HashSet<String>();
	static {
		try {
			/*InetAddress address = InetAddress.getLocalHost();
			localIP = address.getHostAddress().toString();*/
			/* 获取所有网卡信息*/
			for(Enumeration<NetworkInterface> i = NetworkInterface.getNetworkInterfaces(); i.hasMoreElements(); ) {
	            NetworkInterface ni = i.nextElement();
	            for(Enumeration<InetAddress> j = ni.getInetAddresses(); j.hasMoreElements(); ) {
	            	String localAddress = j.nextElement().toString().substring(1);
	            	localAddresses.add(localAddress);
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前应用服务器IP
	 * @return
	 */
	public static String getLocalIP(String prefix) {
		if(localIP != null) return localIP;
		for(String address : localAddresses) {
			if(address.startsWith(prefix)) {
				localIP = address;
				break;
			}
		}
		return localIP;
	}
	
	/**
	 * 是否本地某一网卡IP
	 * @param address
	 * @return
	 */
	public static boolean isLocalIP(String address) {
		return localAddresses.contains(address);
	}
}
