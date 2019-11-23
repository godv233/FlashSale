package com.java.sale.redis;

public interface KeyPrefix {
		
	int expireSeconds();
	
	String getPrefix();
	
}
