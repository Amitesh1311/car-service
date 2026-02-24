package com.carRental.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.carRental.dto.BookACarDto;
import com.carRental.dto.CarsDto;
import com.carRental.entity.BookACar;
import com.carRental.entity.Cars;
import com.carRental.entity.UserInfo;
import com.carRental.repository.BookACarRepository;
import com.carRental.repository.CarRepository;
import com.carRental.repository.UserRepository;

@Service
public class CarService {

	@Autowired
	private CarRepository carRepository;

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public BookACarRepository bookACarRepository;

	public ResponseEntity<?> postCar(CarsDto carsDto) {
		try {

			Cars car = new Cars();

			car.setName(carsDto.getName());
			car.setBrand(carsDto.getBrand());
			car.setColor(carsDto.getColor());
			car.setPrice(carsDto.getPrice());
			car.setModelYear(carsDto.getModelYear());
			car.setType(carsDto.getType());
			car.setDescription(carsDto.getDescription());
			car.setTransmission(carsDto.getTransmission());
			car.setImage(carsDto.getImage().getBytes());

			Cars cr = carRepository.save(car);

			if (cr != null)
				return new ResponseEntity<>(cr, HttpStatusCode.valueOf(201));
			else
				return new ResponseEntity<>("Error", HttpStatusCode.valueOf(400));

		} catch (Exception e) {
			return new ResponseEntity<>("Error", HttpStatusCode.valueOf(400));
		}
	}

	public ResponseEntity<?> getAllCars() {
		// TODO Auto-generated method stub
		try {
			List<Cars> listCars = carRepository.findAll();
			return new ResponseEntity<>(listCars, HttpStatusCode.valueOf(200));

		} catch (Exception e) {
			return new ResponseEntity<>("Error", HttpStatusCode.valueOf(400));
		}

	}

	public ResponseEntity<?> deleteCarbyId(Long id) {
		// TODO Auto-generated method stub
		try {
			carRepository.deleteById(id);
			return new ResponseEntity<>("Car Deleted Successfully", HttpStatusCode.valueOf(200));

		} catch (Exception e) {
			return new ResponseEntity<>("Error", HttpStatusCode.valueOf(400));
		}
	}

	public ResponseEntity<?> bookCar(BookACarDto bookACarDto) {
		// TODO Auto-generated method stub
		try {
			Optional<Cars> optionalCar = carRepository.findById(bookACarDto.getCarId());
			Optional<UserInfo> optionalUser = userRepository.findById(bookACarDto.getUserId());

			if (optionalCar.isPresent() && optionalUser.isPresent()) {
				BookACar bookACar = new BookACar();
				bookACar.setUser(optionalUser.get());
				bookACar.setCar(optionalCar.get());
				bookACar.setBookCarStatus(1L);
				bookACar.setFromDate(bookACarDto.getFromDate());
				bookACar.setToDate(bookACarDto.getToDate());
				bookACar.setBookCarStatus(1L);
				long diffInMilliSeconds = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();
				long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
				bookACar.setDays(days);
				bookACar.setPrice(optionalCar.get().getPrice() * days);

				bookACarRepository.save(bookACar);
				return ResponseEntity.status(HttpStatus.CREATED).build();

			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	public ResponseEntity<?> findCarById(Long id) {
		Optional<Cars> optionalCar = carRepository.findById(id);
		return new ResponseEntity<>(optionalCar.get(), HttpStatus.valueOf(201));
	}

	public ResponseEntity<?> myBookings(Long id) {
		// TODO Auto-generated method stub
		List<BookACar> listBookings = bookACarRepository.findByUserId(id);
		return new ResponseEntity<>(listBookings, HttpStatusCode.valueOf(200));

	}
}
