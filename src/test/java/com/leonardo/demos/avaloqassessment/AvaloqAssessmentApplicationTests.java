package com.leonardo.demos.avaloqassessment;

import com.leonardo.demos.avaloqassessment.controller.rest.SimulationController;
import com.leonardo.demos.avaloqassessment.model.dto.Dice;
import com.leonardo.demos.avaloqassessment.model.persistence.entity.Simulation;
import com.leonardo.demos.avaloqassessment.service.SimulatorSvc;
import com.leonardo.demos.avaloqassessment.service.ValidatorSvc;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class AvaloqAssessmentApplicationTests {

	@Mock
	private ValidatorSvc validatorSvc;

	@Mock
	private SimulatorSvc simulatorSvc;

	@InjectMocks
	private SimulationController simulationController;


	//-------------------------------------------


	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@AfterAll
	void bye() {
		System.out.println("ThankYou!");
	}


	//-------------------------------------------


	@BeforeEach
	void init(TestInfo tinfo) {
		System.out.println("Begin: " + tinfo.getTestClass().get().getSimpleName() + " . " + tinfo.getTestMethod().get().getName());
	}

	@AfterEach
	void end(TestInfo tinfo) {
		System.out.println("End: " + tinfo.getTestClass().get().getSimpleName() + " . " + tinfo.getTestMethod().get().getName());
	}


	//-------------------------------------------


	@Test
	void testSimulationCustom() {

		// ARRANGE

		Map<Long,Long> results = new HashMap<>();
		results.put(2L, 2L);
		results.put(3L, 4L);
		results.put(4L, 1L);
		results.put(5L, 3L);
		results.put(6L, 5L);
		results.put(7L, 3L);
		results.put(8L, 2L);

		Mockito.when(validatorSvc.validate(Mockito.any()))
				.thenReturn(Collections.emptySet());

		Mockito.when(simulatorSvc.runSimulation(Mockito.any(Simulation.class)))
				.thenReturn(results);

		// ACT

		ResponseEntity response = simulationController.simulationCustom(2L, 6L, 20L);

		// ASSERT

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertEquals(response.getBody(), results);

		Mockito.verify(validatorSvc)
				.validate(Mockito.any(Dice.class), Mockito.any(Simulation.class));

		Mockito.verify(simulatorSvc, Mockito.times(1))
				.runSimulation(Mockito.any(Simulation.class));

	}


	@Test
	void testSimulationCustom_ValidationFailure() {

		// ARRANGE

		long dice = 2;
		long sides = 1;
		long rolls = 10;

		Mockito.when(validatorSvc.validate(Mockito.any())).thenCallRealMethod();

		// ACT

		ResponseEntity response = simulationController.simulationCustom(dice, sides, rolls);

		// ASSERT

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		assertTrue(((Set)response.getBody()).contains("Sides of a dice must be at least 4"));

		Mockito.verifyNoInteractions(simulatorSvc);

	}


}
