package unipr.fshmn.simora.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called ScheduleRepository
// CRUD refers Create, Read, Update, Delete

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    List<Schedule> findBySubject(String subject);

    List<Schedule> findBySubjectAndDepartment(String subject,String department);
}