package com.ftn.uns.travelplaner.util;

import com.ftn.uns.travelplaner.model.Transportation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

public class DateTimeFormatter {

    public static final java.time.format.DateTimeFormatter DATE_TIME_PATTERN =
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final String DATE_PATTERN = "%d %s %d";
    private static final String DURATION_PATTERN = "%s -> %s";
    private static final String TIME_PATTERN = "%02d:%02d";

    public static String formatDurationView(Transportation origin, Transportation destination) {
        LocalDateTime originDepartureTime = origin.departure;
        LocalDateTime destinationDepartureTime = destination.departure;

        String startDate = formatDate(LocalDate.from(originDepartureTime));
        String endDate = formatDate(LocalDate.from(destinationDepartureTime));

        return String.format(Locale.getDefault(), DURATION_PATTERN, startDate, endDate);
    }

    public static String formatDate(LocalDate date) {
        return String.format(
                Locale.getDefault(),
                DATE_PATTERN,
                date.getDayOfMonth(),
                date.getMonth().name(),
                date.getYear());
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return DATE_TIME_PATTERN.format(dateTime);
    }

    public static String formatTime(LocalTime time) {
        return String.format(Locale.getDefault(), TIME_PATTERN, time.getHour(), time.getMinute());
    }
}
