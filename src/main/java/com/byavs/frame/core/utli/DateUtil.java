package com.byavs.frame.core.utli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qibin.long on 2017/4/14.
 */
public final class DateUtil {
    public static final String DATE_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SMALL = "yyyy-MM-dd";
    public static final String DATE_DIGIT_FULL = "yyyyMMddHHmmss";
    public static final String DATE_DIGIT_SMALL = "yyyyMMdd";

    /**
     * 根据指定格式提取日期
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期为字符串
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format2String(Date date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static Date format(Date date, String pattern) {
        return parse(format2String(date, pattern), pattern);
    }

    /**
     * 和当前日期比较,只比较日期，不比较时间
     *
     * @param date
     * @return
     */
    public static int compareToday(Date date) {
        return compareToday(date, new Date());
    }

    /**
     * date1和date2比较，只比较日期，不比较时间
     *
     * @return
     */
    public static int compareToday(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return 0;
        }
        if (date1 == null) {
            return -1;
        }
        if (date2 == null) {
            return 1;
        }
        Date formatDate1 = format(date1, DATE_SMALL);
        Date formatDate2 = format(date2, DATE_SMALL);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(formatDate1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(formatDate2);
        return cal1.compareTo(cal2);
    }

    /**
     * date1和date2比较
     *
     * @return
     */
    public static int compareTime(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.compareTo(cal2);
    }

    /**
     * date1减date2，返回天数
     *
     * @return
     */
    public static long subtractOfDay(Date date1, Date date2) {
        Date formatDate1 = format(date1, DATE_SMALL);
        Date formatDate2 = format(date2, DATE_SMALL);
        return (formatDate1.getTime() - formatDate2.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 日期加指定天数,返回日期部分
     *
     * @param date
     * @return
     */
    public static Date addDays(Date date, int days) {
        Date formatDate = parse(format2String(date, DATE_SMALL), DATE_SMALL);
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatDate);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * 日期加指定小时,返回日期部分
     *
     * @param date
     * @return
     */
    public static Date addHours(Date date, int hours) {
        date = format(date, DATE_FULL);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     * 日期加指定分钟,返回日期部分
     *
     * @param date
     * @return
     */
    public static Date addMinutes(Date date, int minutes) {
        date = format(date, DATE_FULL);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    /**
     * 返回指定日期那周的第一天
     *
     * @param date
     * @return
     */
    public static Date firstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //设置周第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 日期加指定月分，返回该月第一天
     *
     * @param date
     * @param months
     * @return
     */
    public static Date firstDayOfMonth(Date date, int months) {
        Date formatDate = parse(format2String(date, DATE_SMALL), DATE_SMALL);
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatDate);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 日期加指定年分，返回该年第一天
     *
     * @param date
     * @param years
     * @return
     */
    public static Date firstDayOfYear(Date date, int years) {
        Date formatDate = parse(format2String(date, DATE_SMALL), DATE_SMALL);
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatDate);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    /**
     * 获取系统当前日期
     * yyyy-MM-dd
     *
     * @return
     */
    public static String getNowDate() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_SMALL);
        return df.format(new Date());
    }

    /**
     * 获取系统当前日期
     *
     * @return
     */
    public static String getNowDate(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }

    /**
     * 日期加指定年份，返回该年日期
     *
     * @param date  日期
     * @param years 年份
     * @return 该年日期
     */
    public static Date addYears(Date date, int years) {
        Date formatDate = parse(format2String(date, DATE_SMALL), DATE_SMALL);
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatDate);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    /**
     * 校验字符日期格式
     *
     * @param date    字符日期
     * @param pattern 日期格式
     * @return boolean
     */
    public static boolean isStrDate(String date, String pattern) {
        String d = format2String(parse(date, pattern), pattern);
        if (date.equals(d))
            return true;
        return false;
    }

    /**
     * 校验日期格式
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return boolean
     */
    public static boolean isDate(Date date, String pattern) {
        String d = format2String(date, pattern);
        return isStrDate(d, pattern);
    }

    /**
     * 获取日期是年份的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Date formatDate = parse(format2String(date, DATE_SMALL), DATE_SMALL);
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatDate);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
}
