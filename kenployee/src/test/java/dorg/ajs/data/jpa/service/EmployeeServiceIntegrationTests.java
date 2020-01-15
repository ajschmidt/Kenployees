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
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.core.annotation.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles(value = "scratch")
@SpringBootTest(classes = SampleDataRestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)

public class EmployeeServiceIntegrationTests {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	EmployeeService service;

	@Autowired
	private TestRestTemplate restTemplate;

	private URI employeesUri;
	private static final LocalDate birth_date = LocalDate.parse("1937-02-12");
	private static final LocalDate start_date = LocalDate.parse("2020-01-11");
	private Employee employee;

	@Before
	public void setup() throws URISyntaxException {
		employeesUri = new URI("http://localhost:" + randomServerPort + "/v1/employees/");
		restTemplate = restTemplate.withBasicAuth("admin", "secret");
		employee = new Employee("Andy", "J", "Schmidt", birth_date, start_date, true);
	}

	@Test
	@Order(1)
	public void listAllEmployees() {
		final ResponseEntity<Object> result = this.restTemplate.getForEntity(employeesUri, Object.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		assertThat(((List<Employee>) result.getBody()).size(), is(equalTo(2)));
	}

	@Test
	@Order(2)
	public void testAddDeleteActivate() throws URISyntaxException {
		final HttpHeaders headers = new HttpHeaders();
		HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
		final URI location = this.restTemplate.postForLocation(employeesUri, request);
		// Verify request succeed
		Assert.assertTrue(location.toString().contains(employeesUri.toString()));
		ResponseEntity<Employee> check = this.restTemplate.getForEntity(location, Employee.class);
		Assert.assertEquals(200, check.getStatusCodeValue());
		Long newId = check.getBody().getId();

		// now delete it
		this.restTemplate.delete(location);
		check = this.restTemplate.getForEntity(location, Employee.class);
		Assert.assertEquals(404, check.getStatusCodeValue());

		// now Activate the employee again
		employee.setId(newId);
		request = new HttpEntity<>(employee, headers);
		this.restTemplate.put(employeesUri, request);
		check = this.restTemplate.getForEntity(location, Employee.class);
		Assert.assertEquals(200, check.getStatusCodeValue());
	}
}
