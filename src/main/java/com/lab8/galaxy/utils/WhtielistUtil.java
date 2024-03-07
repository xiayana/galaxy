package com.lab8.galaxy.utils;
import com.lab8.galaxy.entity.Whtielist;
import org.apache.commons.lang3.reflect.FieldUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class WhtielistUtil {

    public static boolean areAllFieldsEmpty(Whtielist whtielist) {
        if (whtielist == null) {
            return true; // 如果整个对象是 null，则认为所有字段都是空的
        }

        for (Field field : FieldUtils.getAllFieldsList(whtielist.getClass())) {
            // 忽略静态字段
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            // 忽略特定字段
            String fieldName = field.getName();
            if ("pid".equals(fieldName) || "type".equals(fieldName)) {
                continue;
            }

            try {
                Object value = FieldUtils.readField(field, whtielist, true);
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
        return true; // 所有非静态且非特定字段都为空或空字符串
    }
}
