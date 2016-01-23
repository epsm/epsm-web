package com.epsm.epsmWeb.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UrlRequestSender<T> {
	private Logger logger = LoggerFactory.getLogger(UrlRequestSender.class);
	
	public boolean sendObjectInJsonToUrlWithPOST(String url, T object){;
		try{			
			URL urlObject = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
			OutputStream outStream = null;
			int responseCode = Integer.MIN_VALUE;
		
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			outStream = connection.getOutputStream();
			serialize(outStream, object);
			outStream.flush();
			outStream.close();
			responseCode = connection.getResponseCode();
			connection.disconnect();
			
			if(responseCode == 200){
				logger.debug("Sent: {} to {}, response code: {}.", object, url, responseCode);
				
				return true;
			}else{
				ObjectMapper mapper = new ObjectMapper();
				
				String jsonString = mapper.writeValueAsString(object);
				logger.debug("Sent: {} to {}, response code: {}.", jsonString, url, responseCode);
				
				return false;
			}
		}catch(Exception e){
			logger.warn("Error sending POST request to url {}. ",url, e);
		}
		
		return false;
	}
	
	private void serialize(OutputStream outStream, Object object){
		ObjectMapper mapper = new ObjectMapper();
		
        try {
			mapper.writeValue(outStream, object);
		} catch (IOException e) {
			logger.warn("Error in JSON serialization. ", e);
		}
	}
}
