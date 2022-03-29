package com.jyw.learn.util;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Java日期转换成Cron日期表达式工具类
 * @author jyw
 *
 */
public class CronUtil {


    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null ) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(java.util.Date  date){
        String dateFormat="ss mm HH dd MM ?";
        return formatDateByPattern(date, dateFormat);
    }

    public static void main(String[] args) {
        String cron=CronUtil.getCron(new Date());
        System.out.println(cron);
    }


}