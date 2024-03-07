package com.lab8.galaxy.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 时间格式化工具类
 * @author xy
 * @since 2022-03-23 18:17:42
 */
@Slf4j
@Component
public class DateFormateUtil {

    public Date formate(String date){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateStart = simpleDateFormat.parse(date);
            return dateStart;
        }catch (Exception e){
            log.error("date formate error,date:[{}],formate:[{}]",date,date,e);
            return null;
        }
    }
    public  String format(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
             log.error("Error formatting date: " + date, e);
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> retrieveDatePeriod() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        // 设置为昨天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String endDateString = sdf.format(calendar.getTime());

        // 设置为14天前
        calendar.add(Calendar.DAY_OF_MONTH, -13);
        String startDateString = sdf.format(calendar.getTime());

        List<String> dateList = new ArrayList<>();
        while (!startDateString.equals(endDateString)) {
            dateList.add(startDateString);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            startDateString = sdf.format(calendar.getTime());
        }
        dateList.add(endDateString);

        return dateList;
    }
    /**
     * 将给定的时间字符串转换为UTC时间格式的字符串。
     *
     * @param localTimeStr 本地时间字符串
     * @param format       时间格式（例如 "yyyy-MM-dd HH:mm:ss"）
     * @return UTC时间格式的字符串
     */
    public static String convertToUTCString(String localTimeStr, String format) {
        SimpleDateFormat localFormatter = new SimpleDateFormat(format);
        localFormatter.setTimeZone(TimeZone.getDefault()); // 设置为本地时区

        SimpleDateFormat utcFormatter = new SimpleDateFormat(format);
        utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC")); // 设置为UTC时区

        try {
            Date localDate = localFormatter.parse(localTimeStr);
            return utcFormatter.format(localDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // 或者根据需要处理异常
        }
    }

}
