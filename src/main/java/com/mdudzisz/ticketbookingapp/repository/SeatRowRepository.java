package com.mdudzisz.ticketbookingapp.repository;

import com.mdudzisz.ticketbookingapp.model.SeatRow;
import com.mdudzisz.ticketbookingapp.model.SeatRowId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRowRepository extends CrudRepository<SeatRow, SeatRowId> {

    List<SeatRow> findByRoomIdOrderByRowNrAsc(long roomId);

}

