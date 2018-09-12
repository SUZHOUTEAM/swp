package com.mlsc.waste.utils;

import java.math.BigDecimal;

public final class ArithUtil {
    private static final int DEF_DIV_SCALE = 10;
  
    private static final int SCALE = 6;

    private ArithUtil() {
    }

    public static float add(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.add(b2).floatValue();
    }

    public static float sub(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.subtract(b2).floatValue();
    }

    public static float mul(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.multiply(b2).floatValue();
    }

    public static float div(float v1, float v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    public static float div(float v1, float v2, int scale) {
        if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Float.toString(v1));
            BigDecimal b2 = new BigDecimal(Float.toString(v2));
            return b1.divide(b2, scale, SCALE).floatValue();
        }
    }

    public static float round(float v, int scale) {
        if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b = new BigDecimal(Float.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, SCALE).floatValue();
        }
    }

    public static float convertsToFloat(float v) {
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    public static int convertsToInt(float v) {
        BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }

    public static long convertsToLong(float v) {
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }

    public static float returnMax(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).floatValue();
    }

    public static float returnMin(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).floatValue();
    }

    public static int compareTo(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }
}
