package com.epsm.epsmWeb.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.epsm.epsmCore.model.generation.PowerStationParameters;

@Component
public class PowerStationParametersClient extends AbstractClient<PowerStationParameters>{

	@Value("${api.powerstation.esatblishconnection}")
	private String api;
	
	public boolean sendStationParameters(PowerStationParameters parameters){
		return sendMessage(parameters, api);
	}
}
