/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dorg.ajs.data.jpa.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import dorg.ajs.data.jpa.SampleDataRestApplication;
import dorg.ajs.data.jpa.domain.Employee;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Integration tests for {@link EmployeeRepository}.
 *
 * @author Oliver Gierke
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleDataRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class EmployeeServiceIntegrationTests {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	EmployeeService service;

	@Autowired
	private TestRestTemplate restTemplate;

	private URI employeesUri;

	@Before
	public void setup() throws URISyntaxException {
		employeesUri = new URI("http://localhost:" + randomServerPort + "/employees/");
	}

	@Test
	public void listAllEmployees() {
		final ResponseEntity<Employee[]> result = this.restTemplate.getForEntity(employeesUri, Employee[].class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		assertThat(((Employee[])result.getBody()).length, is(equalTo(2)));
	}

	@Test
	public void testAddEmployeeSuccess() throws URISyntaxException {
		final LocalDate birthDate = LocalDate.parse("2000-02-15");
		final LocalDate startDate = LocalDate.parse("2020-01-11");
		final Employee employee = new Employee("Andy", "J", "Schmidt", birthDate, startDate, true);
		final HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");
		final HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
		final ResponseEntity<String> result = this.restTemplate.postForEntity(employeesUri, request, String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
}
