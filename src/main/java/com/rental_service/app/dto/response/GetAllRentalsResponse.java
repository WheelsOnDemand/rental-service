package com.rental_service.app.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllRentalsResponse {
	private String id;
	private String carId;
	private LocalDateTime dataStarted;
	private int rentedForDays;
	private double dailyPrice;
	private double totalPrice;
}