package com.mdudzisz.ticketbookingapp.repository;

import com.mdudzisz.ticketbookingapp.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

}

