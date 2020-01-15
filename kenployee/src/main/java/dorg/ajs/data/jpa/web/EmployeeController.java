package dorg.ajs.data.jpa.web;

import java.util.List;

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

import dorg.ajs.data.jpa.exception.RecordNotFoundException;
import dorg.ajs.data.jpa.domain.Employee;
import dorg.ajs.data.jpa.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	EmployeeService service;

	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> list = service.getActiveEmployees();
		return new ResponseEntity<List<Employee>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
		Employee entity = service.getActiveEmployeeById(id);

		return new ResponseEntity<Employee>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Employee> createOrUpdateEmployee(@RequestBody Employee employee) throws RecordNotFoundException {
		Employee updated = service.createEmployee(employee);
		return new ResponseEntity<Employee>(updated, new HttpHeaders(), HttpStatus.OK);
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
