package com.epsm.epsmWeb.controller.api;

import com.epsm.epsmWeb.service.IncomingMessageService;
import com.epsm.epsmcore.model.generation.PowerStationGenerationSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerApi.PowerStation.POWER_STATION)
public class PowerStationController {
	private Logger logger = LoggerFactory.getLogger(PowerStationController.class);
	
	@Autowired
	private IncomingMessageService service;
	
	@RequestMapping(value = ControllerApi.PowerStation.SCHEDULE, method = RequestMethod.POST)
	public void acceptPowerstationGenerationSchedule(PowerStationGenerationSchedule schedule){
		logger.debug("Received: {}.", schedule);
		service.executeSchedule(schedule);
	}
}
