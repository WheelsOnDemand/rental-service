package com.rental_service.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRentalRequest {
	private String id;

	private String carId;
	
	private int rentedForDays;
	private double dailyPrice;
}