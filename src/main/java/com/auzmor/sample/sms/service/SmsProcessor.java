package com.auzmor.sample.sms.service;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.auzmor.sample.sms.persistence.repo.CacheRepository;
import com.auzmor.sample.sms.vo.SmsMessage;

@Service
public class SmsProcessor {
	private static final Logger LOG = LoggerFactory.getLogger(SmsProcessor.class);
	private static final String STOP_WORD_REGX = ".*\\STOP\\b.*";

	@Autowired
	private CacheRepository repository;

	@Async
	public void processInboundSms(SmsMessage message) {

		if (isStopRequest(message.getText())) {
			LOG.info("Got stop request...");
			registerStopRequest(message);
		}
	}

	@Async
	public void processOutboundSms(SmsMessage message) {
		repository.incrementCounter(message.getFrom());
		LOG.info("Message sent: " + repository.getCounter(message.getFrom()));
	}

	public boolean isBlocked(String to, String from) {
		String key = to + "_" + from;
		return repository.exists(key);
	}

	private boolean isStopRequest(String text) {
		Pattern stopMatcher = Pattern.compile(STOP_WORD_REGX);
		return stopMatcher.matcher(text).find();
	}

	private void registerStopRequest(SmsMessage message) {
		String key = message.getFrom() + "_" + message.getTo();
		repository.store(key, "DUMMY");
	}
}
