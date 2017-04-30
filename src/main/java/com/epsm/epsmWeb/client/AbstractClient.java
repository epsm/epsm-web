package com.epsm.epsmWeb.client;

import com.epsm.epsmWeb.util.UrlRequestSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(UrlRequestSender.class)
public abstract class AbstractClient<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UrlRequestSender<T> sender;
	
	public boolean send(T message){
		logger.debug("Sending: {} to {}.", message, getUrl());
		
		return sender.sendObjectInJsonToUrlWithPOST(getUrl(), message);
	}

	protected abstract String getUrl();
}
