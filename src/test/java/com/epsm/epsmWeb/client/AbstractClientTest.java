package com.epsm.epsmWeb.client;

import com.epsm.epsmWeb.util.UrlRequestSender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractClientTest {

	private final static String URL = "someUrl";
	
	@InjectMocks
	private AbstractClient<Object> client = spy(AbstractClient.class);
	
	@Mock
	private UrlRequestSender<Object> sender;
	
	@Mock
	private Object message;

	@Before
	public void setUp() {
		when(client.getUrl()).thenReturn(URL);
	}

	@Test
	public void clientPassesApiAndMessageToSender(){
		client.send(message);
		
		verify(sender).sendObjectInJsonToUrlWithPOST(URL, message);
	}
	
	@Test
	public void sendReturnsExpectedBooleanValue(){
		when(sender.sendObjectInJsonToUrlWithPOST(URL, message)).thenReturn(true);
		
		Assert.assertTrue(client.send(message));
	}
}
