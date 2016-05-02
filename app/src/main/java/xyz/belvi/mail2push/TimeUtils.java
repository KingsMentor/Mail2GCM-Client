package xyz.belvi.mail2push;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by BELVI-PC on 10/28/2015.
 */
public class TimeUtils {

    public static String toReadableTime(String time, boolean chat) {
        try {
            Date date = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a").parse(time);
            Calendar c = Calendar.getInstance();
            Date now = c.getTime();
            long interval = (now.getTime() - date.getTime()) / ((1000 * 60 * 60 * 24));
            return getOutputTime(interval, time, chat);
        } catch (ParseException ex) {
            Logger.getLogger(TimeUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return time;
    }


    public static String getTimeOfDay() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour < 12) {
            return "Good Morning";
        } else if (hour < 16) {
            return "Good Afternoon";
        } else {
            return "Good evening";
        }
    }

    public static int DaysBetween(Calendar day1, Calendar day2) {
        int cDay = day1.get(Calendar.DAY_OF_MONTH);
        int nowDay = day2.get(Calendar.DAY_OF_MONTH);
        return Math.abs(cDay - nowDay);
    }

    public static String toReadableTime(long time, boolean chat) {
        Date date = new Date(time);
        try {
            Calendar c = Calendar.getInstance();
            Calendar cdate = Calendar.getInstance();
            cdate.setTimeInMillis(time);
            return getOutputTime(DaysBetween(cdate, c), time, chat);
        } catch (ParseException ex) {
            Logger.getLogger(TimeUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SimpleDateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
    }

    private static String getOutputTime(long days, String time, boolean chat) throws ParseException {
        Date date = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a").parse(time);
        if (days == 0) {
            if (chat)
                return "Today";
            return new SimpleDateFormat("hh:mm a").format(date);
        } else if (days == 1) {
            return "YESTERDAY ";
        } else if (days < 7) {
            return new SimpleDateFormat("EEE dd").format(date);
        } else if (days <= 365) {
            return new SimpleDateFormat("MMM dd").format(date);
        } else if (days > 365) {
            return new SimpleDateFormat("MMM dd,yyyy").format(date);
        }
        return time;
    }

    private static String getOutputTime(long days, long time, boolean chat) throws ParseException {
        Date date = new Date(time);
        if (days == 0) {
            if (chat)
                return "Today";
            return new SimpleDateFormat("hh:mm a").format(date);
        } else if (days == 1) {
            return "yesterday ";
        } else if (days < 7) {
            return new SimpleDateFormat("EEE dd").format(date);
        } else if (days <= 365) {
            return new SimpleDateFormat("MMM dd").format(date);
        } else if (days > 365) {
            return new SimpleDateFormat("MMM dd,yyyy").format(date);
        }
        return SimpleDateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
    }

}
