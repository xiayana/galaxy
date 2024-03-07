package com.lab8.galaxy.utils;

import org.apache.commons.lang3.reflect.FieldUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class ObjectFieldChecker {

    public static boolean areAllFieldsEmpty(Object object, List<String> ignoreFields) {
        if (object == null) {
            return true; // 如果整个对象是 null，则认为所有字段都是空的
        }

        for (Field field : FieldUtils.getAllFieldsList(object.getClass())) {
            // 忽略静态字段和指定的忽略字段
            if (Modifier.isStatic(field.getModifiers()) || ignoreFields.contains(field.getName())) {
                continue;
            }

            try {
                Object value = FieldUtils.readField(field, object, true);
                // 对于字符串类型，检查是否为空或空字符串
                if (value instanceof String && !((String) value).trim().isEmpty()) {
                    return false;
                }
                // 对于其他类型的字段，只检查是否为 null
                else if (!(value instanceof String) && value != null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field value", e);
            }
        }
        return true; // 所有非静态且非忽略字段都为空或空字符串
    }
}
