package com.epsm.epsmWeb.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epsm.epsmCore.model.consumption.ConsumerState;
import com.epsm.epsmCore.model.generation.GeneratorState;
import com.epsm.epsmCore.model.generation.PowerStationState;
import com.epsm.epsmWeb.service.ModelStateService;

@Controller
@RequestMapping("/")
public class ModelStateController{
	
	@Autowired
	private ModelStateService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String createModelStatePage(ModelMap model) {
	 	obtainAllNecessaryParameters(model);
	 	
        return "model_state";
    }
	
	private void obtainAllNecessaryParameters(ModelMap model){
		Collection<PowerStationState> powerStationStates = null;
		Collection<ConsumerState> consumerStates = null;
		String realTimeStamp = null;
		String simulationTimeStamp = null;
		String frequency = null;
		
		powerStationStates = getPowerStationStatesContainer();
		consumerStates = getConsumerStatesContainer();
		realTimeStamp = getRealTimeStamp(powerStationStates, consumerStates);
		simulationTimeStamp = getSimulationTimeStamp(powerStationStates, consumerStates);
		frequency = getFrequency(powerStationStates);
		
		model.put("powerStationStatesContainer", powerStationStates);
		model.put("consumerStatesContainer", consumerStates);
		model.put("realTimeStamp", realTimeStamp.toString());
		model.put("simulationTimeStamp", simulationTimeStamp.toString());
		model.put("frequency", frequency);
	}
	
	private Collection<PowerStationState> getPowerStationStatesContainer(){
		return service.getPowerstationStates();
	}
	
	private Collection<ConsumerState> getConsumerStatesContainer(){
		return service.getConsumerStates();
	}
	
	private String getRealTimeStamp(Collection<PowerStationState> powerStationStates,
			Collection<ConsumerState> consumerStates){
		
		if(powerStationStates.size() != 0){
			return powerStationStates.iterator().next().getRealTimeStamp().toString();
		}else if(consumerStates.size() != 0){
			return consumerStates.iterator().next().getRealTimeStamp().toString();
		}else{
			return "unknown";
		}
	}
	
	private String getSimulationTimeStamp(Collection<PowerStationState> powerStationStates,
			Collection<ConsumerState> consumerStates){
		
		if(powerStationStates.size() != 0){
			return powerStationStates.iterator().next().getSimulationTimeStamp().toString();
		}else if(consumerStates.size() != 0){
			return consumerStates.iterator().next().getSimulationTimeStamp().toString();
		}else{
			return "unknown";
		}
	}
	
	private String getFrequency(Collection<PowerStationState> powerStationStates){
		
		if(powerStationStates.size() != 0){
			return new Float(powerStationStates.iterator().next().getFrequency()).toString();
		}else{
			return "unknown";
		}
	}
}
