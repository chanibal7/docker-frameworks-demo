package com.otv.cache.service;

import java.util.concurrent.ConcurrentHashMap;

import com.otv.user.User;

/**
 * Cache Service Interface
 * 
 * @author  onlinetechvision.com
 * @since   10 Mar 2012
 * @version 1.0.0
 *
 */
public interface ICacheService {

	/**
	 * Get User Map
	 * 
	 * @return ConcurrentHashMap User Map
	 */
	public ConcurrentHashMap<Long, User> getUserMap();
	
}
