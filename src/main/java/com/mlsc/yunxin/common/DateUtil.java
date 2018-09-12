package com.mlsc.yunxin.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by yinxl on 2017/7/13.
 */
public class DateUtil {

    static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 采用默认日期格式 yyyy-MM-dd HH:mm:ss
     * @param dateTime
     * @return
     */
    public static String getDateStr(DateTime dateTime) {
        return dateTimeFormatter.print(dateTime);
    }

    public static String getDateStr(DateTime dt, String pattern) {
        return DateTimeFormat.forPattern(pattern).print(dt);
    }

    /**
     * 采用默认日期格式 yyyy-MM-dd HH:mm:ss
     * @param dateTimeStr
     * @return
     */
    public static DateTime getDateTime(String dateTimeStr) {
        return dateTimeFormatter.parseDateTime(dateTimeStr);
    }

    public static DateTime getDateTime(String dateTimeStr, String pattern) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(dateTimeStr);
    }

}
