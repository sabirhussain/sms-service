package com.auzmor.sample.sms.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.auzmor.sample.sms.persistence.repo.PhoneNumberRepository;

@Component
public class UserNumberValidator implements ConstraintValidator<UserNumber, String> {
	private static final Logger LOG = LoggerFactory.getLogger(UserNumberValidator.class);

	@Autowired
	private PhoneNumberRepository numberRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		LOG.info("inside user number validation...");
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return numberRepository.existsByAccountUsernameAndNumber(user, value);
	}

}
