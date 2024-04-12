package com.haedream.haedream.config;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {// ZonedDateTime 파싱될 수 있게 함
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    @Override
    public void write(JsonWriter out, ZonedDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            // 확실히 서울 시간대 정보를 포함하도록 값 저장
            ZonedDateTime seoulDateTime = value.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
            out.value(seoulDateTime.format(formatter));
        }
    }

    @Override
    public ZonedDateTime read(JsonReader in) throws IOException {
        // 입력된 시간 문자열을 파싱하여 서울 시간대 정보로 조정
        return ZonedDateTime.parse(in.nextString(), formatter).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
    }
}
