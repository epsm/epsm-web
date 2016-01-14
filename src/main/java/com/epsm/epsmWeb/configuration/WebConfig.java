package com.epsm.epsmWeb.configuration;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.epsm.epsmCore.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmCore.model.generation.PowerStationParameters;
import com.epsm.epsmCore.model.generation.PowerStationState;
import com.epsm.epsmWeb.util.PowerStationGenerationScheduleJsonDeserializer;
import com.epsm.epsmWeb.util.PowerStationParametersJsonSerializer;
import com.epsm.epsmWeb.util.PowerStationStateJsonSerializer;

@Configuration
@EnableWebMvc
@ComponentScan("com.epsm.epsmWeb.controller")
public class WebConfig extends WebMvcConfigurerAdapter {
	
	/*@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		
		builder.serializerByType(PowerStationState.class, 
				new PowerStationStateJsonSerializer());
		builder.serializerByType(PowerStationParameters.class, 
				new PowerStationParametersJsonSerializer());
		builder.deserializerByType(PowerStationGenerationSchedule.class, 
				new PowerStationGenerationScheduleJsonDeserializer());
		
		converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
	
	}*/
}
