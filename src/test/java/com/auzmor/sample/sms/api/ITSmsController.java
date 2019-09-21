package com.auzmor.sample.sms.api;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.auzmor.sample.sms.AuzmorBaseIntegrationTest;
import com.auzmor.sample.sms.vo.Response;
import com.auzmor.sample.sms.vo.SmsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ITSmsController extends AuzmorBaseIntegrationTest {

	@Test
	public void shouldPassInboundSMS() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		SmsMessage message = new SmsMessage("441224459426", "4924195509198", "Hello");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(message), headers);

		Response response = restTemplate.withBasicAuth("azr1", "20S0KPNOIM").postForObject("/inbound/sms", request,
				Response.class);
		Assert.assertTrue("Response message mismatch", response.getMessage().equalsIgnoreCase("inbound sms ok"));
	}
}
