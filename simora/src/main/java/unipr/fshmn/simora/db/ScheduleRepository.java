package unipr.fshmn.simora.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called ScheduleRepository
// CRUD refers Create, Read, Update, Delete

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByRoomID(Long roomID);

    @Query(value = "SELECT s.ID,s.roomid,s.subject,s.userid,s.department,s.end_Date,s.start_date " +
                    "FROM (Schedule s INNER JOIN User u " +
                        "ON s.userID = u.id) INNER JOIN Room r ON s.roomid = r.id " +
                    "WHERE s.start_date LIKE ?1 and s.department LIKE ?2 " +
                    "AND r.room LIKE ?3 AND CONCAT(u.first_name,' ',u.last_name) LIKE ?4",nativeQuery = true)
    List<Schedule> gjej(String date,String department,String room, String professor);


}