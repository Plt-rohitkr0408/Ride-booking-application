package com.example.oder_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OderServiceApplication.class, args);
	}

}
