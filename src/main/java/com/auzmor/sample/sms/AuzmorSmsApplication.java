package com.auzmor.sample.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AuzmorSmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuzmorSmsApplication.class, args);
	}

}
