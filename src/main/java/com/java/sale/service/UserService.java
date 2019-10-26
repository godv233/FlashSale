package com.java.sale.service;

import com.java.sale.dao.mapper.UserDao;
import com.java.sale.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 曾伟
 * @date 2019/10/24 21:01
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Cacheable(cacheNames = "user")
    public User getById(int id){
        return userDao.getUserById(id);
    }
}
