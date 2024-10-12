package com.rental_service.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rental_service.app.dto.response.GetCarResponse;

import feign.Headers;

@FeignClient(value="inventoryService" ,url="${microservice.inventory}")
public interface InventoryClient {
	@RequestMapping(method = RequestMethod.GET, value = "api/cars/{carId}") 
	@Headers(value = "Content-Type: application/json")
	GetCarResponse getByCarId(@PathVariable String carId);

}