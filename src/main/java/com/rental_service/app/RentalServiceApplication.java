package com.rental_service.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.rental_service.app.config.ModelMapperService;
import com.rental_service.app.config.ModelMapperServiceImpl;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class RentalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentalServiceApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ModelMapperService getMapperService(ModelMapper modelMapper) {
		return new ModelMapperServiceImpl(modelMapper);
	}

}
