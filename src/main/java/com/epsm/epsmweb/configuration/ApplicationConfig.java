package com.epsm.epsmweb.configuration;

import com.epsm.epsmcore.model.dispatch.Dispatcher;
import com.epsm.epsmcore.model.simulation.Simulation;
import com.epsm.epsmcore.model.simulation.SimulationManager;
import com.epsm.epsmcore.model.simulation.TimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@ComponentScan("com.epsm.epsmweb")
public class ApplicationConfig{

	private static final LocalDateTime SIMULATION_START_DATETIME = LocalDateTime.of(2000, 01, 01, 00, 00);

	@Bean
	public Simulation getSource(Dispatcher dispatcher) {
		TimeService timeService = new TimeService();
		SimulationManager simulationManager = new SimulationManager(timeService, dispatcher, SIMULATION_START_DATETIME);

		return simulationManager.createAndRun();
	}
}