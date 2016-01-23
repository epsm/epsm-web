package com.epsm.epsmWeb.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.epsm.epsmCore.model.consumption.ConsumerParametersStub;

@Component
public class ConsumerParametersClient extends AbstractClient<ConsumerParametersStub>{

	@Value("${api.consumer.esatblishconnection}")
	private String api;
	
	public boolean sendConsumerParameters(ConsumerParametersStub parameters){
		return sendMessage(parameters, api);	
	}
}