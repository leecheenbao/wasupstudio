package com.wasupstudio.util;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Slf4j
public class DateUtils {

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_X = "yyyy-MM-dd HH:mm:ss X";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String DF_YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD_CN = "yyyy年MM月dd HH:mm";
    public static final String YYYYMMDDHH = "yyyyMMddHH";
    public static final String HH_MM_SS = "HH:mm:ss";

    public static final Integer ONE_HUNDRED_DAY = 100;

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

    private static Calendar startDate = Calendar.getInstance();
    private static Calendar endDate = Calendar.getInstance();

    // 計算給定日期加上指定天數後的日期
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    // 計算給定日期加上指定小時數後的日期
    public static Date addHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    // 計算給定日期加上指定分鐘數後的日期
    public static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    // 計算給定日期加上指定秒數後的日期
    public static Date addSeconds(Date date, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }
    public static Date addMin(int f, Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.MINUTE, f);
        return calendar.getTime();
    }

    /**
     * 在指定時間上，增加或減少指定天數
     */
    public static Date addDay(int f, Date dateTime) {
        if (null == dateTime) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.DATE, f);
        return calendar.getTime();
    }

    /**
     * 在指定時間上，增加或減少指定月數
     */
    public static Date addMonth(int f, Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.MONTH, f);
        return calendar.getTime();
    }

    /**
     * 在指定時間上，增加或減少指定小時
     */
    public static Date addHour(int f, Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.HOUR_OF_DAY, f);
        return calendar.getTime();
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String format(Date date) {
        return format(date, YYYY_MM_DD_HH_MM_SS);
    }

    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return DATE_TIME_FORMATTER.format(dateTime);
    }

    public static String format(final LocalDateTime dateTime, final String format) {
        if (dateTime == null) {
            return "";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(dateTime);
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getStrCurDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Date toDate(String str, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parse(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (Exception e) {

        }
        return new Date();
    }
    public static Date toDates(String str, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.parse(str);
        } catch (Exception e) {

        }
        return new Date();
    }

    public static Date getStartWithCurrentDay(Date currentTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String format8(Date date) {
        return format(date, YYYY_MM_DD);
    }


    /**
     * 返回當前日期 yyyyMMdd格式 字符串
     *
     * @return
     */
    public static String getNow8() {
        return format8(new Date());
    }

    /**
     * 獲取當天0點 date對象
     *
     * @return
     */
    public static Date getToday() {
        Date today = new Date();
        try {
            today = parse(getNow8(), DF_YYYYMMDD);
        } catch (Exception e) {
            log.error("Parsing / Getting today's date failed.", e);
        }
        return today;
    }




    /**
     * 獲取毫秒級時間戳
     *
     * @return
     */
    public static Long currentTimeMillis() {
        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return milliSecond;
    }

    /**
     * 是否當天
     *
     * @param time
     * @param pattern
     * @return
     */
    public static boolean isCurrentTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);
        String now = sdf.format(new Date());
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    public static Long getDay(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long days = null;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));
            Date pastTime = dateFormat.parse(date);
            long diff = currentTime.getTime() - pastTime.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static int getDaysBetween(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    public static List<String> getDays(Date startTime, Date endTime, DateFormat dateFormat) {
        // 返回的日期集合
        List<String> days = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startTime);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endTime);
        while (tempStart.before(tempEnd)) {
            days.add(dateFormat.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return days;
    }


    /**
     * 根據天數獲取時間
     *
     * @return
     */
    public static Date getPreviousDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 獲取當天開始時間
     *
     * @return
     */
    public static Date getTodayStartDate() {
        long current = System.currentTimeMillis();//當前時間毫秒數
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零點零分零秒的毫秒數
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;//今天23點59分59秒的毫秒數
        return new Timestamp(zero);
    }

    public static Date getTodayStartTimestamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    public static Date getTodayEndTimestamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }
    public static Date getYesterdayStartTimestamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        calendar.add(Calendar.DATE , -1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    public static Date getYesterdayEndTimestamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        calendar.add(Calendar.DATE , -1);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }
    /**
     * 獲取當天開始時間
     *
     * @return
     */
    public static Date getYesterdayStartDate() {
        long current = System.currentTimeMillis();//當前時間毫秒數
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零點零分零秒的毫秒數
        long twelve = zero - 24 * 60 * 60 * 1000;//今天23點59分59秒的毫秒數
        return new Timestamp(twelve);
    }

    /**
     * 獲取當天結束時間
     *
     * @return
     */
    public static Date getTodayEndDate() {
        long current = System.currentTimeMillis();//當前時間毫秒數
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零點零分零秒的毫秒數
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;//今天23點59分59秒的毫秒數
        return new Timestamp(twelve);
    }

    /**
     * 獲取目標時間那天的結束時間
     *
     * @param date
     * @return
     */
    public static Date getEndDate(Date date) {
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTime(date);
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
//        dateEnd.set(Calendar.MILLISECOND, 99);
        return dateEnd.getTime();
    }

    /**
     * 獲取目標時間那天的開始時間
     *
     * @param date
     * @return
     */
    public static Date getStartDate(Date date) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        dateStart.set(Calendar.MILLISECOND, 0);
        return dateStart.getTime();
    }

    /**
     * 設置時間 時 分 秒
     *
     * @param date
     * @param minute
     * @param second
     * @return
     */
    public static Date getDate(Date date, int hour, int minute, int second) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, hour);
        dateStart.set(Calendar.MINUTE, minute);
        dateStart.set(Calendar.SECOND, second);
        return dateStart.getTime();
    }

    /**
     * 獲取今年開始時間
     *
     * @return
     */
    public static Date getYearStartDate() {
        Calendar calendar = Calendar.getInstance();// 獲取當前日期
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 獲取當月開始時間
     *
     * @return
     */
    public static Date getMonthStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 獲取上月開始時間
     *
     * @return
     */
    public static Date getLastMonthStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 獲取上月開始時間
     *
     * @return
     */
    public static Date getLastMonthEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 獲取當前時間季度開始時間
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 3);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 4);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 9);
            }
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 獲取第幾周
     *
     * @param today
     * @return
     */
    public static int getWeekNum(String today) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(today);
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(date);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 獲取本週第一天的時間
     *
     * @return
     */
    public static Date getFirstWeekDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 獲取本週第一天的時間
     *
     * @return
     */
    public static Date getDailyStartDay() {
        LocalDateTime endDayTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return convertToDate(endDayTime);
    }

    /**
     * 獲取本週最後一天的時間
     *
     * @return
     */
    public static Date getDailyEndDay() {
        LocalDateTime endDayTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return convertToDate(endDayTime);
    }

    /**
     * 獲取本週第一天的時間
     *
     * @return
     */
    public static Date getWeeklyStartDay() {
        LocalDateTime today = LocalDateTime.now();
        int dayOfWeekNum = today.getDayOfWeek().getValue();

        LocalDate localDate = today.minusDays(dayOfWeekNum-1).toLocalDate();
        LocalTime localTime = LocalTime.MIN;
        LocalDateTime lastWeekDayTime = LocalDateTime.of(localDate, localTime);
        return convertToDate(lastWeekDayTime);
    }

    /**
     * 獲取本週最後一天的時間
     *
     * @return
     */
    public static Date getWeeklyEndDay() {
        LocalDateTime today = LocalDateTime.now();
        int dayOfWeekNum = today.getDayOfWeek().getValue();

        LocalDate localDate = today.plusDays(7 - dayOfWeekNum).toLocalDate();
        LocalTime localTime = LocalTime.MAX;
        LocalDateTime lastWeekDayTime = LocalDateTime.of(localDate, localTime);
        return convertToDate(lastWeekDayTime);
    }

    /**
     * 獲取本月第一天的時間
     *
     * @return
     */
    public static Date getMonthlyStartDay() {
        LocalDateTime today = LocalDateTime.now();
        int dayOfMonthNum = today.getDayOfMonth();

        LocalDate localDate = today.minusDays(dayOfMonthNum-1).toLocalDate();
        LocalTime localTime = LocalTime.MIN;
        LocalDateTime lastMonthDayTime = LocalDateTime.of(localDate, localTime);
        return convertToDate(lastMonthDayTime);
    }

    /**
     * 獲取本月最後一天的時間
     *
     * @return
     */
    public static Date getMonthlyEndDay() {
        LocalDateTime today = LocalDateTime.now();
        int dayOfMonthNum = today.getDayOfMonth();

        LocalDate localDate = today.minusDays(dayOfMonthNum-1).plusMonths(1).minusDays(1).toLocalDate();
        LocalTime localTime = LocalTime.MAX;
        LocalDateTime lastMonthDayTime = LocalDateTime.of(localDate, localTime);
        return convertToDate(lastMonthDayTime);
    }

    public static Long getMonthlyPartitionTime() {
        return getMonthlyPartitionTime(null);
    }

    public static Long getMonthlyPartitionTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        return date.getTime();
    }

    /**
     * 獲取目標時間周第一天的時間
     *
     * @return
     */
    public static Date getFirstWeekDay(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }


    /**
     * 設置時間 分和秒
     *
     * @param date
     * @param minute
     * @param second
     * @return
     */
    public static Date getDate(Date date, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 增加/減少 秒
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addSecond(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, amount);
        return calendar.getTime();
    }

    /**
     * 獲取過去第幾天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today);
    }

    /**
     * 根據日期獲取 星期 （2019-05-06 ——> 星期一）
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = f.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    public static String toWeekName(Integer dayNum) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weekDays[dayNum];
    }

    public static int dateToWeekInt(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Integer[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = f.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    /**
     * 時間轉換成時間戳
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static long dateToStamp(String strDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(strDate);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 功能描述：返回月
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回月
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回日期
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小時
     *
     * @param date 日期
     * @return 返回小時
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分鐘
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒鐘
     *
     * @param date Date 日期
     * @return 返回秒鐘
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫
     *
     * @param date 日期
     * @return 返回毫
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();

    }

    /**
     * 計算兩個時間相差多少個年
     *
     * @param start
     * @param end
     * @return 年
     */
    public static int getYearsBetween(String start, String end) {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.setTime(toDate(start, YYYY_MM));
        endDate.setTime(toDate(end, YYYY_MM));
        return (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR));
    }

    /**
     * 計算兩個時間相差多少個月
     *
     * @param start
     * @param end
     * @return int
     */
    public static int getMonthsBetween(String start, String end) {
        startDate.setTime(toDate(start, YYYY_MM));
        endDate.setTime(toDate(end, YYYY_MM));
        int result = getYearsBetween(start, end) * 12 + endDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH);
        return result == 0 ? 0 : Math.abs(result);
    }


    /**
     * 獲取指定月份第一天
     *
     * @param date
     * @return Date
     */
    public static Date getMonthStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 獲取指定月份最後一天
     *
     * @param date
     * @return Date
     */
    public static Date getMonthLastDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 獲取昨天時間
     *
     * @return
     */
    public static Date getYesterday() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1);
        return calendar.getTime();
    }

    public static Date getEndOfYesterday(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static Date getSameTimeYesterday(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    /**
     * 獲取上個小時時間
     *
     * @return
     */
    public static Date getLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        return calendar.getTime();
    }

    public static Date parse(String dateString) {
        ZonedDateTime time = ZonedDateTime.parse(dateString).withZoneSameInstant(ZoneId.of("+8"));
        Instant instant = time.toInstant();
        return Date.from(instant);
    }

    public static DatePair getNatureWeekStartAndEnd(@NonNull final Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date start = calendar.getTime();

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        Date end = calendar.getTime();

        return new DatePair(start, end);
    }

    public static DatePair getNextTimesNatureWeekStartAndEnd(@NonNull final Date date, Integer nextTimes){

        if(Objects.isNull(nextTimes)){
            nextTimes = 1;
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getNatureWeekStartAndEnd(date).getStartDate());

        calendar.add(Calendar.DAY_OF_YEAR, 7 * nextTimes);
        return getNatureWeekStartAndEnd(calendar.getTime());
    }


    public static List<String> getNatureWeekDays(@NonNull final Date date, DateFormat dateFormat){

        if(Objects.isNull(dateFormat)){
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }

        DatePair datePair = getNatureWeekStartAndEnd(date);
        return getDays(datePair.getStartDate(), datePair.getEndDate(), dateFormat);
    }

    public static Optional<Integer> getDayOfTheWeek(@NonNull final List<String> weekDays, @NonNull final Date today) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(today);
        for (int i = 0; i < weekDays.size(); i++) {
            if (weekDays.get(i).equals(date)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }


    public static class DatePair{

        private Date startDate;
        private Date endDate;

        public DatePair(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }

    public static String convertDateToString(Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static Date getSomeTimeOfSomeDay(Date date, Integer numOfDays, Integer hour, Integer minute, Integer second, Integer milliSecond){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, numOfDays == null ? 0 : numOfDays);
        c.set(Calendar.HOUR_OF_DAY, hour == null ? 0 : hour);
        c.set(Calendar.MINUTE, minute == null ? 0 : minute);
        c.set(Calendar.SECOND, second == null ? 0 : second);
        c.set(Calendar.MILLISECOND, milliSecond == null ? 0 : milliSecond);
        return c.getTime();
    }

    public static List<Date> getDatesInBetween(Date startDate, Date endDate){
        List<Date> datesInBetween = new ArrayList<>();

        Calendar start = new GregorianCalendar();
        start.setTime(startDate);

        Calendar end = new GregorianCalendar();
        end.setTime(endDate);

        while(start.before(end)){
            datesInBetween.add(start.getTime());
            start.add(Calendar.DATE, 1);
        }

        return datesInBetween;
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        if (date != null) {
            return date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        }
        return null;
    }

    public static Date convertToDate(LocalDateTime date) {
        if (date != null) {
            return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    public static LocalDateTime parseStrDate(String date){
        return parseStrDate(date, null);
    }

    public static LocalDateTime parseStrDate(String date, String format){
        if(Objects.isNull(date)){
            return null;
        }
        if(Objects.isNull(format)){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
    }

    /**
     * timestamp to dateString
     * @param timestamp
     * @param datePattern
     * @return
     */
    public static String stampToDate(Long timestamp, String datePattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
            Date date = new Date(timestamp);
            return formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * timestamp to date
     *
     * @param timestamp
     * @param datePattern
     * @return
     */
    public static Date stampToDateDate(Long timestamp, String datePattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            String stringDate = sdf.format(new Date(timestamp));
            return sdf.parse(stringDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 確認日期是否超過 100 天
     * @param from
     * @return
     */
    public static boolean isOneHundredBefore(Date from){
        try {
            /**  若為null則判斷為全表搜尋 **/
            if( from == null ) {
                return true;
            }
            Date comparedDate = DateUtils.addDay(-100, DateUtils.getTodayStartDate());
            return comparedDate.after(from);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 確認日期是否超過 100 天(LocalTime)
     * @param from
     * @return
     */
    public static boolean isOneHundredBeforeLocalDateTime(LocalDateTime from){
        try {
            /**  若為null則判斷為全表搜尋 **/
            if( from == null ) {
                return true;
            }
            LocalDateTime comparedTime = LocalDateTime.now().minusDays(100);
            return comparedTime.isAfter(from);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     *
     * @param start 開始時間
     * @param end 結束時間
     * @param day 相差天數
     * @return
     * 結束時間是否和開始時間相差天數
     */
    public static boolean isDifferenceDay(LocalDateTime start , LocalDateTime end ,Integer day){
        if(end.minusDays(day).isAfter(start)){
            return true;
        }else {
            return false;
        }
    }

    public static boolean between(Date from, Date time, Date to) {
        return (from.before(time) || from.equals(time)) && (time.before(to) || time.equals(to));
    }

    /**
     *
     * @return
     * 取得當下時間
     */
    public static String getCurrentDateTimeFormatted() {
        // 获取当前日期和时间
        Date currentDate = new Date();
        // 格式化日期和时间为字符串
        return format(currentDate);
    }
}

