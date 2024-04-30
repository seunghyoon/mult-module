package com.hcl.carehe.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan(basePackages = { "com.hcl.carehe.common", 
									"com.hcl.carehe.app"})
public class HclCareheAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(HclCareheAppApplication.class, args);
	}
}
