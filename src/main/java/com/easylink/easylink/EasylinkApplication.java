package com.easylink.easylink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages = "com.easylink")
public class EasylinkApplication {

    public static void main(String[] args) {
		SpringApplication.run(EasylinkApplication.class, args);
	}
}
