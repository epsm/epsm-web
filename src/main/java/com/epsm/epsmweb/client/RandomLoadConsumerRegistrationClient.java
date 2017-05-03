package com.epsm.epsmweb.client;

import com.epsm.epsmcore.model.consumption.RandomLoadConsumerParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RandomLoadConsumerRegistrationClient extends AbstractClient<RandomLoadConsumerParameters>{

	@Value("${api.randomLoadConsumer.register}")
	private String url;

	@Override
	protected String getUrl() {
		return url;
	}
}