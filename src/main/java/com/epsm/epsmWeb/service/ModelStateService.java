package com.epsm.epsmWeb.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsm.epsmCore.model.consumption.Consumer;
import com.epsm.epsmCore.model.consumption.ConsumerState;
import com.epsm.epsmCore.model.dispatch.DispatchingObject;
import com.epsm.epsmCore.model.generalModel.DispatchingObjectsSource;
import com.epsm.epsmCore.model.generation.PowerStation;
import com.epsm.epsmCore.model.generation.PowerStationState;

/*
 * Temporary solution. It would be much better solution to create class SimulationState
 * in epsm State hierarhy and interface to get it, but it demands pretty big refactoring.
 */
@Service
public class ModelStateService {
	private Logger logger = LoggerFactory.getLogger(ModelStateService.class);
	
	@Autowired
	private DispatchingObjectsSource source;
	
	public Collection<PowerStationState> getPowerstationStates(){
		return filterPowerStationStates(getDispatchingObjects());
	}
	
	private Collection<DispatchingObject> getDispatchingObjects(){
		Map<Long, DispatchingObject> objectsInMap = source.getDispatchingObjects();
		
		if(objectsInMap == null){
			String message = "DispatchingObjectsSource returned null instead"
					+ " Map<Long, DispatchingObject>";
			logger.error(message);
			throw new NullPointerException(message);
		}
		
		return objectsInMap.values();
	}
	
	private Collection<PowerStationState> filterPowerStationStates(
			Collection<DispatchingObject> objects){
		
		Collection<PowerStationState> powerStationStates = new ArrayList<PowerStationState>();
		
		for(DispatchingObject object: objects){
			if(object instanceof PowerStation){
				PowerStation station = (PowerStation) object;
				powerStationStates.add((PowerStationState) station.getState());
			}
		}
		
		return powerStationStates;
	}
	
	public Collection<ConsumerState> getConsumerStates(){
		return filterConsumerStates(getDispatchingObjects());
	}
	
	private  Collection<ConsumerState> filterConsumerStates(
			Collection<DispatchingObject> objects){
		
		Collection<ConsumerState> consumerStates= new ArrayList<ConsumerState>();
		
		for(DispatchingObject object: objects){
			if(object instanceof Consumer){
				Consumer consumer = (Consumer) object;
				consumerStates.add((ConsumerState) consumer.getState());
			}
		}
		
		return consumerStates;
	}
}
