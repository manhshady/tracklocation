package vn.softlink.core.util.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import vn.softlink.core.Config;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public class TimeHelper {

    public static long SECOND = 1000;
    public static long MINUTE = 60 * SECOND;
    public static long HOUR = 60 * MINUTE;
    public static long DAY = 24 * HOUR;

    private static SimpleDateFormat timeFormat;
    private static TimeZone timeZone;

    private TimeHelper() {
    }

    static {
        timeFormat = new SimpleDateFormat(Config.DATE_TIME_FMT);
        timeZone = TimeZone.getTimeZone(Config.TIME_ZONE);
    }

    public static long format(String timeString) {
        return format(timeString, timeFormat);
    }

    public static String format(long longTimeMillis) {
        return format(longTimeMillis, timeFormat);
    }

    public static long format(String string, SimpleDateFormat formatter) {
        try {
            return formatter.parse(string).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String format(long timestamp, SimpleDateFormat formatter) {
        return formatter.format(new Date(timestamp));
    }

}
