package com.java.sale.exception;

import com.java.sale.common.CodeMsg;
import lombok.Data;

/**自定义全局异常，封装CodeMsg
 * @author 曾伟
 * @date 2019/11/10 16:53
 */
@Data
public class GlobalException extends  RuntimeException {
    private  CodeMsg cm;
    public GlobalException  (CodeMsg msg){
        super(msg.toString());
        this.cm=msg;
     }

}
