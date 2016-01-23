package com.epsm.epsmWeb.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.epsm.epsmCore.model.bothConsumptionAndGeneration.Message;
import com.epsm.epsmWeb.util.UrlRequestSender;

@Import(UrlRequestSender.class)
public class AbstractClient<T extends Message> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UrlRequestSender<T> sender;
	
	protected boolean sendMessage(T message, String url){
		logger.debug("Sending: {} to {}.", message, url);
		
		return sender.sendObjectInJsonToUrlWithPOST(url, message);
	}
}
