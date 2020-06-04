package com.app.core.util;

import android.support.v4.util.Pair;

import com.app.core.log.Logger;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public final class DateUtil {
    public enum DateType {
        UNSPECIFIED,
        MONTH,
        YEAR
    }

    /**
     * @return current millis in local time
     */
    public static long currentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String countTimerTask(long startMillis) {
        double secs = countTime(startMillis);
        return "" + secs + " secs";
    }

    /**
     * @param startMillis start millis time
     * @return seconds space between current and start time
     */
    public static double countTime(long startMillis) {
        return (currentTime() - startMillis) / 1000.000;
    }

    public static boolean countTimeOut(long startMillis, long timeOut) {
        long time = currentTime() - startMillis;
        return time <= timeOut;
    }

    public static String getCurrentTime() {
        DateTime dateTime = DateTime.now(DateTimeZone.forTimeZone(TimeZone.getDefault()));
        return dateTime.toString();
    }

    public static String getTimeBefore(@NonNull DateType type, int range, @NonNull String timeFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat, Locale.UK);
        Date dateTime;
        switch (type) {
            case MONTH:
                dateTime = new DateTime().minusMonths(range).toDate();
                break;
            case YEAR:
                dateTime = new DateTime().minusYears(range).toDate();
                break;
            default:
                dateTime = new DateTime().minusYears(range).toDate();
                break;
        }
        return formatter.format(dateTime);
    }

    public static String getTimeAfter(@NonNull DateType type, int range, @NonNull String timeFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat, Locale.UK);
        Date dateTime;
        Calendar calendar = Calendar.getInstance(Locale.UK);
        if (range != 0) {
            switch (type) {
                case MONTH:
                    calendar.add(Calendar.MONTH, range);
                    dateTime = calendar.getTime();
                    break;
                case YEAR:
                    calendar.add(Calendar.YEAR, range);
                    dateTime = calendar.getTime();
                    break;
                default:
                    calendar.add(Calendar.YEAR, range);
                    dateTime = calendar.getTime();
                    break;
            }
        } else {
            dateTime = calendar.getTime();
        }
        return formatter.format(dateTime);
    }

    public static DateTime getCurrentDateTime() {
        return DateTime.now(DateTimeZone.forTimeZone(TimeZone.getDefault()));
    }

    public static String getCurrentDateTimeByFormat(@NonNull String format) {
        // Get Time
        Locale locale = Locale.US;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
        return dateFormat.format(now);
    }

    public static String getCurrentTimeUTCZone(@NonNull String formatString) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(formatString).withLocale(Locale.US);
        DateTime dateTime = DateTime.now(DateTimeZone.forTimeZone(TimeZone.getTimeZone("UTC")));
        return dateTime.toString(formatter);
    }

    public static String getCurrentTimeWithZone(@NonNull String formatString) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(formatString).withLocale(Locale.getDefault());
        DateTime dateTime = DateTime.now(DateTimeZone.forTimeZone(TimeZone.getTimeZone("PST")));
        return dateTime.toString(formatter);
    }

    public static String getTimeDefaultZone(@NonNull Date dateUTC, @NonNull String formatString) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(formatString).withLocale(Locale.US);
        DateTime dateTime = new DateTime(dateUTC, DateTimeZone.forTimeZone(TimeZone.getDefault()));
        return dateTime.toString(formatter);
    }

    public static DateTime getDateTimeDefaultZone(@NonNull Date dateUTC) {
        return new DateTime(dateUTC, DateTimeZone.forTimeZone(TimeZone.getDefault()));
    }

    @Nullable
    public static DateTime getDateTimeDefaultZone(@NonNull String dateString, @NonNull String timeFormat) {
        Date dateUTC = getDateFromString(dateString, timeFormat);
        if (dateUTC != null) {
            return new DateTime(dateUTC, DateTimeZone.forTimeZone(TimeZone.getDefault()));
        }
        return null;
    }

    public static boolean isDateAfter(@NonNull String input, @NonNull String dateCompare) {
        return new DateTime(input).compareTo(new DateTime(dateCompare)) < 0;
    }

    public static boolean isDateBefore(@NonNull String input, @NonNull String dateCompare) {
        return new DateTime(input).compareTo(new DateTime(dateCompare)) > 0;
    }

    /**
     * without care timezone.
     *
     * @param dateString String date to parse
     */
    public static Date getDateFromString(@NonNull String dateString, @NonNull String timeFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat, Locale.UK);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (Exception e) {
            Logger.w(e.getMessage());
        }
        return date;
    }

    public static String getDate(double milliSeconds, @NonNull String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.UK);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long) milliSeconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * if contains Z is UTC, no is no Timezone.
     *
     * @param dateString String date to parse
     */
    public static Date getLocalDateFromUTC(@NonNull String dateString, @NonNull String timeFormat) {
        if (!StringUtil.emptyTrim(dateString)) {
            if (dateString.contains("Z")) {
                DateTime dateTime = new DateTime(dateString, DateTimeZone.UTC);
                return dateTime.toDate();
            } else {
                return getDateFromString(dateString, timeFormat);// no timezone
            }
        }
        return null;
    }

    public static String convertUtcDate(@NonNull String time, @NonNull String inFormat, @NonNull String outFormat) {
        return getTimeDefaultZone(getLocalDateFromUTC(time, inFormat), outFormat);
    }

    public static String convertDate(@NonNull String dateString,
                                     @NonNull String inputFormat,
                                     @NonNull String outputFormat) {

        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat, Locale.UK);
        try {
            Date date = sdf.parse(dateString);
            sdf = new SimpleDateFormat(outputFormat, Locale.UK);
            return sdf.format(date);
        } catch (Exception e) {
            Logger.w(e.getMessage());
            return "";
        }
    }


    public static Pair<Date, Date> getMonthRange(int month, int year) {
        Date begin, end;
        {
            Calendar calendar = getCalendarForMonth(month, year);
            begin = calendar.getTime();
        }

        {
            if (month >= 12) {
                month = 1;
                year++;
            } else {
                month++;
            }
            Calendar calendar = getCalendarForMonth(month, year);
            end = calendar.getTime();
        }

        return Pair.create(begin, end);
    }

    public static Calendar getDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal;
    }

    public static Pair<Date, Date> getDateRange(int day, int month, int year) {
        Date begin, end;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, day);
        setTimeToBeginningOfDay(cal);
        begin = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        end = cal.getTime();
        return Pair.create(begin, end);
    }

    private static Calendar getCalendarForMonth(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        setTimeToBeginningOfDay(cal);
        return cal;
    }

    private static void setTimeToBeginningOfDay(@NonNull Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndOfDay(@NonNull Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}
