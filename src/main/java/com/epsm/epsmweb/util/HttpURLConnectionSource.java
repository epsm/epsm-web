package com.epsm.epsmweb.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HttpURLConnectionSource {
	private Logger logger = LoggerFactory.getLogger(HttpURLConnectionSource.class);
	
	public HttpURLConnection getConnection(String url){
		HttpURLConnection connection = createConnecti(url);

		logger.debug("Returned: connection for url {}.");
		
		return connection;
	}
	
	private HttpURLConnection createConnecti(String url){
		try {
			URL urlObject = new URL(url);
			return (HttpURLConnection) urlObject.openConnection();
		} catch (IOException e) {
			logger.warn("Error: while creating HttpURLConnection.", e);
			return null;
		}
	}
}
