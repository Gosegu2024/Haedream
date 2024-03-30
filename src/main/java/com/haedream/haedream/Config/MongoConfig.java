package com.haedream.haedream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import com.haedream.haedream.converter.LocalDateTimeConverter;
import java.util.Arrays;

@Configuration
public class MongoConfig {
    @SuppressWarnings("null")
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new LocalDateTimeConverter()));
    }
}
