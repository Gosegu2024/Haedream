// package com.haedream.haedream.config;

// import org.springframework.core.convert.converter.Converter;
// import org.springframework.data.convert.WritingConverter;
// import java.time.ZonedDateTime;
// import java.util.Date;

// @WritingConverter
// public class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime,
// Date> {
// @Override
// public Date convert(ZonedDateTime source) {
// return Date.from(source.toInstant());
// }
// }