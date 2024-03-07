package com.lab8.galaxy.utils;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.lab8.galaxy.entity.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = "8lab";

    public static String generateToken(UserInfo user) {
        //long nowMillis = System.currentTimeMillis();
        //long expMillis = nowMillis + 3600000; // Token有效期1小时
        Map<String, Object> payload = new HashMap<>();
        payload.put("userNumber", user.getUsernumber());
        payload.put("expire_time", System.currentTimeMillis() + 3600000); // 设置过期时间为1小时后
        String token = JWTUtil.createToken(payload, SECRET_KEY.getBytes());
        return token;
    }

    public static boolean validateToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.setKey(SECRET_KEY.getBytes()).validate(0); // 验证Token，含过期时间检查
    }
}
