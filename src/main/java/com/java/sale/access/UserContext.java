package com.java.sale.access;

import com.java.sale.domain.User;

/**
 * 将用户信息保存在threadLocal中
 *
 * @author 曾伟
 * @date 2019/11/27 16:29
 */
public class UserContext {
    private static ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static void setUser(User user) {
        userHolder.set(user);
    }


    public static User getUser() {
        return userHolder.get();
    }

}
