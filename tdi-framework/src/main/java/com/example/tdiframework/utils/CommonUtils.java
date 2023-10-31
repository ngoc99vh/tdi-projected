package com.example.tdiframework.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommonUtils {
    public CommonUtils() {
    }

    /**
     * Khởi tạo chuỗi UUID
     *
     * @return String
     */
    public String generateUUIDString() {
        return UUID.randomUUID().toString();
    }
}