package praksa.zadatak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import praksa.zadatak.model.WorkEntry;
import praksa.zadatak.model.WorkEntryId;

public interface WorkEntryRepository extends JpaRepository<WorkEntry, WorkEntryId> {
}
