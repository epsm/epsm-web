package com.epsm.epsmWeb.controller;

import com.epsm.epsmWeb.controller.api.ConsumerController;
import com.epsm.epsmWeb.service.IncomingMessageService;
import com.epsm.epsmcore.model.consumption.ConsumerPermission;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerControllerTest {
	private MockMvc mockMvc;
	private ObjectMapper mapper;
	
	@InjectMocks
	private ConsumerController controller;
	
	@Mock
	private IncomingMessageService service;
	
	@Before
	public void initialize(){
		mockMvc = standaloneSetup(controller).build();
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
	}
	
	@Test
	public void acceptsConsumerPermission() throws Exception{
		ConsumerPermission consumerPermission = new ConsumerPermission(1, true);

		mockMvc.perform(
				post("/api/consumer/permission")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(consumerPermission)))
				.andExpect(status().isOk());
	}
}
