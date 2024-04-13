package com.haedream.haedream.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@ReadingConverter
public class SeoulDateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {
    @SuppressWarnings("null")
    @Override
    public ZonedDateTime convert(Date source) {
        return source.toInstant().atZone(ZoneId.of("Asia/Seoul"));
    }
}
