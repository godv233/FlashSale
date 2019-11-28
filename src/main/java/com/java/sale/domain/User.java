package com.java.sale.domain;

import lombok.Data;

import java.util.Date;

/**
 * 用户
 * @author 曾伟
 * @date 2019/10/24 20:58
 */
@Data
public class User {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date LastLoginDate;
    private Integer loginCount;

}
