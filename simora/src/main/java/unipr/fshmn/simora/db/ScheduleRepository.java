package unipr.fshmn.simora.db;

import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called ScheduleRepository
// CRUD refers Create, Read, Update, Delete

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

}