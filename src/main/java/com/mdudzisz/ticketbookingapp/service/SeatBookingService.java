package com.mdudzisz.ticketbookingapp.service;

import com.mdudzisz.ticketbookingapp.entity.*;
import com.mdudzisz.ticketbookingapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.GregorianCalendar;
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

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    private static List<TicketType> availableTicketTypes;

    public static List<TicketType> getAvailableTicketTypes() {
        return availableTicketTypes;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fetchAvailableTicketTypes() {
        availableTicketTypes = ticketTypeRepository.findByValid(TicketTypeRepository.VALID);
    }
    
    public RoomPlan fetchRoomPlan(long screeningId) {

        Optional<Screening> screeningOptional = screeningRepository.findById(screeningId);

        Screening screening = screeningOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with given id.")
        );

        List<BookedSeat> bookedSeats = bookedSeatsRepository.findByScreeningId(screeningId);
        List<SeatRow> seatRows = seatRowRepository.findByRoomIdOrderByRowNrAsc(screening.getRoomId());

        return new RoomPlan(seatRows, bookedSeats);
    }

    public ReservationConfirmation makeReservation(ReservationRequest reservationRequest) {

        checkIfSeatsMayBeBooked(reservationRequest);

        Reservation reservation = reservationRequest.parseReservation(availableTicketTypes);

        Screening screening = screeningRepository.findById(reservation.getScreening().getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with given id."));

        checkIfScreeningInAtLeast15Minutes(screening);

        reservationRepository.save(reservation);

        return ReservationConfirmation.fromReservation(reservation, screening);
    }

    private void checkIfScreeningInAtLeast15Minutes(Screening screening) {
        Calendar nowCalendar = new GregorianCalendar();
        nowCalendar.add(Calendar.MINUTE, 15);

        if (screening.getDate().getTime() < nowCalendar.getTimeInMillis())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Screening should be booked at least 15 minutes before.");
    }

    private void checkIfSeatsMayBeBooked(ReservationRequest reservationRequest) {
        RoomPlan roomPlan = fetchRoomPlan(reservationRequest.getScreeningId());
        if (!roomPlan.seatsMayBeBooked(reservationRequest.getSeats()))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Given seats cannot be booked - are already reserved or there is single seat left between reserved.");
    }

}
