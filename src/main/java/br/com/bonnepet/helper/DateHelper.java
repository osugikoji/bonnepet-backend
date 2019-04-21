package br.com.bonnepet.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
