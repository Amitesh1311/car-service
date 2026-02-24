package com.carRental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carRental.dto.AuthRequest;
import com.carRental.dto.RegisterRequest;
import com.carRental.services.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	@Autowired
	public AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
		return authenticationService.register(registerRequest);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
		return authenticationService.login(authRequest);
	}

}
