package com.haedream.haedream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HaedreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaedreamApplication.class, args);
	}

}
