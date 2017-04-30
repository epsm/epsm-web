package com.epsm.epsmWeb.service;

import com.epsm.epsmcore.model.consumption.ConsumerPermission;
import com.epsm.epsmcore.model.dispatch.DispatchedConsumer;
import com.epsm.epsmcore.model.dispatch.DispatchedPowerStation;
import com.epsm.epsmcore.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmcore.model.simulation.Simulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncomingMessageService implements DispatchedPowerStation, DispatchedConsumer {

	@Autowired
	private Simulation simulation;
	
	@Override
	public void processPermissions(ConsumerPermission permission) {
		simulation.getConsumers().get(permission.getPowerObjectId()).processPermissions(permission);
	}

	@Override
	public void executeSchedule(PowerStationGenerationSchedule schedule) {
		simulation.getPowerStations().get(schedule.getPowerStationId()).executeSchedule(schedule);
	}
}
