/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtz.spring.service;

import com.mtz.spring.dto.User;
import java.util.List;

/**
 *
 * @author salemmo
 */
public interface UserService {
    public User getUser(int id);
    public User addUser(User user);
    public User removeUser(User user);
    public List<User> getAllUsers();
}
