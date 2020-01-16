package dorg.ajs.data.jpa.web;

import java.util.List;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dorg.ajs.data.jpa.exception.RecordNotFoundException;
import dorg.ajs.data.jpa.domain.Employee;
import dorg.ajs.data.jpa.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	EmployeeService service;

	@GetMapping(path = "/", produces = "application/json")
	public List<Employee> getAllEmployees() {
		return service.getActiveEmployees();
	}

	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
		return service.getActiveEmployeeById(id);
	}

	@PostMapping(path = "/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createOrUpdateEmployee(
			@RequestBody Employee employee) throws RecordNotFoundException {
		Employee created = service.createEmployee(employee);
		// Create resource location
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId())
				.toUri();

		// Set 'location' header in response
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) throws RecordNotFoundException {
		Employee updated = service.updateEmployee(employee);
		return new ResponseEntity<Employee>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> deleteEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
		Employee deleted = service.disableEmployeeById(id);
		return new ResponseEntity<Employee>(deleted, new HttpHeaders(), HttpStatus.OK);
	}

}
