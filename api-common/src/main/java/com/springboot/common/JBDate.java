package com.springboot.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class JBDate {
    public JBDate() {
    }

    //获得当前月
    public static String getCurrentMonth() {
        Date nowDate = new Date();
        SimpleDateFormat DateFormat = new SimpleDateFormat("MM");
        return DateFormat.format(nowDate);
    }

    //获得当前日期
    public static String getNowDate() {
        Date nowDate = new Date();
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return DateFormat.format(nowDate);
    }


    //获得当前日期
    public static String getNowDate(long time) {
        Date nowDate = new Date(time);
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return DateFormat.format(nowDate);
    }


    public static String formDate(String value) {
        String newValue = "null";
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (value == null) {
                return "null";
            }

            Date da = f.parse(value);
            Calendar ca = Calendar.getInstance();
            ca.setTime(da);
            ca.add(Calendar.HOUR_OF_DAY, 8);
            newValue = "" + f.format(ca.getTime()) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newValue;
    }


    /**
     * 得到当前年月 yuansh 2010-10-29
     *
     * @return
     * @throws ParseException
     */
    public static String afterNDay(int n, String xx) {
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        // date = df.parse(xx);
        try {
            c.setTime(df.parse(xx));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        c.add(Calendar.DATE, n);
        Date d2 = c.getTime();
        String s = df.format(d2);
        return s;
    }


    public static Date afterNDay_String(int n, Date xx) {
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        // date = df.parse(xx);
        c.setTime(xx);
        c.add(Calendar.DATE, n);
        Date d2 = c.getTime();
        return d2;
    }


    /**
     * 得到当前年月 yuansh 2010-10-29
     *
     * @return
     * @throws ParseException
     */
    public static String afterNDay(int n) {
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(new Date());
        c.add(Calendar.DATE, n);
        Date d2 = c.getTime();
        String s = df.format(d2);
        return s;
    }

    public static String getCurYearmonth() {
        Date nowDate = new Date();
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMM");
        return DateFormat.format(nowDate);
    }


    public static String getWeek() {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//	 if(dayOfWeek <0)dayOfWeek=0;
//	 System.out.println(dayNames[dayOfWeek]);
        return String.valueOf(dayOfWeek);
    }


    public static String getNowDate1() {
        Date nowDate = new Date();
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMdd");
        return DateFormat.format(nowDate);
    }

    public static String getNowDateTime() {
        Date nowDate = new Date();
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return DateFormat.format(nowDate);
    }

    public static String getNowDateTimeMS() {
        Date nowDate = new Date();
        SimpleDateFormat DateFormat = new SimpleDateFormat("HH:mm:ss");
        return DateFormat.format(nowDate);
    }

    public static String getNowDateTime1() {
        Date nowDate = new Date();
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return DateFormat.format(nowDate);
    }

    public static String getToDayNextOffsetDate(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(6, calendar.get(6) + offset);
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMdd");
        return DateFormat.format(calendar.getTime());
    }

    public static String getToDayNextOffsetDate1(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(6, calendar.get(6) + offset);
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return DateFormat.format(calendar.getTime());
    }

    public static void main(String[] args) throws ParseException {

        System.out.println(JBDate.getdef("2019-07-27 10:01:05", 36000) + "");
//        System.out.println(JBDate.getNowDate() + " " + JBDate.getNowDateTimeMS());

    }


    public static boolean getdef(String datetime, int ttime) {


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long hour = 0l;
        try {
            Date date = df.parse(datetime);
            Date now = new Date();
            long l = now.getTime() - date.getTime();
//            System.out.println(l);
//            System.out.println(ttime*60*1000);
//            long day = l / (24 * 60 * 60 * 1000);
//            hour = (l / (60 * 60 * 1000) - day * 24);
//            System.out.println("" + day + "天" + hour + "小时");
//            long min=((l/(60*1000))-day*24*60-hour*60);
            //long s=(l/1000-day*24*60*60-hour*60*60-min*60);
//            System.out.println(min);

//            System.out.println("l:" + l);
//            System.out.println(" ttime * 60 * 1000 :" +  ttime * 60 * 1000 );
            if (l - ttime * 60 * 1000 > 0) {
//                System.out.println("超过" + ttime + "分钟");
                return true;
            } else {
//                System.out.println("没有超过" + ttime + "分钟");
                return false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    public static long tt(String date) {
        long xx = 0l;
        String t1 = JBDate.getNowDate().replace('-', '/');
        String t2 = date.replace('-', '/');
        Date dt1 = new Date(t1);
        Date dt2 = new Date(t2);
        long i = (dt1.getTime() - dt2.getTime()) / (1000 * 60 * 60 * 24);


        return i / 365 + 1;
    }



    //获取当月第一天
    public static String getfirstDay() {
        SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-01");
        Date firstDay = new Date();
        return formats.format(firstDay);


    }

    public static String getendDay() {
        Calendar cal = Calendar.getInstance();
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        java.text.Format formatter3 = new SimpleDateFormat("yyyy-MM-" + maxDay);
        return formatter3.format(cal.getTime());
    }

    /**
     * 获取前一月
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static String getQmonth(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(simpleDateFormat.parse(time));

        // 前一个月的日期：2010-02-28
        c.add(Calendar.MONTH, -1);
        return simpleDateFormat.format(c.getTime());
    }


    /**
     * 获取后一月
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static String getmonth(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(simpleDateFormat.parse(time));

        // 前一个月的日期：2010-02-28
        c.add(Calendar.MONTH, +1);
        return simpleDateFormat.format(c.getTime());
    }


    /**
     * 获取指定月份到金月份的所有月份
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static List<String> getq_nowList(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(simpleDateFormat.parse(time));

        // 前一个月的日期：2010-02-28

        String nowTime = new SimpleDateFormat("yyyy-MM").format(new Date());
        String lastTime = simpleDateFormat.format(c.getTime());
        List<String> month_list = new ArrayList<String>();
        int i = 1;
        while (!(nowTime.equals(lastTime))) {
            c.add(Calendar.MONTH, +1);
            lastTime = simpleDateFormat.format(c.getTime());
            month_list.add(lastTime);
            i++;
        }

        return month_list;
    }

//	JAVA处理日期时间常用方法：
//
//	1.java.util.Calendar
//	Calendar 类是一个抽象类，它为特定瞬间与一组诸如 YEAR、MONTH、DAY_OF_MONTH、HOUR 等 日历字段之间的转换提供了一些方法，并为操作日历字段（例如获得下星期的日期）提供了一些方法。瞬间可用毫秒值来表示，它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00.000，格里高利历）的偏移量。
//
//	例:
//
//	Java代码
//	1.Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
//	2.cal.add(Calendar.DAY_OF_MONTH, -1);//取当前日期的前一天.
//	3.
//	4.cal.add(Calendar.DAY_OF_MONTH, +1);//取当前日期的后一天.
//	5.
//	6.//通过格式化输出日期
//	7.java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
//	8.
//	9.System.out.println("Today is:"+format.format(Calendar.getInstance().getTime()));
//	10.
//	11.System.out.println("yesterday is:"+format.format(cal.getTime()));
//	Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
//	cal.add(Calendar.DAY_OF_MONTH, -1);//取当前日期的前一天.
//
//	cal.add(Calendar.DAY_OF_MONTH, +1);//取当前日期的后一天.
//
//	//通过格式化输出日期
//	java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
//
//	System.out.println("Today is:"+format.format(Calendar.getInstance().getTime()));
//
//	System.out.println("yesterday is:"+format.format(cal.getTime()));
//
//
//	得到2007-12-25日期:
//
//	Java代码
//	1.Calendar calendar = new GregorianCalendar(2007, 11, 25,0,0,0);
//	2.Date date = calendar.getTime();
//	3.System.out.println("2007 Christmas is:"+format.format(date));
//	Calendar calendar = new GregorianCalendar(2007, 11, 25,0,0,0);
//	Date date = calendar.getTime();
//	System.out.println("2007 Christmas is:"+format.format(date));
//
//	java月份是从0-11,月份设置时要减1.
//
//	GregorianCalendar构造方法参数依次为：年，月-1，日，时，分，秒.
//
//	取日期的部分:
//
//	Java代码
//	1.int year =calendar.get(Calendar.YEAR);
//	2.
//	3.int month=calendar.get(Calendar.MONTH)+1;
//	4.
//	5.int day =calendar.get(Calendar.DAY_OF_MONTH);
//	6.
//	7.int hour =calendar.get(Calendar.HOUR_OF_DAY);
//	8.
//	9.int minute =calendar.get(Calendar.MINUTE);
//	10.
//	11.int seconds =calendar.get(Calendar.SECOND);
//	int year =calendar.get(Calendar.YEAR);
//
//	int month=calendar.get(Calendar.MONTH)+1;
//
//	int day =calendar.get(Calendar.DAY_OF_MONTH);
//
//	int hour =calendar.get(Calendar.HOUR_OF_DAY);
//
//	int minute =calendar.get(Calendar.MINUTE);
//
//	int seconds =calendar.get(Calendar.SECOND);
//
//
//	取月份要加1.
//
//	判断当前月份的最大天数:
//
//	Java代码
//	1.Calendar cal = Calendar.getInstance();
//	2.int day=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//	3.System.out.println(day);
//	Calendar cal = Calendar.getInstance();
//	int day=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//	System.out.println(day);
//
//
//	2.java.util.Date
//
//	Java代码
//	1.java.util.Date today=new java.util.Date();
//	2.System.out.println("Today is "+formats.format(today));
//	java.util.Date today=new java.util.Date();
//	System.out.println("Today is "+formats.format(today));
//
//
//	取当月的第一天:
//
//	Java代码
//	1.java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-01");
//	2.java.util.Date firstDay=new java.util.Date();
//	3.System.out.println("the month first day is "+formats.format(firstDay));
//	java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-01");
//	java.util.Date firstDay=new java.util.Date();
//	System.out.println("the month first day is "+formats.format(firstDay));
//
//	取当月的最后一天:
//
//	Java代码
//	1.
//	2.Calendar cal = Calendar.getInstance();
//	3.int maxDay=cals.getActualMaximum(Calendar.DAY_OF_MONTH);
//	4.java.text.Format formatter3=new java.text.SimpleDateFormat("yyyy-MM-"+maxDay);
//	5.System.out.println(formatter3.format(cal.getTime()));
//
//	Calendar cal = Calendar.getInstance();
//	int maxDay=cals.getActualMaximum(Calendar.DAY_OF_MONTH);
//	java.text.Format formatter3=new java.text.SimpleDateFormat("yyyy-MM-"+maxDay);
//	System.out.println(formatter3.format(cal.getTime()));
//
//
//	求两个日期之间相隔的天数:
//
//	Java代码
//	1.java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
//	2.java.util.Date beginDate= format.parse("2007-12-24");
//	3.java.util.Date endDate= format.parse("2007-12-25");
//	4.long day=(date.getTime()-mydate.getTime())/(24*60*60*1000);
//	5.System.out.println("相隔的天数="+day);
//	java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
//	java.util.Date beginDate= format.parse("2007-12-24");
//	java.util.Date endDate= format.parse("2007-12-25");
//	long day=(date.getTime()-mydate.getTime())/(24*60*60*1000);
//	System.out.println("相隔的天数="+day);
//
//	一年前的日期:
//
//	Java代码
//	1.java.text.Format formatter=new java.text.SimpleDateFormat("yyyy-MM-dd");
//	2.java.util.Date todayDate=new java.util.Date();
//	3.long beforeTime=(todayDate.getTime()/1000)-60*60*24*365;
//	4.todayDate.setTime(beforeTime*1000);
//	5.String beforeDate=formatter.format(todayDate);
//	6.System.out.println(beforeDate);
//	java.text.Format formatter=new java.text.SimpleDateFormat("yyyy-MM-dd");
//	java.util.Date todayDate=new java.util.Date();
//	long beforeTime=(todayDate.getTime()/1000)-60*60*24*365;
//	todayDate.setTime(beforeTime*1000);
//	String beforeDate=formatter.format(todayDate);
//	System.out.println(beforeDate);
//
//	一年后的日期:
//
//	Java代码
//	1.java.text.Format formatter=new java.text.SimpleDateFormat("yyyy-MM-dd");
//	2.java.util.Date todayDate=new java.util.Date();
//	3.long afterTime=(todayDate.getTime()/1000)+60*60*24*365;
//	4.todayDate.setTime(afterTime*1000);
//	5.String afterDate=formatter.format(todayDate);
//	6.System.out.println(afterDate);
//	java.text.Format formatter=new java.text.SimpleDateFormat("yyyy-MM-dd");
//	java.util.Date todayDate=new java.util.Date();
//	long afterTime=(todayDate.getTime()/1000)+60*60*24*365;
//	todayDate.setTime(afterTime*1000);
//	String afterDate=formatter.format(todayDate);
//	System.out.println(afterDate);
//
//	求10小时后的时间
//
//	Java代码
//	1.java.util.Calendar Cal=java.util.Calendar.getInstance();
//	2.Cal.setTime(dateOper);
//	3.Cal.add(java.util.Calendar.HOUR_OF_DAY,10);
//	4.System.out.println("date:"+forma.format(Cal.getTime()));
//	java.util.Calendar Cal=java.util.Calendar.getInstance();
//	Cal.setTime(dateOper);
//	Cal.add(java.util.Calendar.HOUR_OF_DAY,10);
//	System.out.println("date:"+forma.format(Cal.getTime()));
//
//	求10小时前的时间
//
//	Java代码
//	1.java.util.Calendar Cal=java.util.Calendar.getInstance();
//	2.Cal.setTime(dateOper);
//	3.Cal.add(java.util.Calendar.HOUR_OF_DAY,-10);
//	4.System.out.println("date:"+forma.format(Cal.getTime()));
//	java.util.Calendar Cal=java.util.Calendar.getInstance();
//	Cal.setTime(dateOper);
//	Cal.add(java.util.Calendar.HOUR_OF_DAY,-10);
//	System.out.println("date:"+forma.format(Cal.getTime()));
//
//	3.java.sql.Date
//	继承自java.util.Date,是操作数据库用的日期类型
//
//	Java代码
//	1.java.sql.Date sqlDate = new java.sql.Date(java.sql.Date.valueOf("2007-12-25").getTime());
//	java.sql.Date sqlDate = new java.sql.Date(java.sql.Date.valueOf("2007-12-25").getTime());
//
//	日期比较:简单的比较可以以字符串的形式直接比较,也可使用
//	java.sql.Date.valueOf("2007-03-08").compareTo(java.sql.Date.valueOf("2007-03-18"))方式来比较日期的大小.也可使用java.util.Date.after(java.util.Date)来比较.
//
//	相差时间：
//	long difference=c2.getTimeInMillis()-c1.getTimeInMillis();
//	相差天数：long day=difference/(3600*24*1000)
//	相差小时：long hour=difference/(3600*1000)
//	相差分钟：long minute=difference/(60*1000)
//	相差秒： long second=difference/1000


}
