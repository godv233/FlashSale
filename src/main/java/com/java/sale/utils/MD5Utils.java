package com.java.sale.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * md5加密
 *
 * @author 曾伟
 * @date 2019/10/26 15:08
 */
public class MD5Utils {
    //用于额外加密的固定的salt
    private final static String salt = "1a2b3c";

    /**
     * 输入加上salt，加密一次
     * @param inputPass
     * @return
     */
    public static String inputPassToFromPass(String inputPass) {
        String pass =inputPass+salt;
        return DigestUtils.md5Hex(pass);
    }

    /**
     *输入加上salt，加密一次
     * @param pass
     * @param salt
     * @return
     */
    public static String FromPassToDBPass(String pass, String salt) {
        String fromPass = pass + salt;
        return DigestUtils.md5Hex(fromPass);
    }

    /**
     * 两次md5.：将用户的密码直接转成数据库存储的密码
     * 这两次MD5中的salt,一次是后端写好的，一次是数据库存储的
     * @param salt
     * @return
     */
    public static String inputPassToDbPass(String pass,String salt){
        String fromPass=inputPassToFromPass(pass);
        String dbPass=FromPassToDBPass(fromPass,salt);
        return dbPass;
    }

}
