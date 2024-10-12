package com.rental_service.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rental_service.app.dto.request.CreatePaymentRequest;
import com.rental_service.app.dto.request.CreateRentalRequest;
import com.rental_service.app.dto.request.UpdateRentalRequest;
import com.rental_service.app.dto.response.CreateRentalResponse;
import com.rental_service.app.dto.response.GetAllRentalsResponse;
import com.rental_service.app.dto.response.GetRentalResponse;
import com.rental_service.app.dto.response.UpdateRentalResponse;
import com.rental_service.app.service.RentalService;

import javax.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
public class RentalController { // aracın bilgisini aldım
	private RentalService rentalService;

	@GetMapping
	public List<GetAllRentalsResponse> getAll() {
		return this.rentalService.getAll();
	}

	@GetMapping("/{id}")
	public GetRentalResponse getById(@PathVariable String id) {
		return this.rentalService.getById(id);
	}

	@PostMapping
	public CreateRentalResponse add(@Valid @RequestBody CreateRentalRequest createRentalRequest,@RequestParam String cardNo, @RequestParam String cardHolder,
            @RequestParam String cvv) {
		CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();
		createPaymentRequest.setCardHolder(cardHolder);
		createPaymentRequest.setCardNo(cardNo);
		createPaymentRequest.setCvv(cvv);
	//	CreateRentalResponse result = this.rentalService.add(createRentalRequest,createPaymentRequest);

		return rentalService.add(createRentalRequest, createPaymentRequest);
	}
	
	@PutMapping
	public UpdateRentalResponse update(@Valid @RequestBody UpdateRentalRequest updateRentalRequest) {
		return rentalService.update(updateRentalRequest);
	}
	@DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        rentalService.delete(id);
    }
	
}