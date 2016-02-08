package com.epsm.epsmWeb.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UrlRequestSender<T> {
	private Logger logger = LoggerFactory.getLogger(UrlRequestSender.class);
	
	@Autowired
	private HttpURLConnectionSource connectionSource;
	
	@Autowired
	private ObjectMapper mapper;
	
	public boolean sendObjectInJsonToUrlWithPOST(String url, T object){
		HttpURLConnection connection = null;
		OutputStream outputStream = null;
		int responseCode = 0;
		
		if((connection = getConnection(url)) == null){
			return false;
		}
		if(! tryToAdjustConnection(connection)){
			closeConnection(connection);
			return false;
		}
		if((outputStream = getOutputStream(connection)) == null){
			closeConnection(connection);
			return false;
		}
		if(! tryToSerializeObject(outputStream, object)){
			closeConnection(connection);
			return false;
		}
		if(! tryToSendObject(connection, outputStream, object)){
			closeConnection(connection);
			return false;
		}
		
		responseCode = getResponceCode(connection);
		
		if(! isResponeCodeExpected(responseCode)){
			closeConnection(connection);
			return false;
		}
		
		closeConnection(connection);
		logger.info("Sent {} to {}.",object, url);
		
		return true;
	}
	
	private HttpURLConnection getConnection(String url){
		HttpURLConnection connection = connectionSource.getConnection(url);
		
		if(connection == null){
			logger.warn("Received: null insted HttpURLConnection from HttpURLConnection.");
		}
		
		return connection;
	}
	
	private boolean tryToAdjustConnection(HttpURLConnection connection){
		try {
			adjustConnectionProperties(connection);
			return true;
		} catch (ProtocolException e) {
			logger.warn("Error setting connection properties.");
			
			return false;
		}
	}
	
	private void adjustConnectionProperties(HttpURLConnection connection) throws ProtocolException{
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
	}
	
	private OutputStream getOutputStream(HttpURLConnection connection){
		try {
			return connection.getOutputStream();
		} catch (IOException e) {
			logger.warn("Error getting OutputString. ", e);
			
			return null;
		}
	}

	private boolean tryToSerializeObject(OutputStream outputStream, Object object){
	    try {
			mapper.writeValue(outputStream, object);
			
			return true;
		} catch (IOException e) {
			logger.warn("Error in JSON serialization. ", e);
			
			return false;
		}
	}
	
	private boolean tryToSendObject(HttpURLConnection connection, OutputStream outputStream, T object){
		try {
			flushAndCloseOutputStream(outputStream);
			
			return true;
		} catch (IOException e) {
			String objectAsJasonString = serializeObjectToString(object);
			logger.warn("Error sending {}. ", objectAsJasonString);
			
			return false;
		}
	}
	
	private void flushAndCloseOutputStream(OutputStream outputStream) throws IOException{
		outputStream.flush();
		outputStream.close();
	}
	
	private boolean isResponeCodeExpected(int responseCode){
		return responseCode == 200;
	}
	
	private int getResponceCode(HttpURLConnection connection){
		try {
			return connection.getResponseCode();
		} catch (IOException e) {
			logger.warn("Error in JSON serialization. ", e);
			
			return Integer.MIN_VALUE;
		}
	}
	
	private void closeConnection(HttpURLConnection connection){
		connection.disconnect();
	}
	
	private String serializeObjectToString(T object){
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			logger.warn("Error serializing {} to string.", e);
			
			return "";
		}
	}
}
