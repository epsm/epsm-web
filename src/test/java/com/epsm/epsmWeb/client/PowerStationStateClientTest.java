package com.epsm.epsmWeb.client;

import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epsm.epsmCore.model.generation.PowerStationState;
import com.epsm.epsmWeb.util.UrlRequestSender;

@RunWith(MockitoJUnitRunner.class)
public class PowerStationStateClientTest {
	
	@InjectMocks
	private PowerStationStateClient client;
	
	@Mock
	private UrlRequestSender<PowerStationState> sender;
	
	@Mock
	private PowerStationState state;
	
	@Before
	public void setUp() throws Exception{
		Field api = client.getClass().getDeclaredField("api");
		api.setAccessible(true);
		api.set(client, "someUrl");
	}
	
	@Test
	public void clientPassesApiAndMessageToSender(){
		client.sendPowerStationState(state);
		
		verify(sender).sendObjectInJsonToUrlWithPOST("someUrl", state);
	}
}
