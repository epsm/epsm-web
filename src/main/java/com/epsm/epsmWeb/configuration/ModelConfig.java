package com.epsm.epsmWeb.configuration;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epsm.epsmCore.model.dispatch.Dispatcher;
import com.epsm.epsmCore.model.generalModel.DispatchingObjectsSource;
import com.epsm.epsmCore.model.generalModel.DispatchingObjectsSourceFactory;
import com.epsm.epsmCore.model.generalModel.TimeService;

@Configuration
public class ModelConfig{
	private final LocalDateTime simulationStartDateTime = LocalDateTime.of(2000, 01, 01, 00, 00);
	private Logger logger = LoggerFactory.getLogger(ModelConfig.class);
	
	@Autowired
	private TimeService timeservice;
	
	@Autowired
	private Dispatcher dispatcher;
	
	@Bean
	public DispatchingObjectsSource getSource(){
		DispatchingObjectsSourceFactory factory = new DispatchingObjectsSourceFactory(
				timeservice, dispatcher, simulationStartDateTime);
		
		logger.info("EPS Model created and run.");
		
		return factory.createSource();
	}
}
