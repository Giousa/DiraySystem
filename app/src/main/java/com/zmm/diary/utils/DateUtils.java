package com.zmm.diary.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/1
 * Email:65489469@qq.com
 */
public class DateUtils {

    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        if(TextUtils.isEmpty(formatType)){
            formatType =  "yyyy-MM-dd";
        }
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }


    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        if(TextUtils.isEmpty(formatType)){
            formatType =  "yyyy-MM-dd";
        }
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        if(TextUtils.isEmpty(formatType)){
            formatType =  "yyyy-MM-dd";
        }
        return new SimpleDateFormat(formatType).format(data);
    }


    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        if(TextUtils.isEmpty(formatType)){
            formatType =  "yyyy-MM-dd";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        if(TextUtils.isEmpty(formatType)){
            formatType =  "yyyy-MM-dd";
        }
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static Date parseTime(String time) {

        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


//    public static String format(Date date) {
//        DateTime now = new DateTime();
//        DateTime today_start = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0);
//        DateTime today_end = today_start.plusDays(1);
//        DateTime yesterday_start = today_start.minusDays(1);
//
//        if(date.after(today_start.toDate()) && date.before(today_end.toDate())) {
//            return String.format("今天 %s", new DateTime(date).toString("HH:mm"));
//        } else if(date.after(yesterday_start.toDate()) && date.before(today_start.toDate())) {
//            return String.format("昨天 %s", new DateTime(date).toString("HH:mm"));
//        }
//
//        return new DateTime(date).toString("yyyy-MM-dd HH:mm");
//    }

    public static String isShowTime(long time){

        try {

            long current = System.currentTimeMillis();
            int curentYeay = Integer.parseInt(longToString(current, "yyyy"));
            int currentMonth = Integer.parseInt(longToString(current, "MM"));
            int currentDay = Integer.parseInt(longToString(current, "dd"));


            int year = Integer.parseInt(longToString(time, "yyyy"));
            int month = Integer.parseInt(longToString(time, "MM"));
            int day = Integer.parseInt(longToString(time, "dd"));

            if(year != curentYeay){
                return longToString(time,"yyyy-MM-dd");
            }else {

                if(month != currentMonth){
                    return longToString(time,"MM-dd");
                }else {
                    if(day != currentDay){
                        return longToString(time,"MM-dd");
                    }else {
                        return longToString(time,"HH:mm");
                    }
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return null;

    }


}
