package unipr.fshmn.simora.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called ScheduleRepository
// CRUD refers Create, Read, Update, Delete

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    //List<Schedule> findByStartDateLike(String date);
    //List<Schedule> findByDepartment(String department);
    //List<Schedule> findByRoomID(Long roomID);
    //List<Schedule> findByUserID(Long userID);

    List<Schedule> findByStartDateLikeAndDepartmentLike(String date,String department);
    //List<Schedule> findByStartDateContainingAndRoomID(String date,Long roomID);
    //List<Schedule> findByStartDateContainingAndUserID(String date,Long userID);

    //List<Schedule> findByDepartmentAndRoomID(String department,Long roomID);
    //List<Schedule> findByDepartmentAndUserID(String department,Long userID);

    //List<Schedule> findByRoomIDAndUserID(Long roomID,Long userID);

    List<Schedule> findByStartDateContainingAndDepartmentAndRoomID(String date,String department,Long roomID);
    List<Schedule> findByStartDateContainingAndDepartmentAndUserID(String date,String department,Long userID);
    //List<Schedule> findByStartDateContainingAndRoomIDAndUserID(String date,Long userID,Long roomID);
    //List<Schedule> findByDepartmentAndRoomIDAndUserID(String department,Long userID,Long roomID);

    List<Schedule> findByStartDateContainingAndDepartmentContainingAndRoomIDLikeAndUserIDLike(String date,String department,Long roomID,Long userID);
    //@Query(value = "SELECT ID,roomid,subject,userid,department,end_Date,start_date FROM Schedule u WHERE u.start_date LIKE ?1 and u.department LIKE ?2",nativeQuery = true)
    //List<Schedule> gjej(String date,String department);

    @Query(value = "SELECT s.ID,s.roomid,s.subject,s.userid,s.department,s.end_Date,s.start_date " +
                    "FROM (Schedule s INNER JOIN User u " +
                        "ON s.userID = u.id) INNER JOIN Room r ON s.roomid = r.id " +
                    "WHERE s.start_date LIKE ?1 and s.department LIKE ?2 " +
                    "AND r.room LIKE ?3 AND CONCAT(u.first_name,' ',u.last_name) LIKE ?4",nativeQuery = true)
    List<Schedule> gjej(String date,String department,String room, String professor);


}