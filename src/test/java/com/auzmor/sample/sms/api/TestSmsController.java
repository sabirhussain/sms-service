package com.auzmor.sample.sms.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.auzmor.sample.sms.AuzmorBaseMockTest;
import com.auzmor.sample.sms.persistence.repo.PhoneNumberRepository;
import com.auzmor.sample.sms.service.SmsProcessor;
import com.auzmor.sample.sms.validation.constraint.OutboundSmsValidator;
import com.auzmor.sample.sms.vo.Response;
import com.auzmor.sample.sms.vo.SmsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SmsController.class)
@AutoConfigureDataJpa
public class TestSmsController extends AuzmorBaseMockTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SmsProcessor processor;

	@MockBean
	private OutboundSmsValidator outboundSmsValidator;

	@MockBean
	private PhoneNumberRepository numberRepository;

	@Test
	public void shouldFailUnauthorizedRequest() throws Exception {
		SmsMessage message = new SmsMessage("abc", "xyz", "Hello");
		// BDDMockito.given(processor.processInboundSms(message)).
		mvc.perform(post("/inbound/sms", message)).andExpect(status().isUnauthorized());
		mvc.perform(post("/outbound/sms", message)).andExpect(status().isUnauthorized());
	}

	@Test
	public void shouldFailBadRequest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		SmsMessage message = new SmsMessage("441224459426", "492419550919", "Hello");

		mvc.perform(post("/inbound/sms", message).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(message)).with(user("azr1").password("20S0KPNOIM")))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].error").value("to parameter not found"));
		mvc.perform(post("/outbound/sms", message).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(message)).with(user("azr1").password("20S0KPNOIM")))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].error").value("from parameter not found"));
	}

	@Test
	public void shouldFailForBadDataType() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		SmsMessage message = new SmsMessage("abcefgh", "4924195509198", "hello");

		mvc.perform(post("/inbound/sms", message).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(message)).with(user("azr1").password("20S0KPNOIM")))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].error").value("from is not a number"));
	}

	@Test
	public void shouldConsiderStopRequest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		SmsMessage message = new SmsMessage("441224459426", "4924195509198", "STOP");

		// Used by validator
		BDDMockito.when(numberRepository.existsByAccountUsernameAndNumber("azr1", "441224459426")).thenReturn(true);
		String error = "sms from " + message.getFrom() + " to " + message.getTo() + " blocked by STOP request";

		BDDMockito.when(outboundSmsValidator.validate(any(SmsMessage.class)))
				.thenReturn(Arrays.asList(new Response("", error)));

		mvc.perform(post("/outbound/sms", message).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(message)).with(user("azr1").password("20S0KPNOIM")))
				.andExpect(status().isBadRequest()).andExpect(
						jsonPath("$[0].error").value("sms from 441224459426 to 4924195509198 blocked by STOP request"));
	}

	@Test
	public void shouldPassAuthorizedRequest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		SmsMessage message = new SmsMessage("441224459426", "492419550919", "Hello");

		// Used by validator
		BDDMockito.when(numberRepository.existsByAccountUsernameAndNumber(anyString(), anyString())).thenReturn(true);

		mvc.perform(post("/inbound/sms", message).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(message)).with(user("azr1").password("20S0KPNOIM")))
				.andExpect(status().isOk());
		mvc.perform(post("/outbound/sms", message).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(message)).with(user("azr2").password("54P2EOKQ47")))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("outbound sms ok"));
	}
}
