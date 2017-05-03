package com.epsm.epsmweb.util;

import java.net.HttpURLConnection;

import org.junit.Assert;
import org.junit.Test;

public class HttpURLConnectionSourceTest {
	private HttpURLConnectionSource source = new HttpURLConnectionSource();
	private HttpURLConnection connection;
	private final String RIGHT_URL = "http://localhost/";
	private final String WRONG_URL = "hkfgbhg";
	private String actualUrl;
	
	@Test
	public void returnsConnectionWithExpectedUrlIfUrlRight(){
		getConnectionWithRightUrl();
		getUrlAsString();
		
		Assert.assertEquals(RIGHT_URL, actualUrl);
	}
	
	private void getConnectionWithRightUrl(){
		connection = source.getConnection(RIGHT_URL);
	}
	
	private void getUrlAsString(){
		actualUrl = connection.getURL().toString();
	}
	
	@Test
	public void returnsNullIffUrlIsWrong(){
		getConnectionWithWrongUrl();
		
		Assert.assertNull(connection);
	}
	
	private void getConnectionWithWrongUrl(){
		connection = source.getConnection(WRONG_URL);
	}
}
