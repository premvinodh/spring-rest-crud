package my.play.ground.springrestcrud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import my.play.ground.springrestcrud.exceptions.EmployeeNotFoundException;
import my.play.ground.springrestcrud.models.Employee;
import my.play.ground.springrestcrud.repositories.EmployeeRepository;
import my.play.ground.springrestcrud.services.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	public EmployeeController() {
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/employees")
	public List<Employee> getEmployees() {
		return employeeService.getAllEmployees();
	}
	// end::get-aggregate-root[]

	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee newEmployee) {
		return employeeService.createEmployee(newEmployee);
	}

	// Single item
	@GetMapping("/employees/{id}")
	public Employee getEmployee(@PathVariable Long id) {
		return employeeService.getEmployee(id);
	}

	@PutMapping("/employees/{id}")
	Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		return employeeService.createOrUpdateEmployee(newEmployee, id);
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
	}
}