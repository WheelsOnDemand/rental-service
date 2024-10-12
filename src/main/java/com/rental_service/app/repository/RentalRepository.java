package com.rental_service.app.repository;
	
import org.springframework.data.jpa.repository.JpaRepository;

import com.rental_service.app.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, String>   {
	Rental findByCarId(String carId);

}