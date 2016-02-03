package com.epsm.epsmWeb.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epsm.epsmCore.model.consumption.ConsumerState;
import com.epsm.epsmCore.model.generation.PowerStationState;
import com.epsm.epsmWeb.service.ModelStateService;

@Controller
@RequestMapping("/")
public class ModelStatePageController{
	private DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm:ss");
	private Logger logger = LoggerFactory.getLogger(ModelStatePageController.class);
	
	@Value("${dispatcher.url}")
	private String dispatcherUrl;
	
	@Autowired
	private ModelStateService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String createModelStatePage(ModelMap model, HttpServletRequest request) {
	 	obtainAllNecessaryParameters(model);
	 	logger.info("Requested: model state page from {}.", request.getRemoteAddr());
	 	
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
		model.put("realTimeStamp", realTimeStamp);
		model.put("simulationTimeStamp", simulationTimeStamp.toString());
		model.put("frequency", frequency);
		model.put("dispatcherUrl", dispatcherUrl);
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
			return formatter.format(powerStationStates.iterator().next().getRealTimeStamp());
		}else if(consumerStates.size() != 0){
			return formatter.format(consumerStates.iterator().next().getRealTimeStamp());
		}else{
			return "unknown";
		}
	}
	
	private String getSimulationTimeStamp(Collection<PowerStationState> powerStationStates,
			Collection<ConsumerState> consumerStates){
		
		if(powerStationStates.size() != 0){
			return formatter.format(powerStationStates.iterator().next().getSimulationTimeStamp());
		}else if(consumerStates.size() != 0){
			return formatter.format(consumerStates.iterator().next().getSimulationTimeStamp());
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
