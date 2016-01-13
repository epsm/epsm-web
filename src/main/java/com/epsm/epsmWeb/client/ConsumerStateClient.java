package com.epsm.epsmWeb.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.epsm.epsmWeb.model.consumption.ConsumerState;

@Component
public class ConsumerStateClient extends AbstractClient<ConsumerState>{

	@Value("${api.consumer.acceptstate}")
	private String api;
	
	public void sendConsumerState(ConsumerState state){
		sendMessage(state, api);
	}
}
