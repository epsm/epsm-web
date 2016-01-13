package com.epsm.epsmWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epsm.epsmWeb.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmWeb.service.IncomingMessageService;

@RestController
@RequestMapping("/api/powerstation")
public class PowerStationController {
	
	@Autowired
	private IncomingMessageService service;
	
	@RequestMapping(value="/command", method = RequestMethod.POST)
	public @ResponseBody void acceptPowerstationGenerationSchedule(
			@RequestBody PowerStationGenerationSchedule schedule){
		service.acceptCommand(schedule);
	}
}
