package com.epsm.epsmWeb.client;

import com.epsm.epsmcore.model.generation.PowerStationState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PowerStationStateClient extends AbstractClient<List<PowerStationState>>{

	@Value("${api.powerstation.acceptstate}")
	private String url;


	@Override
	protected String getUrl() {
		return url;
	}
}
