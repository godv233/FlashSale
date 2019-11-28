package com.java.sale.redis;

/**
 * key的接口
 */
public interface KeyPrefix {
		
	int expireSeconds();
	
	String getPrefix();
	
}
