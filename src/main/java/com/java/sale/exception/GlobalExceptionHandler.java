package com.java.sale.exception;

import com.java.sale.common.CodeMsg;
import com.java.sale.common.Result;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;

import java.util.List;

/**
 * 全局异常处理器.相当于一个controller
 * @author 曾伟
 * @date 2019/11/10 16:27
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        if (e instanceof BindException){//参数绑定异常
            BindException exception=(BindException) e;
            List<ObjectError> errors=exception.getAllErrors();//得到异常列表
            ObjectError error=errors.get(0);//只得到第一个
            String msg=error.getDefaultMessage();//得到信息
            //返回一个绑定了参数的codemsg

            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else if (e  instanceof GlobalException){//全局异常
            GlobalException globalException=(GlobalException) e;
            CodeMsg codeMsg = globalException.getCm();
            return Result.error(codeMsg);
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}

