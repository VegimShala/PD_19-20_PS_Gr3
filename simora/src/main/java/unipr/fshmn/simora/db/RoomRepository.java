package unipr.fshmn.simora.db;

import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called RoomRepository
// CRUD refers Create, Read, Update, Delete

public interface RoomRepository extends CrudRepository<Room, Integer> {

}