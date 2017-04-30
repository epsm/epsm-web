package com.epsm.epsmWeb.service;

import com.epsm.epsmcore.model.consumption.Consumer;
import com.epsm.epsmcore.model.consumption.ConsumerParameters;
import com.epsm.epsmcore.model.consumption.ConsumerState;
import com.epsm.epsmcore.model.generation.PowerStation;
import com.epsm.epsmcore.model.generation.PowerStationState;
import com.epsm.epsmcore.model.simulation.Simulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ModelStateService {
	private static final Logger logger = LoggerFactory.getLogger(ModelStateService.class);
	
	@Autowired
	private Simulation simulation;
	
	public Collection<PowerStationState> getPowerstationStates(){
		return simulation.getPowerStations().values().stream()
				.map(PowerStation::getState)
				.collect(Collectors.toList());
	}
	
	public   Collection<ConsumerState> getConsumerStates(){
		return simulation.getConsumers().values().stream()
				.map(Consumer<ConsumerParameters, ConsumerState>::getState)
				.collect(Collectors.toList());
	}
}
