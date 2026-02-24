package com.carRental.dto;

import java.util.Date;

public class BookACarDto {
	
	private Long id;

	private Date fromDate;
	
	private Date toDate;
	
	private Long days;
	
	private Long price;
	
	private Long  bookCarStatus;
	
	private Long userId;
	
	private Long carId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getBookCarStatus() {
		return bookCarStatus;
	}

	public void setBookCarStatus(Long bookCarStatus) {
		this.bookCarStatus = bookCarStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}
	
	
}


