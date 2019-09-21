package com.auzmor.sample.sms.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auzmor.sample.sms.persistence.entity.PhoneNumber;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {

	boolean existsByAccountUsernameAndNumber(String username, String number);
}
