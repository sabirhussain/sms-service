package com.auzmor.sample.sms.validation.constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auzmor.sample.sms.persistence.repo.CacheRepository;
import com.auzmor.sample.sms.service.SmsProcessor;
import com.auzmor.sample.sms.vo.Response;
import com.auzmor.sample.sms.vo.SmsMessage;

@Component
public class OutboundSmsValidator {

	@Autowired
	private SmsProcessor processor;

	@Autowired
	private CacheRepository repository;

	public List<Response> validate(SmsMessage message) {
		List<Response> responses = new ArrayList<>();

		if (processor.isBlocked(message.getTo(), message.getFrom())) {
			String error = "sms from " + message.getFrom() + " to " + message.getTo() + " blocked by STOP request";
			responses.add(new Response("", error));
		}

		if (repository.getCounter(message.getFrom()) >= 50) {
			String error = "limit reached for from " + message.getFrom();
			responses.add(new Response("", error));
		}

		return responses.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(responses);
	}
}
