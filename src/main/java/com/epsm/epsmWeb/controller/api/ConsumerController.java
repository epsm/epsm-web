package com.epsm.epsmWeb.controller.api;

import com.epsm.epsmWeb.service.IncomingMessageService;
import com.epsm.epsmcore.model.consumption.ConsumerPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerApi.Consumer.CONSUMER)
public class ConsumerController {
	private Logger logger = LoggerFactory.getLogger(ConsumerController.class);
	
	@Autowired
	private IncomingMessageService service;
	
	@RequestMapping(value = ControllerApi.Consumer.PERMISSION, method = RequestMethod.POST)
	public void acceptConsumptionPermission(ConsumerPermission permission){
		logger.debug("Received: {}.", permission);
		service.processPermissions(permission);
	}
}
