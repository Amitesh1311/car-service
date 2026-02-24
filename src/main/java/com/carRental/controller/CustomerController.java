package com.carRental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carRental.dto.BookACarDto;
import com.carRental.services.CarService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CarService carService;
	
	@GetMapping("/book")
	public ResponseEntity<?> getAllCar(){
		return carService.getAllCars();
	}
	
	@PostMapping("/book")
	public ResponseEntity<?> bookCar(@RequestBody BookACarDto bookACarDto){
		return carService.bookCar(bookACarDto);
	}
	
	@GetMapping("/book/car/{id}")
	public ResponseEntity<?> findCarById(@PathVariable Long id){
		return carService.findCarById(id);
	}
	
	@GetMapping("/myBookings/{id}")
	public ResponseEntity<?> myBookings(@PathVariable Long id){
		return carService.myBookings(id);
	}
}
