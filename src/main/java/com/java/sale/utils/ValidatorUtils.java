package com.java.sale.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断格式正确
 *
 * @author 曾伟
 * @date 2019/11/10 14:45
 */
public class ValidatorUtils {
    //正则表达式简单验证
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher m = mobile_pattern.matcher(mobile);
        return m.matches();
    }
}
