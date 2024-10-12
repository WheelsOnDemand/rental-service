package com.rental_service.app.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalRequest {
	
	@NotNull
	@NotEmpty
	private String carId;
	@NotNull
	private int rentedForDays;
	@NotNull
	private double dailyPrice;

}