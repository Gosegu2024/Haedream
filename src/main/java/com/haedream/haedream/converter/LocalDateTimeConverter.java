package com.haedream.haedream.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class LocalDateTimeConverter implements Converter<LocalDateTime, String> { // 생성일자 관련된 컬럼 내용 "yyyy-MM-dd
                                                                                  // HH:mm:ss" 형식으로 통일시키기
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String convert(@SuppressWarnings("null") LocalDateTime source) {
        return source.format(formatter);
    }

}
