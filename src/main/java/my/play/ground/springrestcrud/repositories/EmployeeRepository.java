package my.play.ground.springrestcrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.play.ground.springrestcrud.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}