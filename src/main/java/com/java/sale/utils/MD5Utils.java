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

    public static String inputPassToFromPass(String inputPass) {
        String pass =inputPass+salt;
        return DigestUtils.md5Hex(pass);
    }

    public static String FromPassToDBPass(String pass, String salt) {
        String fromPass = pass + salt;
        return DigestUtils.md5Hex(fromPass);
    }
    public static String inputPassToDbPass(String pass,String salt){
        String fromPass=inputPassToFromPass(pass);
        String dbPass=FromPassToDBPass(fromPass,salt);
        return dbPass;
    }

}
