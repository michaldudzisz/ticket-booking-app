package com.mdudzisz.ticketbookingapp.service;

import com.mdudzisz.ticketbookingapp.model.BookedSeat;
import com.mdudzisz.ticketbookingapp.model.RoomPlan;
import com.mdudzisz.ticketbookingapp.model.Screening;
import com.mdudzisz.ticketbookingapp.model.SeatRow;
import com.mdudzisz.ticketbookingapp.repository.BookedSeatsRepository;
import com.mdudzisz.ticketbookingapp.repository.RoomRepository;
import com.mdudzisz.ticketbookingapp.repository.ScreeningRepository;
import com.mdudzisz.ticketbookingapp.repository.SeatRowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SeatBookingService {

    @Autowired
    private SeatRowRepository seatRowRepository;

    @Autowired
    private BookedSeatsRepository bookedSeatsRepository;

    @Autowired
    private ScreeningRepository screeningRepository;
    
    public RoomPlan fetchRoomPlan(long screeningId) {

        Optional<Screening> screeningOptional = screeningRepository.findById(screeningId);

        Screening screening = screeningOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with given id.")
        );

        List<BookedSeat> bookedSeats = bookedSeatsRepository.findByScreeningId(screeningId);
        List<SeatRow> seatRows = seatRowRepository.findByRoomIdOrderByRowNrAsc(screening.getId());

        return new RoomPlan(seatRows, bookedSeats);
    }
}
