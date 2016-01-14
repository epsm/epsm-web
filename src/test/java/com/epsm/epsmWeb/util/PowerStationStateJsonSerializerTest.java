package com.epsm.epsmWeb.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epsm.epsmCore.model.generation.GeneratorState;
import com.epsm.epsmCore.model.generation.PowerStationState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class PowerStationStateJsonSerializerTest {
	private ObjectMapper mapper;
	private SimpleModule testModule;
	private PowerStationState state;
	
	@Before
	public void setUp(){
		mapper = new ObjectMapper();
		
		LocalDateTime realTimeStamp = LocalDateTime.of(1, 2, 3, 4, 5, 6, 7);
		LocalTime simulationTimeStamp = LocalTime.of(1, 2, 3, 4);
		state = new PowerStationState(884, realTimeStamp, simulationTimeStamp, 2, 50);
		GeneratorState state_1 = new GeneratorState(1, 60);
		GeneratorState state_2 = new GeneratorState(2, 70);
			
		state.addGeneratorState(state_1);
		state.addGeneratorState(state_2);
		
		
		testModule = new SimpleModule();
		testModule.addSerializer(PowerStationState.class,
				new PowerStationStateJsonSerializer());
		mapper.registerModule(testModule);
	}
	
	@Test
	public void serializesCorrect() throws JsonProcessingException{
		String expected = 
			"{\"powerObjectId\":884,"
			+ "\"realTimeStamp\":\"0001-02-03T04:05:06.000000007\","
			+ "\"simulationTimeStamp\":3723000000004,"
			+ "\"generatorQuantity\":2,"
			+ "\"frequency\":50.0,"
			+ "\"generators\":{"
			+ "\"1\":{\"generationInWM\":60.0,\"generatorNumber\":1},"
			+ "\"2\":{\"generationInWM\":70.0,\"generatorNumber\":2}}}";
		String serialized = mapper.writeValueAsString(state);
		Assert.assertEquals(expected, serialized);
	}
}
