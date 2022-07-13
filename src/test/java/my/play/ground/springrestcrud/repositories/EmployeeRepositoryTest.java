package my.play.ground.springrestcrud.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import my.play.ground.springrestcrud.models.Employee;

/**
 * These tests are not required as there are no methods in our
 * EmployeeRepository. Adding these tests to be used as a reference just in case
 * you have some methods in the EmployeeRepository exposed.
 */
//@ExtendWith(SpringExtension.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findAll_ShouldReturnListWith2Employees_When2EmployeesPresent() {
		// arrange - store the data in the db testEntityManager
		// NOTE: Id property should not be populated otherwise
		// persistFlushFind throws 'detached entity passed to persist'
		Employee employee1 = new Employee("Bilbo Baggins", "burglar");
		Employee employee2 = new Employee("Frodo Baggins", "thief");
		testEntityManager.persistFlushFind(employee1);
		testEntityManager.persistFlushFind(employee2);

		// act & assert
		List<Employee> employees = employeeRepository.findAll();
		assertThat(employees.size(), is(2));
		// First Employee
		assertThat(employees.get(0).getId(), is(1L));
		assertThat(employees.get(0).getName(), is("Bilbo Baggins"));
		assertThat(employees.get(0).getRole(), is("burglar"));
		// Second Employee
		assertThat(employees.get(1).getId(), is(2L));
		assertThat(employees.get(1).getName(), is("Frodo Baggins"));
		assertThat(employees.get(1).getRole(), is("thief"));

		// clean up the db
		testEntityManager.remove(employees.get(0));
		testEntityManager.remove(employees.get(1));

		// verify
	}
}
