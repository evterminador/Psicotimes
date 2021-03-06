package com.example.formandocodigo.psicotimes.utils;

import com.example.formandocodigo.psicotimes.login.net.entity.ApiError;
import com.example.formandocodigo.psicotimes.login.net.RetrofitBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by FormandoCodigo on 29/11/2017.
 */

public class Converts {

    public static ApiError convertErrors(ResponseBody response) {
        Converter<ResponseBody, ApiError> converter = RetrofitBuilder
                .getRetrofit()
                .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError apiError = null;

        try {
            apiError = converter.convert(response);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return apiError;
    }

    public static String convertLongToTimeChar(long usedTime) {
        String day="", hour="", min="", sec="";

        int d = (int) ((usedTime/1000/60/60/24));
        if (d!=0)
            day = d+"d ";

        int h=(int)((usedTime/1000/60/60) % 24);
        if (h!=0)
            hour = h+"h ";

        int m=(int)((usedTime/1000/60) % 60);
        if (m!=0)
            min = m+"m ";

        int s=(int)((usedTime/1000) % 60);
        if (s==0 && (h!=0 || m!=0))
            sec="";
        else
            sec = s+"s";

        return day+hour+min+sec;
    }

    public static String convertTimestampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return dateFormat.format(timestamp);
    }

    public static String convertTimestampToStringShort(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(timestamp);
    }

    public static Timestamp convertStringToTimestamp(String value) throws ParseException {
        SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = dateFormat.parse(value);

        return new Timestamp(date.getTime());
    }

    public static Calendar getCalendarForNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    public static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static void setTimeToEndofDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    public static int getDayTimesTamp(Timestamp timestamp) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(timestamp);

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return day;
    }
}
