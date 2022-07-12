package my.play.ground.springrestcrud.services;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import my.play.ground.springrestcrud.exceptions.EmployeeNotFoundException;
import my.play.ground.springrestcrud.models.Employee;
import my.play.ground.springrestcrud.repositories.EmployeeRepository;

/**
 * https://www.youtube.com/watch?v=gIb_m06XeQE
 */
// If this is not present then the repository and employeeService would be null
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

	@MockBean
	private EmployeeRepository repository;

	@InjectMocks
	private EmployeeService employeeService;

	@Test
	public void getAllEmployees_ShouldReturnListWith2Employees_WhenEmployeesPresent() throws Exception {
		// arrange - train your mock
		given(repository.findAll()).willReturn(
				List.of(new Employee(1, "Bilbo Baggins", "burglar"), new Employee(2, "Frodo Baggins", "thief")));

		// act & assert
		List<Employee> employees = this.employeeService.getAllEmployees();
		assertThat(employees.size(), is(2));
		// First Employee
		assertThat(employees.get(0).getId(), is(1L));
		assertThat(employees.get(0).getName(), is("Bilbo Baggins"));
		assertThat(employees.get(0).getRole(), is("burglar"));
		// Second Employee
		assertThat(employees.get(1).getId(), is(2L));
		assertThat(employees.get(1).getName(), is("Frodo Baggins"));
		assertThat(employees.get(1).getRole(), is("thief"));

		// verify that method was invoked as we have used a mock repository
		verify(repository, times(1)).findAll();
	}

	@Test
	public void getAllEmployees_ShouldReturnEmptyList_WhenNoEmployeesPresent() throws Exception {
		// arrange - train your mock
		given(repository.findAll()).willReturn(Collections.EMPTY_LIST);

		// act & assert
		List<Employee> employees = this.employeeService.getAllEmployees();
		assertThat(employees.size(), is(0));

		// verify that method was invoked as we have used a mock repository
		verify(repository, times(1)).findAll();
	}

	@Test
	public void getEmployee_ShouldReturn1Employee_WhenEmployeePresent() throws Exception {
		// arrange - train your mock
		given(repository.findById(anyLong())).willReturn(Optional.of(new Employee(1, "Bilbo Baggins", "burglar")));

		// act & assert
		Employee employee = this.employeeService.getEmployee(1L);
		assertThat(employee.getId(), is(1L));
		assertThat(employee.getName(), is("Bilbo Baggins"));
		assertThat(employee.getRole(), is("burglar"));

		// verify that method was invoked as we have used a mock repository
		verify(repository, times(1)).findById(anyLong());
	}

	@Test
	public void getEmployee_ShouldThrowEmployeeNotFoundException_WhenNoEmployeePresent() throws Exception {
		// arrange - train your mock
		given(repository.findById(anyLong())).willReturn(Optional.empty());

		// act & assert
		// TODO:- need to use hamcrest but using Assertions of Junit 5 :(
		EmployeeNotFoundException exception = Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
			this.employeeService.getEmployee(1L);
		});
		Assertions.assertEquals("Could not find employee 1", exception.getMessage());

		// verify that method was invoked as we have used a mock repository
		verify(repository, times(1)).findById(anyLong());
	}

	@Test
	public void createEmployee_ShouldReturnEmployeeObject_WithIdPopulated() throws Exception {
		Employee employee = new Employee(1, "Gandalf", "wizard");
		// arrange - train your mock
		given(repository.save(any())).willReturn(employee);

		// act & assert
		Employee savedEmployee = this.employeeService.createEmployee(employee);
		assertThat(savedEmployee.getId(), is(1L));
		assertThat(savedEmployee.getName(), is("Gandalf"));
		assertThat(savedEmployee.getRole(), is("wizard"));

		// verify that method was invoked as we have used a mock repository
		verify(repository, times(1)).save(any());
	}

	@Test
	public void createOrUpdateEmployee_ShouldUpdateEmployee_WhenEmployeePresent() throws Exception {
		/**
		 * -------------------- First insert an new employee --------------------
		 */
		Employee employee = new Employee(1, "Gandalf", "wizard");
		// arrange - train your mock
		given(repository.save(any())).willReturn(employee);

		// act & assert
		Employee savedEmployee = this.employeeService.createEmployee(employee);
		assertThat(savedEmployee.getId(), is(1L));
		assertThat(savedEmployee.getName(), is("Gandalf"));
		assertThat(savedEmployee.getRole(), is("wizard"));

		/**
		 * ---------------- Update the above newly created employee ----------------
		 */
		savedEmployee.setRole("the " + savedEmployee.getRole());
		// arrange - train your mock
		given(repository.findById(anyLong())).willReturn(Optional.of(employee));
		given(repository.save(any())).willReturn(savedEmployee);

		// act & assert
		Employee updatedEmployee = this.employeeService.createOrUpdateEmployee(savedEmployee, savedEmployee.getId());
		assertThat(savedEmployee.getId(), is(1L));
		assertThat(savedEmployee.getName(), is("Gandalf"));
		assertThat(savedEmployee.getRole(), is("the wizard"));

		// verify the methods were invoked as we have used a mock repository
		verify(repository, times(1)).findById(anyLong());
		verify(repository, times(2)).save(any());
	}
	
	@Test
	public void createOrUpdateEmployee_ShouldSaveEmployee_WhenEmployeeNotPresent() throws Exception {
		Employee employee = new Employee(1, "Gandalf", "wizard");

		// arrange - train your mock
		given(repository.findById(anyLong())).willReturn(Optional.empty());
		given(repository.save(any())).willReturn(employee);

		// act & assert
		Employee updatedEmployee = this.employeeService.createOrUpdateEmployee(employee, employee.getId());
		assertThat(updatedEmployee.getId(), is(1L));
		assertThat(updatedEmployee.getName(), is("Gandalf"));
		assertThat(updatedEmployee.getRole(), is("wizard"));

		// verify the methods were invoked as we have used a mock repository
		verify(repository, times(1)).findById(anyLong());
		verify(repository, times(1)).save(any());
	}

	@Test
	public void deleteEmployee_ShouldDeleteEmployee() throws Exception {
		/**
		 * -------------------- First insert an new employee --------------------
		 */
		Employee employee = new Employee(1, "Gandalf", "wizard");
		// arrange - train your mock
		given(repository.save(any())).willReturn(employee);

		// act & assert
		Employee savedEmployee = this.employeeService.createEmployee(employee);
		assertThat(savedEmployee.getId(), is(1L));
		assertThat(savedEmployee.getName(), is("Gandalf"));
		assertThat(savedEmployee.getRole(), is("wizard"));

		/**
		 * ----------------- getEmployeeById - should the newly inserted employee
		 * ----------------
		 */
		// arrange - train your mock
		given(repository.findById(anyLong())).willReturn(Optional.of(new Employee(1, "Gandalf", "wizard")));

		// act & assert
		Employee fetchedEmployee = this.employeeService.getEmployee(1L);
		assertThat(fetchedEmployee.getId(), is(1L));
		assertThat(fetchedEmployee.getName(), is("Gandalf"));
		assertThat(fetchedEmployee.getRole(), is("wizard"));

		/**
		 * -------------------- deleteById - should delete employee --------------------
		 */
		this.employeeService.deleteEmployee(1L);

		/**
		 * -------------------- getEmployeeById - this should throw
		 * EmployeeNotFoundException as it was just deleted --------------------
		 */
		// arrange - train your mock
		given(repository.findById(anyLong())).willReturn(Optional.empty());

		// act & assert
		// TODO:- need to use hamcrest but using Assertions of Junit 5 :(
		EmployeeNotFoundException exception = Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
			this.employeeService.getEmployee(1L);
		});
		Assertions.assertEquals("Could not find employee 1", exception.getMessage());

		// verify the methods were invoked as we have used a mock repository
		verify(repository, times(1)).save(any());
		verify(repository, times(2)).findById(anyLong());
	}

}
