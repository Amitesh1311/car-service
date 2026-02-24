package com.carRental.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carRental.entity.UserInfo;
import com.carRental.entity.UserInfoUserDetails;
import com.carRental.repository.UserRepository;

@Service
public class UserInfoUserDetailsService implements UserDetailsService{

	@Autowired
	public UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserInfo> user = userRepository.findByEmail(username);
		if(!user.isEmpty()) return new UserInfoUserDetails(user.get());
		else
		throw new UsernameNotFoundException(username+ " Not Found.");
	}

}
