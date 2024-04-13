package com.haedream.haedream.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatZonedDateTime(LocalDateTime dateTime) {
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime dateTime2 = dateTime.atZone(zoneId);

        if (dateTime2 == null)
            return "";
        return formatter.format(dateTime2);
    }
}
