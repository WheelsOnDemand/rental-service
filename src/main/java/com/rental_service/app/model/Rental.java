package com.rental_service.app.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="rentals", schema="rentaldb")
public class Rental {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "carId")
	private String carId;
	@Column(name = "dateStarted")
	private LocalDate dateStarted = LocalDate.now();
	@Column(name = "rentedForDays")
	private int rentedForDays;
	@Column(name = "dailyPrice")
	private double dailyPrice;
	@Column(name = "totalPrice")
	private double totalPrice;

	}
	