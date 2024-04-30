package com.hcl.carehe.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = { "com.hcl.carehe.common", 
									"com.hcl.carehe.admin"})
public class HclCareheAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(HclCareheAdminApplication.class, args);
	}
}
