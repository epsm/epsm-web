package com.epsm.epsmWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epsm.epsmWeb.client.ConsumerParametersClient;
import com.epsm.epsmWeb.client.ConsumerStateClient;
import com.epsm.epsmWeb.client.PowerStationParametersClient;
import com.epsm.epsmWeb.client.PowerStationStateClient;
import com.epsm.epsmWeb.model.consumption.ConsumerParametersStub;
import com.epsm.epsmWeb.model.consumption.ConsumerState;
import com.epsm.epsmWeb.model.dispatch.Parameters;
import com.epsm.epsmWeb.model.dispatch.State;
import com.epsm.epsmWeb.model.generation.PowerStationParameters;
import com.epsm.epsmWeb.model.generation.PowerStationState;

@Component
public class OutgoingMessageServiceImpl implements OutgoingMessageService {

	@Autowired
	private PowerStationParametersClient stationParametersClient;
	
	@Autowired
	private PowerStationStateClient stationStateClient;
	
	@Autowired
	private ConsumerParametersClient consumerParametersClient;
	
	@Autowired
	private ConsumerStateClient consumerStateClient;
	
	@Override
	public void establishConnection(Parameters parameters) {
		if(parameters == null){
			String message = "OutgoingMessageServiceImpl establishConnection(...):"
		    		+ " parameters must not be null.";
			throw new IllegalArgumentException(message);
		}else if(parameters instanceof PowerStationParameters){
			stationParametersClient.sendStationParameters((PowerStationParameters) parameters);
		}else if(parameters instanceof ConsumerParametersStub){
			consumerParametersClient.sendConsumerParameters((ConsumerParametersStub) parameters);
		}else{
			String message = String.format("OutgoingMessageServiceImpl establishConnection(...):"
		    		+ " %s is unsupported", parameters.getClass().getSimpleName());
			throw new IllegalArgumentException(message);
		}
	}

	@Override
	public void acceptState(State state) {
		if(state == null){
			String message = "OutgoingMessageServiceImpl acceptState(...):"
		    		+ " type must not be null.";
			throw new IllegalArgumentException(message);
		}else if(state instanceof PowerStationState){
			stationStateClient.sendPowerStationState((PowerStationState) state);
		}else if(state instanceof ConsumerState){
			consumerStateClient.sendConsumerState((ConsumerState) state);
		}else{
			String message = String.format("OutgoingMessageServiceImpl acceptState(...):"
		    		+ " %s is unsupported", state.getClass().getSimpleName());
			throw new IllegalArgumentException(message);
		}
	}
}