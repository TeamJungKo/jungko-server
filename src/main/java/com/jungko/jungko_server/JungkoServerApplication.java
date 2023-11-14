package com.jungko.jungko_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JungkoServerApplication {

	public static void main(final String[] args) {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
		SpringApplication.run(JungkoServerApplication.class, args);
	}

}
