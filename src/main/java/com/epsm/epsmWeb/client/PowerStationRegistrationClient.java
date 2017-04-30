package com.epsm.epsmWeb.client;

import com.epsm.epsmcore.model.generation.PowerStationParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PowerStationRegistrationClient extends AbstractClient<PowerStationParameters>{

	@Value("${api.powerstation.register}")
	private String url;
	
	@Override
	protected String getUrl() {
		return url;
	}
}
