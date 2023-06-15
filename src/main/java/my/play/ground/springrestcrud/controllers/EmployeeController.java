package my.play.ground.springrestcrud.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import my.play.ground.springrestcrud.models.Employee;
import my.play.ground.springrestcrud.services.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	public EmployeeController() {
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/employees")
	public List<Employee> getEmployees(@RequestParam(required = false) Long id) {
	List<Employee> lstEmployees = new ArrayList<Employee>();
	if (id != null) {
		Employee employee = employeeService.getEmployee(id);
		lstEmployees.add(employee);
	} else {
		lstEmployees = employeeService.getAllEmployees();
	}
	return lstEmployees;
}
	// end::get-aggregate-root[]

	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee newEmployee) {
		return employeeService.createEmployee(newEmployee);
	}
	
	// Single item
	@GetMapping("/employees/{id}")
	public Employee getEmployee(@PathVariable(name = "id") Long employeeId) {
		return employeeService.getEmployee(employeeId);
	}
	
	
	// Employee as part of request body in a Get mapping
	@GetMapping("/employees/employee-as-payload-in-get")
	public Employee getEmployee(@RequestBody Employee employee) {
		return employee;
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