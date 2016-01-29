package com.epsm.epsmWeb.client;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epsm.epsmCore.model.generation.PowerStationParameters;
import com.epsm.epsmWeb.util.UrlRequestSender;

@RunWith(MockitoJUnitRunner.class)
public class PowerStationParametersClientTest {
	
	@InjectMocks
	private PowerStationParametersClient client;
	
	@Mock
	private UrlRequestSender<PowerStationParameters> sender;
	
	@Mock
	private PowerStationParameters parameters;
	
	@Before
	public void setUp() throws Exception{
		Field api = client.getClass().getDeclaredField("api");
		api.setAccessible(true);
		api.set(client, "someUrl");
	}
	
	@Test
	public void clientPassesApiAndMessageToSender(){
		client.sendStationParameters(parameters);
		
		verify(sender).sendObjectInJsonToUrlWithPOST("someUrl", parameters);
	}
	
	@Test
	public void sendStationParametersMethodReturnsRightBooleanValue(){
		when(sender.sendObjectInJsonToUrlWithPOST("someUrl", parameters)).thenReturn(true);
		
		Assert.assertTrue(client.sendStationParameters(parameters));
	}
}
