package com.haedream.haedream.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatZonedDateTime(ZonedDateTime dateTime) {
        if (dateTime == null)
            return "";
        return formatter.format(dateTime);
    }
}
