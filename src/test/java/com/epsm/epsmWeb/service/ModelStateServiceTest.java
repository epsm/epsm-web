package com.epsm.epsmWeb.service;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.epsm.epsmCore.model.consumption.ConsumerState;
import com.epsm.epsmCore.model.consumption.ShockLoadConsumer;
import com.epsm.epsmCore.model.dispatch.DispatchingObject;
import com.epsm.epsmCore.model.generalModel.DispatchingObjectsSource;
import com.epsm.epsmCore.model.generation.PowerStation;
import com.epsm.epsmCore.model.generation.PowerStationState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PowerStation.class, ShockLoadConsumer.class})
public class ModelStateServiceTest {
	private PowerStationState powerStationState;
	private ConsumerState consumerState;
	
	@InjectMocks
	private ModelStateService service;
	
	@Mock
	private DispatchingObjectsSource source;
	
	@Before
	public void setUp(){
		HashMap<Long, DispatchingObject> objects = new HashMap<Long, DispatchingObject>();
		PowerStation powerStation = PowerMockito.mock(PowerStation.class);
		ShockLoadConsumer consumer = PowerMockito.mock(ShockLoadConsumer.class);
		powerStationState = mock(PowerStationState.class);
		consumerState = mock(ConsumerState.class);
		
		when(powerStation.getState()).thenReturn(powerStationState);
		when(consumer.getState()).thenReturn(consumerState);
		when(source.getDispatchingObjects()).thenReturn(objects);
		objects.put(1L, powerStation);
		objects.put(2L, consumer);
	}
	
	@Test(expected = NullPointerException.class)
	public void exceptionIfDispatchingObjectsSourceRetunsNullInsteadObjects(){
		when(source.getDispatchingObjects()).thenReturn(null);
		
		service.getConsumerStates();
	}
	
	@Test
	public void returnsPowerstationStates(){
		Assert.assertTrue(service.getPowerstationStates().contains(powerStationState));
		Assert.assertTrue(service.getPowerstationStates().size() == 1);
	}
	
	@Test
	public void returnsConsumerStates(){
		Assert.assertTrue(service.getConsumerStates().contains(consumerState));
		Assert.assertTrue(service.getConsumerStates().size() == 1);
	}
}
