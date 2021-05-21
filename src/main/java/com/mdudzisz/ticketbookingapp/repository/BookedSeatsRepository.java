package com.mdudzisz.ticketbookingapp.repository;

import com.mdudzisz.ticketbookingapp.model.BookedSeat;
import com.mdudzisz.ticketbookingapp.model.BookedSeatId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookedSeatsRepository extends CrudRepository<BookedSeat, BookedSeatId> {

    List<BookedSeat> findByScreeningId(long screeningId);
}
