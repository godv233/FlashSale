package com.java.sale.service;

import com.java.sale.common.CodeMsg;
import com.java.sale.common.Result;
import com.java.sale.dao.mapper.UserDao;
import com.java.sale.domain.User;
import com.java.sale.utils.MD5Utils;
import com.java.sale.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 曾伟
 * @date 2019/10/24 21:01
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    //@Cacheable(cacheNames = "user")
    public User getById(Long id) {
        return userDao.getUserById(id);
    }

    /**
     * 登陆方法
     * @param loginVo
     */
    public CodeMsg login(LoginVo loginVo) {
        if (loginVo==null){
            return CodeMsg.SERVER_ERROR;
        }
        String mobile=loginVo.getMobile();
        String password=loginVo.getPassword();
        //判断手机号是否存在
        User user = getById(Long.parseLong(mobile));
        if (user==null){
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        //验证密码
        String dbPass = user.getPassword();
        String salt=user.getSalt();
        String toDBPass = MD5Utils.FromPassToDBPass(password, salt);
        if(!toDBPass.equals(dbPass)){
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;
    }
}
