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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import dorg.ajs.data.jpa.exception.RecordNotFoundException;
import dorg.ajs.data.jpa.domain.Employee;
import dorg.ajs.data.jpa.service.EmployeeService;

@RestController
@RequestMapping("/v1")
@Api(value = "Employee Management Service", description = "CRUD operations on Employee data")
public class EmployeeController {
	@Autowired
	EmployeeService service;

	@ApiOperation(value = "View a list of 'Active' employees", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(path = "/employees", produces = "application/json")
	public List<Employee> getAllEmployees() {
		return service.getActiveEmployees();
	}

	@ApiOperation(value = "View a specific 'Active' employee by their ID", response = Employee.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the employee"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The employee record was not found for the specified ID") })
	@GetMapping("/employees/{id}")
	public Employee getEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
		return service.getActiveEmployeeById(id);
	}

	@ApiOperation(value = "Add an employee to the employee database, and assign them an ID.", response = URI.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created the employee and a link to their record has been returned in the 'Location' header."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), })
	@PostMapping(path = "/employees", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createOrUpdateEmployee(@RequestBody Employee employee) throws RecordNotFoundException {
		Employee created = service.createEmployee(employee);
		// Create resource location
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId())
				.toUri();

		// Set 'location' header in response
		return ResponseEntity.created(location).build();
	}

	@ApiOperation(value = "Update an existing employee given their ID.", response = Employee.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated the employee."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "An employee with a the specified ID was not found"), })
	@PutMapping("/employees")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) throws RecordNotFoundException {
		Employee updated = service.updateEmployee(employee);
		return new ResponseEntity<Employee>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@ApiOperation(value = "This sets an employee's status to 'Inactive' but does not remove them from the database.", response = Employee.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The employee was disabled."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "An employee with a the specified ID was not found"), })
	@DeleteMapping("employees/{id}")
	public ResponseEntity<Employee> deleteEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
		Employee deleted = service.disableEmployeeById(id);
		return new ResponseEntity<Employee>(deleted, new HttpHeaders(), HttpStatus.OK);
	}

}
