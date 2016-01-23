package com.epsm.epsmWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epsm.epsmCore.model.consumption.ConsumerParametersStub;
import com.epsm.epsmCore.model.consumption.ConsumerState;
import com.epsm.epsmCore.model.dispatch.Parameters;
import com.epsm.epsmCore.model.dispatch.State;
import com.epsm.epsmCore.model.generation.PowerStationParameters;
import com.epsm.epsmCore.model.generation.PowerStationState;
import com.epsm.epsmWeb.client.ConsumerParametersClient;
import com.epsm.epsmWeb.client.ConsumerStateClient;
import com.epsm.epsmWeb.client.PowerStationParametersClient;
import com.epsm.epsmWeb.client.PowerStationStateClient;

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
	public boolean registerObject(Parameters parameters) {
		if(parameters == null){
			String message = "OutgoingMessageServiceImpl establishConnection(...):"
		    		+ " parameters must not be null.";
			throw new IllegalArgumentException(message);
		}else if(parameters instanceof PowerStationParameters){
			return stationParametersClient.sendStationParameters((PowerStationParameters) parameters);
		}else if(parameters instanceof ConsumerParametersStub){
			return consumerParametersClient.sendConsumerParameters((ConsumerParametersStub) parameters);
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
