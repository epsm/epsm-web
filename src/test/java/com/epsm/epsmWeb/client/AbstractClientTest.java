package com.epsm.epsmWeb.client;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epsm.epsmCore.model.bothConsumptionAndGeneration.Message;
import com.epsm.epsmWeb.util.UrlRequestSender;

@RunWith(MockitoJUnitRunner.class)
public class AbstractClientTest {
	private String url = "someUrl";
	
	@InjectMocks
	private AbstractClient<Message> client;
	
	@Mock
	private UrlRequestSender<Message> sender;
	
	@Mock
	private Message message;
	
	@Test
	public void clientPassesApiAndMessageToSender(){
		client.sendMessage(message, url);
		
		verify(sender).sendObjectInJsonToUrlWithPOST(url, message);
	}
	
	@Test
	public void sendStationParametersMethodReturnsRightBooleanValue(){
		when(sender.sendObjectInJsonToUrlWithPOST("someUrl", message)).thenReturn(true);
		
		Assert.assertTrue(client.sendMessage(message, url));
	}
}
