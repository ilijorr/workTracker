package praksa.zadatak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import praksa.zadatak.model.BaseUser;

public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
    BaseUser findByUsername(String username);
    Boolean existsByUsername(String username);
}
