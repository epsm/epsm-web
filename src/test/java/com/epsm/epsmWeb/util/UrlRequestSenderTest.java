package com.epsm.epsmWeb.util;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class UrlRequestSenderTest {
	private Object object = new Object();
	private String url = "test_url";
	private boolean executionResult;
	private final int BAD_RESPONSE_CODE = Integer.MIN_VALUE;
	private final int GOOD_RESPONSE_CODE = 200;
		
	@InjectMocks
	private UrlRequestSender<Object> sender;
	
	@Mock
	private ObjectMapper mapper;
	
	@Mock
	private HttpURLConnectionSource connectionSource;
	
	@Mock
	private HttpURLConnection connection;
	
	@Mock
	private OutputStream outputStream;
	
	@Before
	public void setUp() throws IOException{
		makeConnectionSuccessfullSendMessage();
	}
	
	private void makeConnectionSuccessfullSendMessage() throws IOException{
		when(connectionSource.getConnection(url)).thenReturn(connection);
		when(connection.getOutputStream()).thenReturn(outputStream);
		when(connection.getResponseCode()).thenReturn(GOOD_RESPONSE_CODE);
	}
	
	@Test
	public void triesToGetHttpUrlConnectionFromSource(){
		makeSenderSendObject();
		
		verify(connectionSource).getConnection(url);
	}
	
	private void makeSenderSendObject(){
		executionResult = sender.sendObjectInJsonToUrlWithPOST(url, object);
	}
	
	@Test
	public void returnsFalseIfConnectionIsNull(){
		makeConnectionNull();
		makeSenderSendObject();
		
		Assert.assertFalse(executionResult);
	}
	
	private void makeConnectionNull(){
		when(connectionSource.getConnection(url)).thenReturn(null);
	}
	
	@Test
	public void triesAdjustConnection() throws ProtocolException{
		makeSenderSendObject();
		
		verify(connection).setRequestProperty("Content-Type", "application/json");
		verify(connection).setRequestMethod("POST");
		verify(connection).setDoOutput(true);
	}
	
	@Test
	public void returnsFalseIfThereIsExceptionWhileAdjustingConnection() throws ProtocolException{
		makeAdjustConnectionThrowException();
		makeSenderSendObject();
		
		Assert.assertFalse(executionResult);
	}
	
	private void makeAdjustConnectionThrowException() throws ProtocolException{
		doThrow(new ProtocolException()).when(connection).setRequestMethod("POST");
	}
	
	@Test
	public void triesToGetOutoutStream() throws IOException{
		makeSenderSendObject();
		
		verify(connection).getOutputStream();
	}
	
	@Test
	public void returnsFalseIfThereIsExceptionWhileGettingOutputStream() throws IOException{
		makeAdjustGetOutputstreamThrowException();
		makeSenderSendObject();
		
		Assert.assertFalse(executionResult);
	}
	
	private void makeAdjustGetOutputstreamThrowException() throws IOException{
		doThrow(new IOException()).when(connection).getOutputStream();
	}
	
	@Test
	public void triesToSerializeObject() throws JsonGenerationException, JsonMappingException, IOException{
		sender.sendObjectInJsonToUrlWithPOST(url, object);
		
		verify(mapper).writeValue(outputStream, object);
	}
	
	@Test
	public void returnsFalseIfThereIsExceptionWhileSereliazing() throws IOException{
		makeWriteValueThrowException();
		makeSenderSendObject();
		
		Assert.assertFalse(executionResult);
	}
	
	private void makeWriteValueThrowException() throws IOException{
		doThrow(new IOException()).when(mapper).writeValue(outputStream, object);
	}
	
	@Test
	public void triesToFlushOutputstream() throws IOException{
		makeSenderSendObject();
		
		verify(outputStream).flush();
	}
	
	@Test
	public void returnsFalseIfThereIsExceptionWhileFlushingOutputStream() throws IOException{
		makeOutputStreamFlushThrowException();
		makeSenderSendObject();
		
		Assert.assertFalse(executionResult);
	}
	
	private void makeOutputStreamFlushThrowException() throws IOException{
		doThrow(new IOException()).when(outputStream).flush();
	}

	@Test
	public void triesToCloseOutputstream() throws IOException{
		makeSenderSendObject();
		
		verify(outputStream).close();
	}
	
	@Test
	public void triesToGetResponseCode() throws IOException{
		makeSenderSendObject();
		
		verify(connection).getResponseCode();
	}
	
	@Test
	public void returnsFalseIfThereIsExceptionWhilegettungResponseCode() throws IOException{
		makeGetResponseCodeThrowException();
		makeSenderSendObject();
		
		Assert.assertFalse(executionResult);
	}
	
	private void makeGetResponseCodeThrowException() throws IOException{
		doThrow(new IOException()).when(connection).getResponseCode();
	}
	
	@Test
	public void returnsFalseIfResponseCodeNotGood() throws IOException{
		makeConnectionReturnBadResponseCode();
		makeSenderSendObject();
		
		Assert.assertFalse(executionResult);
	}
	
	private void makeConnectionReturnBadResponseCode() throws IOException{
		when(connection.getResponseCode()).thenReturn(BAD_RESPONSE_CODE);
	}
	
	@Test
	public void triesToDisconnect() throws IOException{
		makeSenderSendObject();
		
		verify(connection).disconnect();
	}
	
	@Test
	public void returnsTrueIfObjectSuccessfullySent(){
		makeSenderSendObject();
		
		Assert.assertTrue(executionResult);
	}
}
