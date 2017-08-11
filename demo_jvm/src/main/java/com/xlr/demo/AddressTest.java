package com.xlr.demo;

import com.xlr.demo.util.AddressUtils;

public class AddressTest {
	
	public static void main(String[] args) {
		System.out.println(AddressUtils.isLocalIP("100.39.8.5"));
		System.out.println(AddressUtils.getLocalIP("100.39.8"));
	}
}
