package com.leonardo.demos.avaloqassessment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = AvaloqAssessmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AvaloqAssessmentApplicationContextTests {

	@Value("${local.server.port}")
	private int port = 0;


	@Test
	void testContextLoads() {

		// ARRANGE

		String url0 = "http://localhost:" + port + "/api/dice/v1/simulations/all";
		String url1 = "http://localhost:" + port + "/api/dice/v1/rolls/all";
		String url2 = "http://localhost:" + port + "/api/dice/v1/stats/grouped";

		// ACT

		ResponseEntity response0 = new TestRestTemplate().getForEntity(url0, List.class);
		ResponseEntity response1 = new TestRestTemplate().getForEntity(url1, List.class);
		ResponseEntity response2 = new TestRestTemplate().getForEntity(url2, List.class);

		// ASSERT

		assertEquals(HttpStatus.OK, response0.getStatusCode());
		assertEquals(HttpStatus.OK, response1.getStatusCode());
		assertEquals(HttpStatus.OK, response2.getStatusCode());
	}

}
