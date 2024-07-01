package com.mimsoft.informesblackboard.application.utils.others;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    /**
     * Convert String to Date
     * @param format dd/mm/yyyy or dd-mm-yyyy
     * @return Date
     */
    public static Date DateFromString(String format) { // dd/mm/yyyy || dd-mm-yyyy
        if (format == null || format.length() < 6) return null;
        try {
            String[] data = format.contains("/") ?format.split("/") :format.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(data[2]));
            calendar.set(Calendar.MONTH, Integer.parseInt(data[1]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data[0]));
            return calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Diff Seconds
     * @param dateStart Date
     * @param dateEnd Date
     * @return dateEnd - dateStart
     */
    public static long DiffSeconds(Date dateStart, Date dateEnd) {
        long d = dateEnd.getTime() - dateStart.getTime();
        return (int) (d / 1000);
    }

    public static long DiffHrs(Date dateStart, Date dateEnd) {
        long d = dateEnd.getTime() - dateStart.getTime();
        return (int) (d / 3600 / 1000);
    }

    public static long DiffMinutes(Date dateStart, Date dateEnd) {
        long d = dateEnd.getTime() - dateStart.getTime();
        return (int) (d / 60 / 1000);
    }

    public static long DiffDays(Date dateStart, Date dateEnd) {
        long hrs = DiffHrs(dateStart, dateEnd);
        return hrs / 24;
    }

    public static long DiffYears(Date dateStart, Date dateEnd) {
        long days = DiffDays(dateStart, dateEnd);
        return days / 365;
    }

    public static Date DateToStartYear(Date date) {
        Date _date = DateToStartMonth(date);
        _date.setMonth(0);
        return _date;
    }

    public static Date DateToStartMonth(Date date) {
        Date _date = new Date(date.getTime());
        _date.setDate(1);
        return _date;
    }

    public static Date DateToEndMonth(Date date) {
        Date _date = new Date(date.getTime());
        _date.setDate(1);
        _date.setMonth(_date.getMonth() + 1);
        _date.setDate(_date.getDate() - 1);
        return _date;
    }

    public static Date SetDataHMS(Date date, int h, int m, int s) {
        Date _date = new Date(date.getTime());
        _date.setHours(h);
        _date.setMinutes(m);
        _date.setSeconds(s);
        return _date;
    }

    public static Date AddDays(Date date, int days) {
        Date current = new Date(date.getTime());
        current.setDate(date.getDate() + days);
        return current;
    }

    public static Date ConvertWeekToRangeDate(int year, int week) {
        Date current = SetDataHMS(DateFromString("01-01-" + year), 0, 0, 0);
        Date start = new Date(current.getTime());
        start.setDate(start.getDate() - start.getDay());
        if (week > 1) for (int k = 1; k < week; k++) start = AddDays(start, 7);
        return start;
    }

    public static Date[] ConvertRangeWeekToRangeDate(int year, int week) {
        Date start = ConvertWeekToRangeDate(year, week);
        return new Date[] { start, AddDays(start, 6) };
    }
}
