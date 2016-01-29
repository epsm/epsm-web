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

import com.epsm.epsmCore.model.consumption.ConsumerParametersStub;
import com.epsm.epsmWeb.util.UrlRequestSender;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerParametersClientTest {
	
	@InjectMocks
	private ConsumerParametersClient client;
	
	@Mock
	private UrlRequestSender<ConsumerParametersStub> sender;
	
	@Mock
	private ConsumerParametersStub parameters;
	
	@Before
	public void setUp() throws Exception{
		Field api = client.getClass().getDeclaredField("api");
		api.setAccessible(true);
		api.set(client, "someUrl");
	}
	
	@Test
	public void clientPassesApiAndMessageToSender(){
		client.sendConsumerParameters(parameters);
		
		verify(sender).sendObjectInJsonToUrlWithPOST("someUrl", parameters);
	}
	
	@Test
	public void sendConsumerParametersMethodReturnsRightBooleanValue(){
		when(sender.sendObjectInJsonToUrlWithPOST("someUrl", parameters)).thenReturn(true);
		
		Assert.assertTrue(client.sendConsumerParameters(parameters));
	}
}
