package com.epsm.epsmWeb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epsm.epsmCore.model.generalModel.DispatchingObjectsSource;
import com.epsm.epsmCore.model.generalModel.TimeService;

@RestController
@RequestMapping("/")
public class IndicatorController {
	private Logger logger = LoggerFactory.getLogger(PowerStationController.class);
	
	@Autowired
	private DispatchingObjectsSource source;
	
	@Autowired
	private TimeService timeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String acceptConsumptionPermission(){
		logger.debug("Invoked.");
		return String.format("date and time on server: %s, objects in simulation: %s",
				timeService.getCurrentTime(), source.getDispatchingObjects());
	}
}