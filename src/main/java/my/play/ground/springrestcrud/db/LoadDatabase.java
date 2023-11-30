package my.play.ground.springrestcrud.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import my.play.ground.springrestcrud.models.Employee;
import my.play.ground.springrestcrud.repositories.EmployeeRepository;

/**
 * Instead of this class, you can also just create a 
 * 			schema.sql and 
 * 			data.sql and 
 *  place those files in the resources folder - which is a much cleaner way to load the db. 
 */
@Deprecated
//@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

//  @Bean
  CommandLineRunner initDatabase(EmployeeRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Employee(1, "Bilbo Baggins", "burglar")));
      log.info("Preloading " + repository.save(new Employee(2, "Frodo Baggins", "thief")));
    };
  }
}