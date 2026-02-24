package com.carRental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carRental.entity.UserInfo;
import com.carRental.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

	public Optional<UserInfo> findByEmail(String email);

	public Optional<UserInfo> findByUserRole(UserRole customer);
	
}
