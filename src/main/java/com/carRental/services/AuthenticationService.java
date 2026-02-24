package com.carRental.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.carRental.dto.AuthRequest;
import com.carRental.dto.AuthResponse;
import com.carRental.dto.RegisterRequest;
import com.carRental.entity.UserInfo;
import com.carRental.enums.UserRole;
import com.carRental.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthenticationService {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public JwtService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	public UserInfoUserDetailsService userInfoUserDetailsService;

	public ResponseEntity<?> register(RegisterRequest registerRequest) {

		Optional<UserInfo> userObj = userRepository.findByEmail(registerRequest.getEmail());
		if (!userObj.isEmpty()) {
			return new ResponseEntity<>("User with " + registerRequest.getEmail() + " already present.",
					HttpStatus.valueOf(409));
		}
		UserInfo user = new UserInfo();

		user.setEmail(registerRequest.getEmail());
		user.setName(registerRequest.getName());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);

		UserInfo retUser = userRepository.save(user);

		UserDetails userDetails = userInfoUserDetailsService.loadUserByUsername(registerRequest.getEmail());
		String token = jwtService.generateToken(userDetails);

		AuthResponse authRes = new AuthResponse();
		authRes.setToken(token);
		authRes.setId(userObj.get().getId());
		authRes.setUserRole(userObj.get().getUserRole());
		return new ResponseEntity<>(authRes, HttpStatus.valueOf(201));
	}

	public ResponseEntity<?> login(AuthRequest authRequest) {

		try {

			UserDetails userDetails = userInfoUserDetailsService.loadUserByUsername(authRequest.getEmail());
			Optional<UserInfo> userObj = userRepository.findByEmail(authRequest.getEmail());

			if (!passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {
				return new ResponseEntity<>("Incorrect Password", HttpStatus.valueOf(400));
			}

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

			String token = jwtService.generateToken(userDetails);

			AuthResponse authRes = new AuthResponse();
			authRes.setToken(token);
			authRes.setId(userObj.get().getId());
			authRes.setUserRole(userObj.get().getUserRole());
			return new ResponseEntity<>(authRes, HttpStatus.valueOf(201));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.valueOf(401));
		}
	}

	@PostConstruct
	public void createAdminAccount() {

		Optional<UserInfo> userObj = userRepository.findByUserRole(UserRole.ADMIN);

		if (userObj.isEmpty()) {
			UserInfo user = new UserInfo();

			user.setEmail("admin@tcs.com");
			user.setName("ADMIN");
			user.setPassword(passwordEncoder.encode("a"));
			user.setUserRole(UserRole.ADMIN);
			userRepository.save(user);
		}

	}
}
