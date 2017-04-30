package com.epsm.epsmWeb.controller.api;

public interface ControllerApi {
	
	String API = "/api";
	
	interface Consumer {
		String CONSUMER = API + "/consumer";
		String PERMISSION = "/permission";
	}

	interface PowerStation {
		String POWER_STATION = API + "/powerstation";
		String SCHEDULE = "/schedule";
	}
	
}
