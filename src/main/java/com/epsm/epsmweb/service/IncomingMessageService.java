package com.epsm.epsmweb.service;

import com.epsm.epsmcore.model.consumption.ConsumerPermission;
import com.epsm.epsmcore.model.dispatch.DispatchedConsumer;
import com.epsm.epsmcore.model.dispatch.DispatchedPowerStation;
import com.epsm.epsmcore.model.generation.PowerStation;
import com.epsm.epsmcore.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmcore.model.simulation.Simulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncomingMessageService implements DispatchedPowerStation, DispatchedConsumer {

	private static final Logger logger = LoggerFactory.getLogger(IncomingMessageService.class);

	@Autowired
	private Simulation simulation;
	
	@Override
	public void processPermissions(ConsumerPermission permission) {
		simulation.getConsumers().get(permission.getPowerObjectId()).processPermissions(permission);
	}

	@Override
	public void executeSchedule(PowerStationGenerationSchedule schedule) {
		PowerStation powerStation = simulation.getPowerStations().get(schedule.getPowerStationId());

		if(powerStation != null) {
			powerStation.executeSchedule(schedule);
		} else {
			logger.warn("Acceptet schedule {} for not exist power station.", schedule);
		}
	}
}
