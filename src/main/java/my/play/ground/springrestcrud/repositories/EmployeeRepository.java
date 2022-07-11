package my.play.ground.springrestcrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import my.play.ground.springrestcrud.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}