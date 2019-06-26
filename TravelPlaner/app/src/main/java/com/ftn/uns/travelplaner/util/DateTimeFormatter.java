package com.ftn.uns.travelplaner.util;

import com.ftn.uns.travelplaner.model.Transportation;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatter {

    //public static final java.time.format.DateTimeFormatter DATE_TIME_PATTERN =
            //java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    //public static final java.time.format.DateTimeFormatter DATE_PATTERN =
            //java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private static final String DURATION_PATTERN = "%s -> %s";
    private static final String TIME_PATTERN = "%02d:%02d";

    public static String formatDurationView(Transportation origin, Transportation destination) {
        Date originDepartureTime = origin.departure;
        Date destinationDepartureTime = destination.departure;

        //String startDate = formatDate(LocalDate.from(originDepartureTime));
        String startDate = formatDate(originDepartureTime);
        //String endDate = formatDate(LocalDate.from(destinationDepartureTime));
        String endDate = formatDate(destinationDepartureTime);

        return String.format(Locale.getDefault(), DURATION_PATTERN, startDate, endDate);
    }

    public static String formatDate(Date date) {

        //return DATE_PATTERN.format(date);
        return DATE_FORMAT.format(date);
    }

    public static String formatDateTime(Date dateTime) {
        return DATE_TIME_FORMAT.format(dateTime);
    }

    public static String formatTime(Date time) {
        return String.format(Locale.getDefault(), TIME_PATTERN, time.getHours(), time.getMinutes());
    }
}
