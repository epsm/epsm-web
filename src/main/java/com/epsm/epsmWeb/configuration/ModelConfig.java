package com.epsm.epsmWeb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.epsm.epsmCore.model.dispatch.Dispatcher;
import com.epsm.epsmCore.model.generalModel.DispatchingObjectsSource;
import com.epsm.epsmCore.model.generalModel.DispatchingObjectsSourceFactory;
import com.epsm.epsmCore.model.generalModel.TimeService;

@Configuration
@Import(TimeService.class)
public class ModelConfig{
	
	@Autowired
	private TimeService timeservice;
	
	@Autowired
	private Dispatcher dispatcher;
	
	@Bean
	public DispatchingObjectsSource getSource(){
		DispatchingObjectsSourceFactory factory 
				= new DispatchingObjectsSourceFactory(timeservice, dispatcher);
		
		return factory.createSource();
	}
}
