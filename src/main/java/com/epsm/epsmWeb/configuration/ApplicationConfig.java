package com.epsm.epsmWeb.configuration;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.epsm.epsmCore.model.dispatch.Dispatcher;
import com.epsm.epsmCore.model.generalModel.DispatchingObjectsSource;
import com.epsm.epsmCore.model.generalModel.DispatchingObjectsSourceFactory;
import com.epsm.epsmCore.model.generalModel.TimeService;

@Configuration
@ComponentScan("com.epsm.epsmWeb")
public class ApplicationConfig{
	private final LocalDateTime simulationStartDateTime = LocalDateTime.of(2000, 01, 01, 00, 00);
	private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
	
	@Bean
	public TimeService createTimeService(){
		logger.debug("Timeservice @Bean created.");
		return new TimeService();
	}
	
	@Bean
	public DispatchingObjectsSource getSource(TimeService timeservice, Dispatcher dispatcher){
		DispatchingObjectsSourceFactory factory = new DispatchingObjectsSourceFactory(
				timeservice, dispatcher, simulationStartDateTime);
		
		logger.info("EPS Model created and run.");
		
		return factory.createSource();
	}
}