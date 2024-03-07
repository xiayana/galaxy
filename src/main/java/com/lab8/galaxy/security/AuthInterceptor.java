package com.lab8.galaxy.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab8.galaxy.entity.ResultData;
import com.lab8.galaxy.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String path = request.getServletPath();
        if (path.contains("login") || path.contains("launchById") || path.contains("queryByWhitelist") || path.contains("oderListSave")) {
            return true; // 凭证有效，允许访问
        }
        String token = request.getHeader("Authorization");
        if (token != null && JwtUtils.validateToken(token)) { // 使用 Hutool 的验证方法
            return true; // 凭证有效，允许访问
        }
        // 如果 token 无效，则构建自定义的错误响应
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401 Unauthorized
        response.setContentType("application/json;charset=UTF-8");
        ResultData resultData = new ResultData();
        resultData.setCode(HttpStatus.UNAUTHORIZED.value());
        resultData.setMsg("Token is invalid or expired");
        response.getWriter().write(new ObjectMapper().writeValueAsString(resultData));
        return false; // 拦截请求
    }

}
