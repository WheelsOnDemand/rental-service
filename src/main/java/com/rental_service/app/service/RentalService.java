package com.rental_service.app.service;

import java.util.List;

import com.rental_service.app.dto.request.CreatePaymentRequest;
import com.rental_service.app.dto.request.CreateRentalRequest;
import com.rental_service.app.dto.request.UpdateRentalRequest;
import com.rental_service.app.dto.response.CreateRentalResponse;
import com.rental_service.app.dto.response.GetAllRentalsResponse;
import com.rental_service.app.dto.response.GetRentalResponse;
import com.rental_service.app.dto.response.UpdateRentalResponse;

public interface RentalService {
	
	CreateRentalResponse add(CreateRentalRequest createRentalRequest,CreatePaymentRequest createPaymentRequest);
	UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest);
	GetRentalResponse getById(String id);
	List<GetAllRentalsResponse> getAll();
	void delete(String id);

	
	
}