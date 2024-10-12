package com.rental_service.app.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rental_service.app.client.InventoryClient;
import com.rental_service.app.client.PaymentClient;
import com.rental_service.app.config.ModelMapperService;
import com.rental_service.app.dto.Messages;
import com.rental_service.app.dto.request.CreatePaymentRequest;
import com.rental_service.app.dto.request.CreateRentalRequest;
import com.rental_service.app.dto.request.UpdateRentalRequest;
import com.rental_service.app.dto.response.CreateRentalResponse;
import com.rental_service.app.dto.response.GetAllRentalsResponse;
import com.rental_service.app.dto.response.GetCarResponse;
import com.rental_service.app.dto.response.GetRentalResponse;
import com.rental_service.app.dto.response.UpdateRentalResponse;
import com.rental_service.app.exception.BusinessException;
import com.rental_service.app.kafka.InvoiceProducer;
import com.rental_service.app.kafka.RentalProducer;
import com.rental_service.app.kafka.events.InvoiceCreatedEvent;
import com.rental_service.app.kafka.events.RentalCreatedEvent;
import com.rental_service.app.kafka.events.RentalUpdatedEvent;
import com.rental_service.app.model.Rental;
import com.rental_service.app.repository.RentalRepository;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService {
	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;
	private RentalProducer rentalProducer;
	private PaymentClient paymentClient;
	private InventoryClient inventoryClient;
	private InvoiceProducer invoiceProducer;

	@Override
	public List<GetAllRentalsResponse> getAll() {
		List<Rental> rentals = rentalRepository.findAll();
		List<GetAllRentalsResponse> response = rentals.stream()
				.map(rental -> modelMapperService.forResponse().map(rental, GetAllRentalsResponse.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public CreateRentalResponse add(CreateRentalRequest createRentalRequest,
			CreatePaymentRequest createPaymentRequest) {
		checkIfRentalExistsState(createRentalRequest.getCarId());
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setId(UUID.randomUUID().toString());
		double totalPrice = createRentalRequest.getDailyPrice() * createRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);

		paymentClient.paymentReceived(createPaymentRequest.getCardNo(), createPaymentRequest.getCardHolder(),
				createPaymentRequest.getCvv(), createPaymentRequest.getCardDate(), rental.getTotalPrice());

		this.rentalRepository.save(rental);

		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
		rentalCreatedEvent.setCarId(createRentalRequest.getCarId());
		rentalCreatedEvent.setMessage("Rental Created");
		this.rentalProducer.sendMessage(rentalCreatedEvent);

		InvoiceCreatedEvent invoiceCreatedEvent = new InvoiceCreatedEvent();
		invoiceCreatedEvent.setCarId(rental.getCarId());
		invoiceCreatedEvent.setFullName(createPaymentRequest.getCardHolder());
		invoiceCreatedEvent.setDailyPrice(createRentalRequest.getDailyPrice());
		invoiceCreatedEvent.setTotalPrice(rental.getTotalPrice());
		invoiceCreatedEvent.setRentedForDays(createRentalRequest.getRentedForDays());
		invoiceCreatedEvent.setRentedDate(rental.getDateStarted());
		invoiceProducer.sendMessage(invoiceCreatedEvent);

		CreateRentalResponse createRentalResponse = this.modelMapperService.forResponse().map(rental,
				CreateRentalResponse.class);
		return createRentalResponse;
	}

	@Override
	public UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest) {
		checkIfRentalExistsId(updateRentalRequest.getId());
		RentalUpdatedEvent rentalUpdatedEvent = new RentalUpdatedEvent();

		Rental rental = this.rentalRepository.findById(updateRentalRequest.getId()).get();
		rentalUpdatedEvent.setOldCarId(rental.getCarId());

		rental.setCarId(updateRentalRequest.getCarId());
		rental.setDailyPrice(updateRentalRequest.getDailyPrice());
		rental.setRentedForDays(updateRentalRequest.getRentedForDays());
		double totalPrice = updateRentalRequest.getDailyPrice() * updateRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);

		Rental rentalUpdated = this.rentalRepository.save(rental);
		rentalUpdatedEvent.setNewCarId(rentalUpdated.getCarId());
		rentalUpdatedEvent.setMessage("Rental Updated");
		rentalProducer.sendMessage(rentalUpdatedEvent);

		UpdateRentalResponse updatedRentalResponse = this.modelMapperService.forResponse().map(rental,
				UpdateRentalResponse.class);

		return updatedRentalResponse;

	}
	@Override
	public GetRentalResponse getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		checkIfRentalExistsId(id);
		this.rentalRepository.deleteById(id);
	}
	private void checkIfRentalExistsId(String id) {
		Rental rental = this.rentalRepository.findById(id).get();
		if (rental == null) {
			throw new BusinessException(Messages.RentalIdNotFound);
		}
	}

	private void checkIfRentalExistsState(@NotNull String carId) {// arabanın kiralanıp kiralanmadığı kontrolü
		GetCarResponse getCarResponse = inventoryClient.getByCarId(carId);
		if (getCarResponse.getState() == 2) {// kiralanmış arabaysa
			throw new BusinessException(Messages.CarHired);
		}

	}
	
	

}