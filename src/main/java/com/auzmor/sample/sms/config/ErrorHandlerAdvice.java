package com.auzmor.sample.sms.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auzmor.sample.sms.vo.Response;

@ControllerAdvice
public class ErrorHandlerAdvice {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected List<Response> onConstraintVoilation(ConstraintViolationException e) {
		List<Response> responses = new ArrayList<>();

		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			responses.add(new Response(violation.getInvalidValue().toString(), violation.getMessage()));
		}

		return responses;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected List<Response> onMethodArgumentNotValid(MethodArgumentNotValidException e) {
		List<Response> responses = new ArrayList<>();

		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			String rejectedValue = Optional.ofNullable(fieldError.getRejectedValue()).map(v -> v.toString()).orElse("");
			responses.add(new Response(rejectedValue, fieldError.getDefaultMessage()));
		}

		return responses.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(responses);
	}
}
