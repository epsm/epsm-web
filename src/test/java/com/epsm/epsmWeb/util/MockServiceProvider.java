package com.epsm.epsmWeb.util;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epsm.epsmWeb.service.IncomingMessageService;

@Configuration
public class MockServiceProvider {
	
	@Bean
	public  IncomingMessageService mockIncomingMessageService(){
		return mock(IncomingMessageService.class);
	}
}
