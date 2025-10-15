package praksa.zadatak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import praksa.zadatak.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
