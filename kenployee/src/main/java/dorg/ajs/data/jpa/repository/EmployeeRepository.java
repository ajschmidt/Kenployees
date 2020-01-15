package dorg.ajs.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dorg.ajs.data.jpa.domain.Employee;

@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {
 
}
