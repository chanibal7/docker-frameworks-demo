/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtz.spring.service;

import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.transaction.annotation.Transactional;

import com.mtz.spring.dao.UserDAO;
import com.mtz.spring.dto.User;

/**
 *
 * @author salemmo
 */
@RemoteProxy
public class UserServiceImpl implements UserService{

    
    @RemoteMethod
    @Transactional
    @Override
    public User getUser(int id) {
        return getUserDAO().getUser(id);
    }

    @Transactional
    @RemoteMethod
    @Override
    public User addUser(User user) {
        return getUserDAO().addUser(user);
    }
    
    @Transactional
    @RemoteMethod
    public User removeUser(User user) {
        return getUserDAO().removeUser(user);
    }

    @Transactional
    @RemoteMethod
    @Override
    public List<User> getAllUsers() {
        return getUserDAO().getAllUsers();
    }
    
    private UserDAO userDAO;
    

    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * @param userDAO the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    
}
