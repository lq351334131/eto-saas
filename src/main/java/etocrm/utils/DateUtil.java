package etocrm.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Provide Date Utilities
 */
@Slf4j
public class DateUtil {

    public final static String default_date_joint = "-";
    public final static String default_datemillisformat = "yyyy-MM-dd HH:mm:ss.SSS";
    public final static String default_datetimeformat = "yyyy-MM-dd HH:mm:ss";
    public final static String default_datetimeformat2 = "yyyyMMdd HH:mm:ss";
    public final static String default_datetimeformat3 = "yyyyMMddHHmmss";
    public final static String default_icddatetimeformat = "yyyy-MM-dd'T'HH:mm:ss";
    public final static String default_dateformat = "yyyy-MM-dd";
    public final static String default_file_datetimeformat = "yyyyMMddHHmmssSSS";

    private static String default_timezone = "Asia/Shanghai";

    public static final String DATE_FORMAT_NUMBER8 = "yyyyMMdd";

    private static Integer offset = 0; // number of days to offset

    /**
     * Returns the timestamp instance based on the current system date and
     * default timezone.
     *
     * @return the current timestamp
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(getDate().getTime());
    }

    /**
     * Returns the date instance based on the current system date and default
     * timezone.
     * <p/>
     *
     * @return Current System date.
     */
    public static Date getDate() {
        TimeZone tz = TimeZone.getTimeZone(default_timezone);
        Calendar calendar = Calendar.getInstance(tz);
        calendar.add(Calendar.DATE, offset.intValue());
        return calendar.getTime();
    }



    public static String formatDateTimeByFormat(Date date, String format) {
        String dateTimeStr = "";
        try {
            DateFormat defaultDTF = new SimpleDateFormat(format);
            dateTimeStr = defaultDTF.format(date);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return dateTimeStr;
    }


    /**
     * Get the formatted string of the given date instance based on the date
     * format provided.
     * <p/>
     * See {@link SimpleDateFormat SimpleDateFormat} for examples of
     * the format string.
     * <p/>
     *
     * @param date
     *            The date that needs to be formatted
     * @param format
     *            The format to be applied to the date
     * @return The formatted Date String; an empty String if the given date or
     *         format is <code>null</code>, or if there is error in formatting
     */
    public static String format(Date date, String format) {
        if (date == null || format == null)
            return "";

        String dateStr = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateStr = sdf.format(date);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        return dateStr;
    }

    /**
     * Returns a Date object instance from the input string. The input date
     * string is parsed to return a date object based on the format provided.
     * <p/>
     * Eg., to parse the date string "01-01-2003 9:2 PM", use the format
     * "yyyy-MM-dd h:m a". The resultant data object will have the value
     * "Mar 11 2003 09:02", as displayed in 24 hr format.
     * <p/>
     *
     * @param dateStr
     *            the date string
     * @param format
     *            the date format that the date string conforms to
     * @return the date instance parsed from the date string; <code>null</code>
     *         if the date string is <code>null</code> of if the date string is
     *         invalid
     */
    public static Date parse(String dateStr, String format) {
        Date date = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        return date;
    }

    /**
     * Date Arithmetic function. Adds the specified (signed) amount of time to
     * the given time field, based on the calendar's rules.
     * <p>
     * For example, to subtract 5 days from a specific date, it can be achieved
     * by calling:
     * <p>
     * DateUtil.add(date, Calendar.DATE, -5).
     * <p>
     *
     * @param date
     *            The date to perform the arithmetic function on
     * @param field
     *            A Calendar constant to retrieve the field value from the Date
     *            object. Same as for {@link #get get()}.
     * @param amount
     *            the amount of date or time to be added to the field
     * @return The date as a result of the execution of the arithmetic function.
     */
    public static Date add(Date date, int field, int amount) {
        TimeZone tz = TimeZone.getTimeZone(default_timezone);
        Calendar cal = Calendar.getInstance(tz);
        cal.setTime(date);
        cal.add(field, amount);

        return cal.getTime();
    }

    /**
     * Format the input date to a date time string in the following format: <br>
     * <code>yyyy-MM-dd HH:mm:ss</code>
     *
     * @param date
     *            the date time value to be formatted into a date time string.
     * @return the formatted date time string; an empty String if the input date
     *         is <code>null</code> or if there is error during formatting.
     */
    public static String formatDateTimeForFile(Date date) {
        String dateTimeStr = "";

        try {
            DateFormat defaultDTF = new SimpleDateFormat(default_file_datetimeformat);
            dateTimeStr = defaultDTF.format(date);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        return dateTimeStr;
    }

}
