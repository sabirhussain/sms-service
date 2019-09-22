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

/**
 * Part of integration test case, validating {@link SmsController}
 * 
 * @author sabirhussain
 *
 */
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

	@Test
	public void shouldConsiderStopRequest() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		SmsMessage message = new SmsMessage("441224459426", "4924195509198", "STOP");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(message), headers);

		Response response = restTemplate.withBasicAuth("azr1", "20S0KPNOIM").postForObject("/inbound/sms", request,
				Response.class);

		// FIXME: Dirty workaround to wait for async task completion.
		Thread.sleep(2000);

		Assert.assertTrue("Response message mismatch", response.getMessage().equalsIgnoreCase("inbound sms ok"));

		message = new SmsMessage("441224459426", "4924195509198", "HELLO");
		HttpEntity<String> request2 = new HttpEntity<String>(mapper.writeValueAsString(message), headers);

		Response[] responses = restTemplate.withBasicAuth("azr2", "54P2EOKQ47").postForObject("/outbound/sms", request2,
				Response[].class);
		Assert.assertTrue("Response message mismatch", responses[0].getError()
				.equalsIgnoreCase("sms from 441224459426 to 4924195509198 blocked by STOP request"));
	}
}
