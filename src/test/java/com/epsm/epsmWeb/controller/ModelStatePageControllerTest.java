package com.epsm.epsmWeb.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.epsm.epsmCore.model.consumption.ConsumerState;
import com.epsm.epsmCore.model.generalModel.DispatchingObjectsSource;
import com.epsm.epsmCore.model.generation.PowerStationState;
import com.epsm.epsmWeb.service.ModelStateService;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {ModelStateService.class, DispatchingObjectsSource.class})
public class ModelStatePageControllerTest {
	private MockMvc mockMvc;
	private Collection<PowerStationState> powerStationStates;
	private Collection<ConsumerState> consumerStates;
	private String realTimeStamp;
	private String simulationTimeStamp;
	private PowerStationState stationState;
	private ConsumerState consumerState;
	
	@InjectMocks
	private ModelStatePageController controller;
	
	@Mock
	private ModelStateService service;
	
	@Before
	public void setUp() throws Exception{
		mockMvc = standaloneSetup(controller).build();
		stationState = mock(PowerStationState.class);
		consumerState = mock(ConsumerState.class);
		when(stationState.getRealTimeStamp()).thenReturn(LocalDateTime.MAX);
		when(stationState.getSimulationTimeStamp()).thenReturn(LocalDateTime.MIN);
		when(stationState.getFrequency()).thenReturn(55.55f);
		when(consumerState.getRealTimeStamp()).thenReturn(LocalDateTime.MAX);
		when(consumerState.getSimulationTimeStamp()).thenReturn(LocalDateTime.MIN);
		realTimeStamp = "31.12.999999999 23:59:59";
		simulationTimeStamp = "01.01.1000000000 00:00:00";
		
		setDispatcherUrl();
	}
	
	private void setDispatcherUrl() throws Exception{
		Field dispatcherUrl = controller.getClass().getDeclaredField("dispatcherUrl");
		dispatcherUrl.setAccessible(true);
		dispatcherUrl.set(controller, "someUrl");
	}
	
	@Test
    public void createModelStatePageIfPowerStationSizeIsNotZero() throws Exception {
		prepareNotZeroSizePowerStationStateListAndNotZeroConsimerState();
		
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("model_state"))
                .andExpect(model().attribute("powerStationStatesContainer", powerStationStates))
                .andExpect(model().attribute("consumerStatesContainer", consumerStates))
                .andExpect(model().attribute("realTimeStamp", realTimeStamp))
                .andExpect(model().attribute("simulationTimeStamp", simulationTimeStamp))
                .andExpect(model().attribute("frequency", "55.55"))
                .andExpect(model().attribute("dispatcherUrl", "someUrl"));
   
        verify(service).getPowerstationStates();
        verify(service).getConsumerStates();
        verifyNoMoreInteractions(service);
	}
	
	private void prepareNotZeroSizePowerStationStateListAndNotZeroConsimerState(){
		powerStationStates = Arrays.asList(stationState);
		when(service.getPowerstationStates()).thenReturn(powerStationStates);
		consumerStates = Arrays.asList(consumerState);
		when(service.getConsumerStates()).thenReturn(consumerStates);
	}
	
	@Test
    public void createModelStatePageIfPowerStationSizeIsZeroButConsumerStateNot() throws Exception {
		prepareZeroSizePowerStationStateListAndNotZeroConsimerState();
		
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("model_state"))
                .andExpect(model().attribute("powerStationStatesContainer", powerStationStates))
                .andExpect(model().attribute("consumerStatesContainer", consumerStates))
                .andExpect(model().attribute("realTimeStamp", realTimeStamp))
                .andExpect(model().attribute("simulationTimeStamp", simulationTimeStamp))
                .andExpect(model().attribute("frequency", "unknown"))
                .andExpect(model().attribute("dispatcherUrl", "someUrl"));
   
        verify(service).getPowerstationStates();
        verify(service).getConsumerStates();
        verifyNoMoreInteractions(service);
	}
	
	private void prepareZeroSizePowerStationStateListAndNotZeroConsimerState(){
		powerStationStates = Collections.emptyList();
		when(service.getPowerstationStates()).thenReturn(powerStationStates);
		consumerStates = Arrays.asList(consumerState);
		when(service.getConsumerStates()).thenReturn(consumerStates);
	}

	@Test
    public void createModelStatePageIfPowerStationLAndConsumerStateListsSizesAreZero() throws Exception {
		prepareZeroSizePowerStationStateAndConsumerStateLists();
		
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("model_state"))
                .andExpect(model().attribute("powerStationStatesContainer", powerStationStates))
                .andExpect(model().attribute("consumerStatesContainer", consumerStates))
                .andExpect(model().attribute("realTimeStamp", "unknown"))
                .andExpect(model().attribute("simulationTimeStamp", "unknown"))
                .andExpect(model().attribute("frequency", "unknown"))
                .andExpect(model().attribute("dispatcherUrl", "someUrl"));
   
        verify(service).getPowerstationStates();
        verify(service).getConsumerStates();
        verifyNoMoreInteractions(service);
	}
	
	private void prepareZeroSizePowerStationStateAndConsumerStateLists(){
		powerStationStates = Collections.emptyList();
		when(service.getPowerstationStates()).thenReturn(powerStationStates);
		consumerStates = Collections.emptyList();
		when(service.getConsumerStates()).thenReturn(consumerStates);
	}
}
