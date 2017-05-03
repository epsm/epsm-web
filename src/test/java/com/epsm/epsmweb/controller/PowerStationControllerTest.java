package com.epsm.epsmweb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.epsm.epsmweb.controller.api.PowerStationController;
import com.epsm.epsmcore.model.common.PowerCurve;
import com.epsm.epsmcore.model.generation.GeneratorGenerationSchedule;
import com.epsm.epsmcore.model.generation.PowerStationGenerationSchedule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epsm.epsmweb.constants.TestsConstants;
import com.epsm.epsmweb.service.IncomingMessageService;
import com.epsm.epsmweb.util.UrlRequestSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Import(UrlRequestSender.class)
@RunWith(MockitoJUnitRunner.class)
public class PowerStationControllerTest {
	private MockMvc mockMvc;
	private ObjectMapper mapper;
	private String objectInJsonString;
	private Object objectToSerialize;
	
	@InjectMocks
	private PowerStationController controller;
	
	@Mock
	private IncomingMessageService service;

	@Before
	public void initialize(){
		mockMvc = standaloneSetup(controller).build();
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
	}
	
	@Test
	public void acceptsPowerStationGenerationSchedule() throws Exception {
		prepareScheduleAsJSONString();
		
		mockMvc.perform(
				post("/api/powerstation/schedule")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectInJsonString))
				.andExpect(status().isOk());
	}
	
	private void prepareScheduleAsJSONString() throws JsonProcessingException{
		PowerStationGenerationSchedule generationSchedule = new PowerStationGenerationSchedule(1);
		PowerCurve generationCurve = new PowerCurve(TestsConstants.LOAD_BY_HOURS);
		GeneratorGenerationSchedule genrationSchedule_1 = new GeneratorGenerationSchedule(
				1, true, true, null);
		GeneratorGenerationSchedule genrationSchedule_2 = new GeneratorGenerationSchedule(
				2, true, false, generationCurve);
		generationSchedule.getGeneratorSchedules().put(1, genrationSchedule_1);
		generationSchedule.getGeneratorSchedules().put(2, genrationSchedule_2);
		
		objectToSerialize = generationSchedule;
		objectInJsonString = mapper.writeValueAsString(objectToSerialize);
	}
}
