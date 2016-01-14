package com.epsm.epsmWeb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.epsm.epsmWeb.configuration.JsonConfig;
import com.epsm.epsmWeb.util.MockServiceProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MockServiceProvider.class, JsonConfig.class,
		PowerStationController.class})
public class PowerStationControllerTest {
	private MockMvc mockMvc;
	String source = 
			"{\"powerObjectId\":1,"
			+ "\"realTimeStamp\":\"0001-02-03T04:05:06.000000007\","
			+ "\"simulationTimeStamp\":3723000000004,"
			+ "\"generatorQuantity\":2,"
			+ "\"generators\":{"
			+ "\"1\":{"
			+ "\"generatorTurnedOn\":true,"
			+ "\"astaticRegulatorTurnedOn\":true,"
			+ "\"generationCurve\":null,"
			+ "\"generatorNumber\":1"
			+ "},\"2\":{"
			+ "\"generatorTurnedOn\":true,"
			+ "\"astaticRegulatorTurnedOn\":false,"
			+ "\"generationCurve\":{"
			+ "\"loadByHoursInMW\":"
			+ "[64.88,59.54,55.72,51.9,48.47,48.85,48.09,57.25,76.35,91.6,100.0,99.23,"
			+ "91.6,91.6,91.22,90.83,90.83,90.83,90.83,90.83,90.83,90.83,90.83,83.96]"
			+ "},\"generatorNumber\":2"
			+ "}}}";
	
	@Autowired
	private PowerStationController controller;
	
	@Before
	public void initialize(){
		mockMvc = standaloneSetup(controller).build();
	}
	
	@Test
	public void acceptsPowerStationGenerationSchedule() throws Exception {
		mockMvc.perform(
				post("/api/powerstation/command")
				.contentType(MediaType.APPLICATION_JSON)
				.content(source))
				.andExpect(status().isOk());
	}
}
