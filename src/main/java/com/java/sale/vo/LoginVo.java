package com.java.sale.vo;

import com.java.sale.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author 曾伟
 * @date 2019/10/26 16:22
 */
@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;

    @Length(min = 32,max = 32)
    private String password;
}
