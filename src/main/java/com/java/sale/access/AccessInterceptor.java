package com.java.sale.access;

import com.java.sale.common.CodeMsg;
import com.java.sale.common.Result;
import com.java.sale.domain.User;
import com.java.sale.redis.AccessKey;
import com.java.sale.redis.RedisService;
import com.java.sale.service.UserService;
import com.java.sale.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 拦截器
 *
 * @author 曾伟
 * @date 2019/11/27 16:18
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    /**
     * 拦截器：方法执行之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            //保存user
            User user = getUser(request, response);
            UserContext.setUser(user);
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            } else {
                int seconds = accessLimit.seconds();
                int maxCount = accessLimit.maxCount();
                //将判断登录的逻辑可以放在这里
                boolean needLogin = accessLimit.needLogin();
                String key = request.getRequestURI();
                if (needLogin) {
                    if (user == null) {
                        render(response, CodeMsg.SESSION_ERROR);//返回前端页面一些错误的信息
                        return false;
                    }
                    key += "_" + user.getId();
                    //限流的核心代码
                    Integer count = (Integer) redisService.get(AccessKey.withExpire(seconds), key);
                    if (count == null) {
                        redisService.set(AccessKey.withExpire(seconds), key, 1);
                    } else if (count < maxCount) {
                        redisService.incr(AccessKey.withExpire(seconds), key);
                    } else {
                        render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 在拦截器中抛出异常给前台
     *
     * @param response
     * @param sessionError
     */
    private void render(HttpServletResponse response, CodeMsg sessionError) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        OutputStream out = response.getOutputStream();
        String str = JsonUtils.beanToString(Result.error(sessionError));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();

    }

    /**
     * 通过请求中的token得到user，可检验登录
     * @param request
     * @param response
     * @return
     */
    private User getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, UserService.COOKIE_NAME_TOKEN);

        //以此判断两种方式传入token
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        User user = userService.getUserByToken(response, token);
        return user;
    }

    /**
     * 在cookie中取值
     * @param request
     * @param cookieNameToken
     * @return
     */
    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), cookieNameToken)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
