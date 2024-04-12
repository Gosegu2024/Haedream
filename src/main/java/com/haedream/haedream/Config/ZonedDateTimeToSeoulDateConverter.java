package com.haedream.haedream.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

// ZonedDateTime를 Date로 변환하면서 서울 시간대를 적용
@WritingConverter
public class ZonedDateTimeToSeoulDateConverter implements Converter<ZonedDateTime, Date> {
    @Override
    public Date convert(ZonedDateTime source) {
        return Date.from(source.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toInstant());
    }
}
