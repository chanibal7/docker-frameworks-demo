package com.otv.http.server;

import java.util.List;

import com.otv.user.User;

/**
 * Http User Service Interface
 * 
 * @author  onlinetechvision.com
 * @since   24 Feb 2012
 * @version 1.0.0
 *
 */
public interface IHttpUserService{

	/**
	 * Add User
	 * 
	 * @param  User user
	 * @return boolean response of the method
	 */
	public boolean addUser(User user);
	
	/**
	 * Delete User
	 * 
	 * @param  User user
	 * @return boolean response of the method
	 */
	public boolean deleteUser(User user);
	
	
	/**
	 * Get User List
	 * 
	 * @return List user list
	 */
	public List<User> getUserList();
	
}
