package praksa.zadatak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import praksa.zadatak.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
