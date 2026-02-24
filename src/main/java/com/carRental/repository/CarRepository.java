package com.carRental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carRental.entity.Cars;

@Repository
public interface CarRepository  extends JpaRepository<Cars, Long>{

}
