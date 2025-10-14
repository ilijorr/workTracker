package praksa.zadatak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import praksa.zadatak.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
