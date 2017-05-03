package com.epsm.epsmweb.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.epsm.epsmcore.model.consumption.ConsumerState;

import java.util.List;

@Component
public class ConsumerStateClient extends AbstractClient<List<ConsumerState>> {

	@Value("${api.consumer.acceptstate}")
	private String url;

	@Override
	protected String getUrl() {
		return url;
	}
}
