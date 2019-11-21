package com.java.sale.service;

import com.java.sale.common.CodeMsg;
import com.java.sale.dao.mapper.UserDao;
import com.java.sale.domain.User;
import com.java.sale.exception.GlobalException;
import com.java.sale.redis.RedisService;
import com.java.sale.utils.MD5Utils;
import com.java.sale.utils.UUIDUtils;
import com.java.sale.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 曾伟
 * @date 2019/10/24 21:01
 */
@Service
public class UserService {
    public static final String COOKIE_NAME_TOKEN = "token";
    public static final int COOKIE_EXPIRED_TIME = 100000;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisService redisService;

    //@Cacheable(cacheNames = "user")
    public User getById(Long id) {
        return userDao.getUserById(id);
    }

    /**
     * 登陆方法
     *
     * @param loginVo
     */
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //判断手机号是否存在
        User user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String toDBPass = MD5Utils.FromPassToDBPass(password, salt);
        if (!toDBPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成一个cookie
        String token = UUIDUtils.uuid();
        addCookie(user,token,response);
        return true;
    }

    public User getUserByToken(HttpServletResponse response,String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        //得到user对象
        User user = (User) redisService.get(token);
        //更改过期时间：就是重新设置一下cookie.
        if (user!=null){
            addCookie(user,token,response);
        }
        return user;
    }
    //生成一个cookie
    private void addCookie(User user,String token,HttpServletResponse response){
        redisService.set(token, user, COOKIE_EXPIRED_TIME);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(COOKIE_EXPIRED_TIME);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
