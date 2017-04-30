package com.epsm.epsmWeb.service;

import com.epsm.epsmWeb.client.*;
import com.epsm.epsmcore.model.consumption.ConsumerState;
import com.epsm.epsmcore.model.consumption.RandomLoadConsumerParameters;
import com.epsm.epsmcore.model.consumption.ScheduledLoadConsumerParameters;
import com.epsm.epsmcore.model.dispatch.Dispatcher;
import com.epsm.epsmcore.model.generation.PowerStationParameters;
import com.epsm.epsmcore.model.generation.PowerStationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutgoingMessageService implements Dispatcher {

	@Autowired
	private PowerStationRegistrationClient stationRegistrationClient;
	
	@Autowired
	private PowerStationStateClient stationStateClient;
	
	@Autowired
	private RandomLoadConsumerRegistrationClient randomLoadConsumerRegistrationClient;

	@Autowired
	private ScheduledLoadConsumerRegistrationClient scheduledLoadConsumerRegistrationClient;

	@Autowired
	private ConsumerStateClient consumerStateClient;

	@Override
	public boolean register(RandomLoadConsumerParameters parameters) {
		return randomLoadConsumerRegistrationClient.send(parameters);
	}

	@Override
	public boolean register(ScheduledLoadConsumerParameters parameters) {
		return scheduledLoadConsumerRegistrationClient.send(parameters);
	}

	@Override
	public boolean register(PowerStationParameters parameters) {
		return stationRegistrationClient.send(parameters);
	}

	@Override
	public boolean acceptConsumerStates(List<ConsumerState> states) {
		return consumerStateClient.send(states);
	}

	@Override
	public boolean acceptPowerStationStates(List<PowerStationState> states) {
		return stationStateClient.send(states);
	}
}
