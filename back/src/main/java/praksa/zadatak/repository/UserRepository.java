package praksa.zadatak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import praksa.zadatak.model.BaseUser;

public interface UserRepository extends JpaRepository<BaseUser, Long> {
}
