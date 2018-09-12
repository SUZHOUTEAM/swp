package com.mlsc.common.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Objects;

/**
 * 日期工具类
 */
public class DateUtil {

    public static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String DATETIME_PATTERN_START = "yyyy-MM-dd 00:00:00";
    public static String DATETIME_PATTERN_END = "yyyy-MM-dd 23:59:59";
    public static String DATETIME_PATTERN_MONTH = "yyyy-MM";
    public static DateTimeFormatter format = DateTimeFormat.forPattern(DATETIME_PATTERN);
    public static DateTimeFormatter format_start = DateTimeFormat.forPattern(DATETIME_PATTERN_START);
    public static DateTimeFormatter format_end = DateTimeFormat.forPattern(DATETIME_PATTERN_END);
    public static DateTimeFormatter format_month = DateTimeFormat.forPattern(DATETIME_PATTERN_MONTH);

    public static String convertToString(DateTime dateTime) {
        Objects.requireNonNull(dateTime);
        return dateTime.toString(format);
    }

    public static DateTime convertToDateTime(String dateTimeStr) {
        Objects.requireNonNull(dateTimeStr);
        return format.parseDateTime(dateTimeStr);
    }

    public static DateTime convertToStartDate(DateTime dateTime){
        Objects.requireNonNull(dateTime);
        return convertToDateTime(dateTime.toString(format_start));
    }
    public static Date convertToStartDate(Date dateTime){
        Objects.requireNonNull(dateTime);
        return convertToDateTime(new DateTime(dateTime).toString(format_start)).toDate();
    }
    public static DateTime convertToEndDate(DateTime dateTime){
        Objects.requireNonNull(dateTime);
        return convertToDateTime(dateTime.toString(format_end));
    }
    public static Date convertToEndDate(Date dateTime){
        Objects.requireNonNull(dateTime);
        return convertToDateTime(new DateTime(dateTime).toString(format_end)).toDate();
    }

    public static String convertToMonthString(Date date){
        Objects.requireNonNull(date);
        return new DateTime(date).toString(format_month);
    }

    public static String convertToString(Date date,String pattern){
        DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        return new DateTime(date).toString(formatter);
    }

}
