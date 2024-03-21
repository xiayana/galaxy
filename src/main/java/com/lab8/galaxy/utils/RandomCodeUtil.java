package com.lab8.galaxy.utils;

import java.security.SecureRandom;
import java.util.Locale;

public class RandomCodeUtil {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBER = "0123456789";
    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + NUMBER;
    private static final SecureRandom random = new SecureRandom();

    /**
     * 生成一个包含随机字母和数字的5位随机码。
     * @return 返回生成的随机码。
     */
    public static String generateRandomCode() {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            // 生成0到DATA_FOR_RANDOM_STRING长度-1的随机数
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            // 根据随机数获取字符
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            // 将字符拼接到StringBuilder
            sb.append(rndChar);
        }
        return sb.toString();
    }
}
