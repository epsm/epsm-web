package com.epsm.epsmWeb.service;

import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epsm.epsmCore.model.consumption.ConsumerParametersStub;
import com.epsm.epsmCore.model.consumption.ConsumerState;
import com.epsm.epsmCore.model.dispatch.Parameters;
import com.epsm.epsmCore.model.dispatch.State;
import com.epsm.epsmCore.model.generation.PowerStationParameters;
import com.epsm.epsmCore.model.generation.PowerStationState;
import com.epsm.epsmWeb.client.ConsumerParametersClient;
import com.epsm.epsmWeb.client.ConsumerStateClient;
import com.epsm.epsmWeb.client.PowerStationParametersClient;
import com.epsm.epsmWeb.client.PowerStationStateClient;

@RunWith(MockitoJUnitRunner.class)
public class OutgoingMessageServiceImplTest {
	private UnknownParameters unknownParameters;
	private UnknownState unknownState;
	private PowerStationParameters stationParameters;
	private ConsumerParametersStub consumerParameters;
	private PowerStationState powerStationState;
	private ConsumerState consumerState;
	
	@InjectMocks
	private OutgoingMessageServiceImpl service;
	
	@Mock
	private PowerStationParametersClient stationParametersClient;
	
	@Mock
	private PowerStationStateClient stationStateClient;
	
	@Mock
	private ConsumerParametersClient consumerParametersClient;
	
	@Mock
	private ConsumerStateClient consumerStateClient;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void usesStationParametersClientForSendingStationParameters(){
		stationParameters = new PowerStationParameters(1, LocalDateTime.MIN, LocalDateTime.MIN, 1);
		service.registerObject(stationParameters);
		
		verify(stationParametersClient).sendStationParameters(stationParameters);
	}
	
	@Test
	public void usesStationStateClientForSendingStationState(){
		powerStationState = new PowerStationState(1, LocalDateTime.MIN, LocalDateTime.MIN, 1, 0);
		service.acceptState(powerStationState);
		
		verify(stationStateClient).sendPowerStationState(powerStationState);
	}
	
	@Test
	public void usesConsumerParametersClientForSendingConsumerParameters(){
		consumerParameters = new ConsumerParametersStub(1, LocalDateTime.MIN, LocalDateTime.MIN);
		service.registerObject(consumerParameters);
		
		verify(consumerParametersClient).sendConsumerParameters(consumerParameters);
	}
	
	@Test
	public void usesConsumerStateClientForSendingConsumerState(){
		consumerState = new ConsumerState(1, LocalDateTime.MIN, LocalDateTime.MIN, 0);
		service.acceptState(consumerState);
		
		verify(consumerStateClient).sendConsumerState(consumerState);
	}
	
	@Test
	public void exceptionIfParametersTypeIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("OutgoingMessageServiceImpl establishConnection(...):"
	    		+ " parameters must not be null.");
	    
	    service.registerObject(null);
	}
	
	@Test
	public void exceptionIfSateTypeIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("OutgoingMessageServiceImpl acceptState(...):"
	    		+ " type must not be null.");
		
		service.acceptState(null);
	}
	
	@Test
	public void exceptionIfParametersTypeIsUnsupported(){
		expectedEx.expectMessage("OutgoingMessageServiceImpl establishConnection(...):"
	    		+ " UnknownParameters is unsupported");
		
		unknownParameters = new UnknownParameters();
		service.registerObject(unknownParameters);
	}
	
	private class UnknownParameters extends Parameters{
		public UnknownParameters() {
			super(1, LocalDateTime.MIN, LocalDateTime.MIN);
		}

		@Override
		public String toString() {
			return null;
		}
	}
	
	@Test
	public void exceptionIfSateTypeIsUnsupported(){
		expectedEx.expectMessage("OutgoingMessageServiceImpl acceptState(...):"
	    		+ " UnknownState is unsupported");
		
		unknownState = new UnknownState();
		service.acceptState(unknownState);
	}
	
	private class UnknownState extends State{
		public UnknownState() {
			super(1, LocalDateTime.MIN, LocalDateTime.MIN);
		}

		@Override
		public String toString() {
			return null;
		}
	}
}
