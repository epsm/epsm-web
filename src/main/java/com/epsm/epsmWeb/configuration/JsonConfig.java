package com.epsm.epsmWeb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.epsm.epsmCore.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmCore.model.generation.PowerStationParameters;
import com.epsm.epsmCore.model.generation.PowerStationState;
import com.epsm.epsmWeb.util.PowerStationGenerationScheduleJsonDeserializer;
import com.epsm.epsmWeb.util.PowerStationParametersJsonSerializer;
import com.epsm.epsmWeb.util.PowerStationStateJsonSerializer;


@Configuration
public class JsonConfig {
	
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		
		builder.serializerByType(PowerStationState.class, 
				new PowerStationStateJsonSerializer());
		builder.serializerByType(PowerStationParameters.class, 
				new PowerStationParametersJsonSerializer());
		builder.deserializerByType(PowerStationGenerationSchedule.class, 
				new PowerStationGenerationScheduleJsonDeserializer());
	    
	    return builder;
	}
	
}