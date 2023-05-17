package com.wasupstudio.util;

import java.util.Date;

public class ValueValidator {

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNullOrWhiteSpace(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNullOrZero(Integer value) {
        return value == null || value == 0;
    }

    public static boolean isNullOrZero(Double value) {
        return value == null || value == 0.0;
    }

    public static boolean isNullOrZero(Float value) {
        return value == null || value == 0.0f;
    }

    public static boolean isNullOrZero(Long value) {
        return value == null || value == 0L;
    }

    public static boolean isNullOrZero(Short value) {
        return value == null || value == 0;
    }

    public static boolean isNullOrZero(Byte value) {
        return value == null || value == 0;
    }

    public static boolean isNullOrZero(Number value) {
        return value == null || value.equals(0);
    }

    public static boolean isNullOrEmpty(Date value) {
        return value == null;
    }
}

