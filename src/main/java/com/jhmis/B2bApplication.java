package com.jhmis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class B2bApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(B2bApplication.class);
	}
	public static void main(String[] args) {
		//禁止ipv6

		System.setProperty("java.net.preferIPv4Stack","true");
		SpringApplication.run(B2bApplication.class, args);
	}
}
