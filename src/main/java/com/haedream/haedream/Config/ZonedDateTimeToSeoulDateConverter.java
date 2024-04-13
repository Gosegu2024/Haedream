package com.haedream.haedream.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@WritingConverter
public class ZonedDateTimeToSeoulDateConverter implements Converter<ZonedDateTime, Date> {
    @SuppressWarnings("null")
    @Override
    public Date convert(ZonedDateTime source) {
        return Date.from(source.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toInstant());
    }
}
