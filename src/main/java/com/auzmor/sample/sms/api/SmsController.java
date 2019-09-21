package com.auzmor.sample.sms.api;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auzmor.sample.sms.service.SmsProcessor;
import com.auzmor.sample.sms.validation.constraint.OutboundSmsValidator;
import com.auzmor.sample.sms.validation.group.Inbound;
import com.auzmor.sample.sms.validation.group.Outbound;
import com.auzmor.sample.sms.vo.Response;
import com.auzmor.sample.sms.vo.SmsMessage;

@RestController
@Validated
public class SmsController {
	private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);

	@Autowired
	private SmsProcessor processor;

	@Autowired
	private OutboundSmsValidator outboundSmsValidator;

	@Validated(Inbound.class)
	@PostMapping("/inbound/sms")
	public ResponseEntity<?> inboundSMSHandler(@Valid @RequestBody SmsMessage message) {
		LOG.info("Inbound sms request: " + message.toString());
		processor.processInboundSms(message);
		return ResponseEntity.ok(new Response("inbound sms ok", ""));
	}

	@Validated(Outbound.class)
	@PostMapping("/outbound/sms")
	public ResponseEntity<?> outboundSMSHandler(@Valid @RequestBody SmsMessage message) {
		LOG.info("Outbound sms request: " + message.toString());
		List<Response> errors = outboundSmsValidator.validate(message);

		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}

		processor.processOutboundSms(message);

		return ResponseEntity.ok(new Response("outbound sms ok", ""));
	}

}
