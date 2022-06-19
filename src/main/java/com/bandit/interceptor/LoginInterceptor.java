package com.bandit.interceptor;



import com.bandit.exception.XException;
import com.bandit.utils.JWTComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private final JWTComponent jwtComponent;
    public LoginInterceptor(JWTComponent jwtComponent) {
        this.jwtComponent = jwtComponent;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod().toString())) {
            return true; //true是直接放行，前端抓包会有options请求
            //false拒接访问，抓包就不会有options请求了
        }
        String token = request.getHeader("token");
        if (token == null) {
            throw new XException(401, "未登录");
        }
        long uid = jwtComponent.decode(token).getClaim("uid").asLong();
        int role = jwtComponent.decode(token).getClaim("role").asInt();
        // 拦截解密出用户真实数据后，置于request供后续使用
        request.setAttribute("uid", uid);
        request.setAttribute("role", role);
        return true;
    }


}
