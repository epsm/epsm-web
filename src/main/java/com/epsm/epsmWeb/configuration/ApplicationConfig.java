package com.epsm.epsmWeb.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import com.epsm.epsmCore.model.generalModel.TimeService;

@Configuration
@ComponentScan("com.epsm.epsmWeb")
public class ApplicationConfig{
	private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer configurer 
				= new PropertySourcesPlaceholderConfigurer();
		configurer.setLocation(new ClassPathResource("application.properties"));
        
		logger.debug("PropertySourcesPlaceholderConfigurer @Bean created.");
		
        return configurer;
    }
	
	@Bean
	public TimeService createTimeService(){
		logger.debug("Timeservice @Bean created.");
		return new TimeService();
	}
}