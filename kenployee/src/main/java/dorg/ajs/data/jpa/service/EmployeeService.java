package dorg.ajs.data.jpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import dorg.ajs.data.jpa.exception.RecordNotFoundException;
import dorg.ajs.data.jpa.domain.Employee;
import dorg.ajs.data.jpa.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repository;

	public List<Employee> getAllEmployees() {
		List<Employee> employeeList = repository.findAll();

		if (employeeList.size() > 0) {
			return employeeList;
		} else {
			return new ArrayList<Employee>();
		}
	}

	public List<Employee> getActiveEmployees() {
		ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
        .withMatcher("active_status", ExampleMatcher.GenericPropertyMatchers.exact());
      Example<Employee> example = Example.of(new Employee("a","j","s", LocalDate.now(), LocalDate.now(), true), customExampleMatcher);
		List<Employee> employeeList = repository.findAll(example);

		if (employeeList.size() > 0) {
			return employeeList;
		} else {
			return new ArrayList<Employee>();
		}
	}

	public Employee getEmployeeById(Long id) throws RecordNotFoundException {
		Optional<Employee> employee = repository.findById(id);

		if (employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}

	public Employee getActiveEmployeeById(Long id) throws RecordNotFoundException {
		Optional<Employee> employee = repository.findById(id);

		if (employee.isPresent() && employee.get().isActiveStatus()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}

	public Employee createEmployee(Employee entity) throws RecordNotFoundException {
		entity = repository.save(entity);
		return entity;
	}

	public Employee updateEmployee(Employee entity) throws RecordNotFoundException {
		Optional<Employee> employee = repository.findById(entity.getId());

		if (employee.isPresent()) {
			Employee newEntity = employee.get();
			return updateExistingEmployee(entity, newEntity);
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}

	private Employee updateExistingEmployee(Employee entity, Employee newEntity) {
		newEntity.setFirstName(entity.getFirstName());
		newEntity.setMiddleInitial(entity.getMiddleInitial());
		newEntity.setLastName(entity.getLastName());
		newEntity.setDateOfBirth(entity.getDateOfBirth());
		newEntity.setDateOfEmployment(entity.getDateOfEmployment());
		newEntity.setActiveStatus(entity.isActiveStatus());
		return repository.save(newEntity);
	}

	public Employee deleteEmployeeById(Long id) throws RecordNotFoundException {
		Optional<Employee> employee = repository.findById(id);

		if (employee.isPresent()) {
			repository.deleteById(id);
			return employee.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}

	public Employee disableEmployeeById(Long id) throws RecordNotFoundException {
		Optional<Employee> employee = repository.findById(id);

		if (employee.isPresent()) {
			Employee disabled = employee.get();
			disabled.setActiveStatus(false);
		   return repository.save(disabled);
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}
}
