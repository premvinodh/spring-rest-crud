package my.play.ground.springrestcrud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import my.play.ground.springrestcrud.exceptions.EmployeeNotFoundException;
import my.play.ground.springrestcrud.models.Employee;
import my.play.ground.springrestcrud.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	public Employee getEmployee(Long id) {
		return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	public Employee createEmployee(Employee newEmployee) {
		return repository.save(newEmployee);
	}

	public Employee createOrUpdateEmployee(Employee newEmployee, Long id) {
		return repository.findById(id).map(employee -> {
			employee.setName(newEmployee.getName());
			employee.setRole(newEmployee.getRole());
			return repository.save(employee);
		}).orElseGet(() -> {
			newEmployee.setId(id);
			return repository.save(newEmployee);
		});
	}

	public void deleteEmployee(Long id) {
		repository.deleteById(id);
	}
}
