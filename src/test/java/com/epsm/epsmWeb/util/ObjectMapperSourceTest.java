package com.epsm.epsmWeb.util;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSourceTest {
	
	@Test
	public void returnsNotNullInstance(){
		Assert.assertNotNull(ObjectMapperSource.getMapper());
	}
	
	@Test
	public void returnsTheSameInstanceEachTime(){
		ObjectMapper mapper_1 = ObjectMapperSource.getMapper();
		ObjectMapper mapper_2 = ObjectMapperSource.getMapper();
		
		Assert.assertTrue(mapper_1 == mapper_2);
	}
}
