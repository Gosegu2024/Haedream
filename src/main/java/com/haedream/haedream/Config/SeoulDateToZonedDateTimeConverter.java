package com.haedream.haedream.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

// Date를 ZonedDateTime으로 변환하면서 서울 시간대를 적용
@ReadingConverter
public class SeoulDateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {
    @Override
    public ZonedDateTime convert(Date source) {
        return source.toInstant().atZone(ZoneId.of("Asia/Seoul"));
    }
}
