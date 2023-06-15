package my.play.ground.springrestcrud.junits.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.play.ground.springrestcrud.controllers.EmployeeController;
import my.play.ground.springrestcrud.exceptions.EmployeeNotFoundException;
import my.play.ground.springrestcrud.models.Employee;
import my.play.ground.springrestcrud.services.EmployeeService;

/**
 * Use the tool https://www.jsonquerytool.com/sample/jsonpathlastinarray to test
 * the jsonPath
 */
// As of Spring Boot 2.1, we no longer need to load the SpringExtension because it's included as a meta annotation in the Spring Boot test annotations like @DataJpaTest, @WebMvcTest, and @SpringBootTest.
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Captor
	private ArgumentCaptor<Employee> employeeArgumentCaptor;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("GET /api/employees - Employees found")
	public void getAllEmployees_ShouldReturnHttp200_With2Employees_WhenEmployeesPresent() throws Exception {
		// arrange - train your mock
		given(employeeService.getAllEmployees()).willReturn(
				List.of(new Employee(1, "Bilbo Baggins", "burglar"), new Employee(2, "Frodo Baggins", "thief")));

		// act & assert
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned data
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("Bilbo Baggins"))).andExpect(jsonPath("$[0].role", is("burglar")))
				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].name", is("Frodo Baggins")))
				.andExpect(jsonPath("$[1].role", is("thief")));

		// verify that method was invoked as we have used a mock service
		verify(employeeService, times(1)).getAllEmployees();
	}

	@Test
	@DisplayName("GET /api/employees - No Employes found")
	public void getAllEmployees_ShouldReturnHttp200_EmptyList_WhenNoEmployeesPresent() throws Exception {
		// arrange - train your mock
		given(employeeService.getAllEmployees()).willReturn(Collections.EMPTY_LIST);

		// act & assert
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned data
				.andExpect(jsonPath("$", hasSize(0)));

		// verify that method was is invoked as we have used a mock service
		verify(employeeService, times(1)).getAllEmployees();
	}

	@Test
	@DisplayName("GET /api/employees/1 - Found")
	public void getEmployeeById_ShouldReturnHttp200_WithOneEmployee_WhenPresent() throws Exception {
		// arrange - train your mock
		given(employeeService.getEmployee(anyLong())).willReturn(new Employee(1, "Bilbo Baggins", "burglar"));

		// act & assert
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", 1))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned data
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Bilbo Baggins")))
				.andExpect(jsonPath("$.role", is("burglar")));

		// verify that method was invoked as we have used a mock service
		verify(employeeService, times(1)).getEmployee(anyLong());
	}

	@Test
	@DisplayName("GET /api/employees/1 - Not Found")
	public void getEmployeeById_ShouldReturnEmployeeNotFoundException_WhenEmptyNotPresent() throws Exception {
		// arrange - train your mock
		given(employeeService.getEmployee(anyLong())).willThrow(new EmployeeNotFoundException(anyLong()));

		// act & assert
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", 1))
				// Validate the response code
				.andExpect(status().isNotFound());

		// verify that method was invoked as we have used a mock service
		verify(employeeService, times(1)).getEmployee(anyLong());
	}

	@Test
	@DisplayName("POST /api/employees")
	public void createNewEmploye_ShouldReturnHttp200_NewEmployeeWithIdPopulated()
			throws JsonProcessingException, Exception {
		Employee employee = new Employee(1, "Gandalf", "wizard");

		// arrange - train your mock
		// given(employeeService.createEmployee(any())).willReturn(employee); // (Or)
		given(employeeService.createEmployee(employeeArgumentCaptor.capture())).willReturn(employee);

		// act & assert
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employees") // this is the url
				.contentType(MediaType.APPLICATION_JSON)
				// this is the first parameter to the method createEmployee in the controller
				.content(objectMapper.writeValueAsString(employee)))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned data
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Gandalf")))
				.andExpect(jsonPath("$.role", is("wizard")));

		assertThat(employeeArgumentCaptor.getValue().getName(), is("Gandalf"));
		assertThat(employeeArgumentCaptor.getValue().getRole(), is("wizard"));

		// verify that method was invoked as we have used a mock service
		verify(employeeService, times(1)).createEmployee(any());
	}

	@Test
	public void updateEmployeeWithKnownId_ShouldUpdateTheEmployee() throws Exception {
		/**
		 * -------------------- First insert an new employee --------------------
		 */
		Employee employee = new Employee(1, "Gimli", "dwarf");

		// arrange - train your mock
		given(employeeService.createEmployee(employeeArgumentCaptor.capture())).willReturn(employee);

		// act & assert
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employees") // this is the url
				.contentType(MediaType.APPLICATION_JSON)
				// this is the first parameter to the method createEmployee in the controller
				.content(objectMapper.writeValueAsString(employee)))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned data
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Gimli")))
				.andExpect(jsonPath("$.role", is("dwarf")));

		/**
		 * -------------------- Update the above newly created employee
		 * --------------------
		 */
		// arrange - train your mock
		// given(employeeService.createOrUpdateEmployee(employee, 1L)).willReturn(new
		// Employee(1, "Gimli", "the dwarf")); // (Or)
		given(employeeService.createOrUpdateEmployee(employeeArgumentCaptor.capture(), eq(1L)))
				.willReturn(new Employee(1, "Gimli", "the dwarf"));

		// act & assert
		employee.setRole("the " + employee.getRole());
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", 1) // this is the url in the controller
				.contentType(MediaType.APPLICATION_JSON)
				// this is the first parameter to the method updateEmployee in the controller
				.content(objectMapper.writeValueAsString(employee)))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned data
				.andExpect(jsonPath("$.name", is("Gimli"))).andExpect(jsonPath("$.role", is("the dwarf")))
				.andExpect(jsonPath("$.id", is(1)));

		assertThat(employeeArgumentCaptor.getValue().getName(), is("Gimli"));
		assertThat(employeeArgumentCaptor.getValue().getRole(), is("the dwarf"));

		// verify that methods were invoked as we have used a mock service
		verify(employeeService, times(1)).createEmployee(any());
		verify(employeeService, times(1)).createOrUpdateEmployee(any(), anyLong());

	}

	@Test
	public void deleteEmployeeWithKnownId_ShouldDeleteTheEmployee() throws JsonProcessingException, Exception {
		/**
		 * -------------------- First insert an new employee --------------------
		 */
		Employee employee = new Employee(1, "Gimli", "dwarf");

		// arrange - train your mock
		given(employeeService.createEmployee(employeeArgumentCaptor.capture())).willReturn(employee);

		// act & assert
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employees") // this is the url
				.contentType(MediaType.APPLICATION_JSON)
				// this is the first parameter to the method createEmployee in the controller
				.content(objectMapper.writeValueAsString(employee)))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned data
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Gimli")))
				.andExpect(jsonPath("$.role", is("dwarf")));

		/**
		 * -------------------- getEmployeeById - should the newly inserted employee
		 * --------------------
		 */
		// arrange - train your mock
		given(employeeService.getEmployee(anyLong())).willReturn(employee);

		// act & assert
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", 1))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned data
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Gimli")))
				.andExpect(jsonPath("$.role", is("dwarf")));

		/**
		 * -------------------- deleteById - should delete employee --------------------
		 */
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}", 1)).andExpect(status().isOk());

		/**
		 * -------------------- getEmployeeById - this should throw
		 * EmployeeNotFoundException as it was just deleted --------------------
		 */
		// arrange - train your mock
		given(employeeService.getEmployee(anyLong())).willThrow(new EmployeeNotFoundException(anyLong()));

		// act & assert
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", 1)).andExpect(status().isNotFound());

		// verify that methods were invoked as we have used a mock service
		verify(employeeService, times(1)).createEmployee(any());
		verify(employeeService, times(2)).getEmployee(anyLong());
		verify(employeeService, times(1)).deleteEmployee(anyLong());
	}
}
