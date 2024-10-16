package com.rental_service.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	private String cardNo;
	private String cardHolder;
	private String cvv;
	private String cardDate;
	private double balance;
}