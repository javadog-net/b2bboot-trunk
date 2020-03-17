package com.haier.order.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatHelper {
    private static String FORMAT_DATETIME_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_STR = "yyyy-MM-dd";

    private DateFormatHelper() {
    }

    public static String dateTimeFormat(String formatstr, long time) {
        return DateFormatUtils.format(time, formatstr);
    }

    public static String dateTimeFormat(long time) {
        return dateTimeFormat(FORMAT_DATETIME_STR, time);
    }

    public static String getTimeStr(String formatStr, Date d) {
        return DateFormatUtils.format(d.getTime(), formatStr);
    }

    public static String getTimeStr(Date d) {
        return getTimeStr(FORMAT_DATETIME_STR, d);
    }

    public static String getNowTimeStr(String formatStr) {
        return DateFormatUtils.format((new Date()).getTime(), formatStr);
    }

    public static String getNowTimeStr() {
        return getNowTimeStr(FORMAT_DATETIME_STR);
    }

    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(5, now.get(5) - day);
        return now.getTime();
    }

    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(5, now.get(5) + day);
        return now.getTime();
    }

    public static Date toDate(String sDate, String formatStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        Date dt = null;

        try {
            dt = formatter.parse(sDate);
        } catch (Exception var5) {
            var5.printStackTrace();
            dt = null;
        }

        return dt;
    }

    public static Date toDate(String sDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATETIME_STR);
        Date dt = null;

        try {
            dt = formatter.parse(sDate);
        } catch (Exception var4) {
            var4.printStackTrace();
            dt = null;
        }

        return dt;
    }
}
