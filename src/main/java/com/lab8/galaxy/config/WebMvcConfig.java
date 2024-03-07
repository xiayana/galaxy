package com.lab8.galaxy.config;

import com.lab8.galaxy.security.AuthInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${security.enable}")
    private boolean isSecurityEnabled;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (isSecurityEnabled) {
            registry.addInterceptor(new AuthInterceptor())
                    .addPathPatterns("/**") // 拦截所有请求
                    .excludePathPatterns("/orderlist/selectOrder","/orderlist/export/orderlists","/orderlist/isRepeated","/projectInfo/test","/orderlist/selectCOrder","/orderlist/selectFaddress","/whtielist/queryByWhitelist","/projectInfo/selectOaInfo","/projectInfo/dashboard","/projectInfo/launchById","/whtielist/launchById","/transaction/selectTran","/orderlist/selectOrder","/projectInfo/selectInfo","/transaction/selectTran","/projectInfo/{id}","/whtielist/{id}","/projectDetails/{id}","/orderlist/oderListSave"); // 排除登录和注册接口
        }
        // 如果安全检查被禁用，则不添加拦截器
    }

}
