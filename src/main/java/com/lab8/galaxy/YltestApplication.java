package com.lab8.galaxy;

import cn.hutool.crypto.SecureUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lab8.bitMatch.dao")
public class YltestApplication {

    public static void main(String[] args) {
        SecureUtil.disableBouncyCastle();
        SpringApplication.run(YltestApplication.class, args);
    }

}
