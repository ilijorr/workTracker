package praksa.zadatak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import praksa.zadatak.model.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
}
