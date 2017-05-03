package com.epsm.epsmweb.client;

import com.epsm.epsmcore.model.consumption.ScheduledLoadConsumerParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ScheduledLoadConsumerRegistrationClient extends AbstractClient<ScheduledLoadConsumerParameters>{

	@Value("${api.scheduledLoadConsumer.register}")
	private String url;

	@Override
	protected String getUrl() {
		return url;
	}
}