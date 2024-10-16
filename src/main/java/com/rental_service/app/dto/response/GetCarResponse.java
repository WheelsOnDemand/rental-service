package com.rental_service.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarResponse {
	private String id;
	private String carId;
	private double dailyPrice;
	private int modelYear;
	private String plate;
	private String brandName;
	private String modelName;
	private int state;
}