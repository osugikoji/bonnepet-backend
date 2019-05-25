package br.com.bonnepet.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static Date parseToDate(String date) {
        try {
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            return sourceFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String parseToDate(Date date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer daysBetween(Date date1, Date date2) {
        Calendar day1 = dateToCalendar(date1);
        Calendar day2 = dateToCalendar(date2);

        Calendar dayOne = (Calendar) day1.clone(),
                dayTwo = (Calendar) day2.clone();

        if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
            return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
        } else {
            if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
                //swap them
                Calendar temp = dayOne;
                dayOne = dayTwo;
                dayTwo = temp;
            }
            int extraDays = 0;

            int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

            while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
                dayOne.add(Calendar.YEAR, -1);
                // getActualMaximum() important for leap years
                extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
            }

            return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays;
        }
    }

    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    public static Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }
}
