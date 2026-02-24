package com.carRental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carRental.dto.CarsDto;
import com.carRental.services.CarService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private CarService carService;
	
	@PostMapping("/car")
	public ResponseEntity<?> postCar(@ModelAttribute CarsDto carsDto){
		return carService.postCar(carsDto);
	}
	
	@GetMapping("/car")
	public ResponseEntity<?> getAllCar(){
		return carService.getAllCars();
	}
	
	
	@DeleteMapping("/car/{id}")
	public ResponseEntity<?> deleteCarbyId(@PathVariable Long id){
		return carService.deleteCarbyId(id);
	}

}
