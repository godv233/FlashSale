package com.java.sale.config;

import com.java.sale.access.UserContext;
import com.java.sale.domain.User;
import com.java.sale.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 参数解析器：用户
 * @author 曾伟
 * @date 2019/11/21 15:13
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserService userService;

    /**
     * 判断参数类型
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //参数的判断，是否是user类型，true才会执行下一步
        Class<?> type = methodParameter.getParameterType();
        return type == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
//
//        String paramToken=request.getParameter(UserService.COOKIE_NAME_TOKEN);
//        String cookieToken=getCookieValue(request,UserService.COOKIE_NAME_TOKEN);
//
//        //以此判断两种方式传入token
//        if (StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
//            return null;
//        }
//        String token=StringUtils.isEmpty(paramToken)? cookieToken:paramToken;
//        User user =userService.getUserByToken(response,token);
//        return user;
//    }
//
//    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies==null||cookies.length<=0){
//            return null;
//        }
//        for (Cookie cookie:cookies){
//            if (StringUtils.equals(cookie.getName(),cookieNameToken)){
//                return cookie.getValue();
//            }
//        }
//        return null;
        //以上注解是逻辑，后期放在了拦截器中进行校验，拦截器比参数配置先发生
        return UserContext.getUser();
    }
}
