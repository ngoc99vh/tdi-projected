package com.example.tdiframework.utils;

import org.springframework.util.ObjectUtils;

public class FunctionUtils {

    /**
     * Kiểm tra các tham số truyền vào là có giá trị
     * @param params
     * @return boolean
     */
    public static boolean checkParams(Object... params) {
        for (Object object : params) {
            if (ObjectUtils.isEmpty(object)) {
                return true;
            }
        }
        return false;
    }
}
