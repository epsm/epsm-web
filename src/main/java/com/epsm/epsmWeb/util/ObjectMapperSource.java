package com.epsm.epsmWeb.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSource {
	private static ObjectMapper mapper;
	
	static{
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
	}
	
	public static ObjectMapper getMapper(){
		return mapper;
	}
}
