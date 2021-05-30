package com.mdudzisz.ticketbookingapp.service;

import com.mdudzisz.ticketbookingapp.entity.*;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.*;

@Data
public class ReservationRequest {

    public static final int MIN_NAME_LENGTH = 3;
    public static final String SINGLE_DASHED_SURNAME = "^[A-ZĄĘĆŁŃÓŚŹŻ][a-ząęćłńóśźż]{2,}-[A-ZĄĘĆŁŃÓŚŹŻ][a-ząęćłńóśźż]{2,}$";
    public static final String ONE_WORD_NAME = "^[A-ZĄĘĆŁŃÓŚŹŻ][a-ząęćłńóśźż]{2,}$";

    private String firstName;

    private String lastName;

    private long screeningId;

    private List<BookedSeat> seats;

    private List<String> tickets;

    public void validate() {
        validateTicketAndSeatsSizes();
        validateNames();
    }

    public void fillData() {
        seats.forEach(seat -> seat.setScreeningId(screeningId));
    }

    private void validateTicketAndSeatsSizes() {
        if (seats.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "At least one seat should be booked.");

        if (seats.size() != tickets.size())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Number of seats does not correspond to number of tickets.");
    }

    private void validateNames() {
        validateFirstName();
        validateLastName();
    }

    private void validateFirstName() {
        if (!firstName.matches(ONE_WORD_NAME))
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "First name should have at least 3 characters starting with capital letter.");
    }

    private void validateLastName() {
        if (!lastName.matches(ONE_WORD_NAME) && !lastName.matches(SINGLE_DASHED_SURNAME))
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Last name should have at least 3 characters starting with capital letter " +
                            "or consist of two such parts separated with single dash.");
    }


    public Reservation parseReservation(List<TicketType> availableTicketTypes) {

        Reservation reservation = new Reservation();
        reservation.setFirstName(firstName);
        reservation.setLastName(lastName);
        reservation.setDone(new Timestamp(new Date().getTime()));
        reservation.setScreening(new Screening(screeningId));

        reservation.setBookedSeats(seats);

        reservation.setReservationTickets(parseReservationTicketTypes(availableTicketTypes));

        reservation.getBookedSeats().forEach(s -> s.setReservation(reservation));
        reservation.getReservationTickets().forEach(s -> s.setReservation(reservation));

        return reservation;
    }

    private List<ReservationTicket> parseReservationTicketTypes(List<TicketType> availableTicketTypes) {

        List<ReservationTicket> ticketsToBook = new LinkedList<>();

        for (String ticketName : tickets) {

            Optional<TicketType> foundOpt = availableTicketTypes.stream()
                    .filter(ticket -> ticketName.equals(ticket.getTypeName()))
                    .findAny();

            TicketType found = foundOpt.orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                            "No such ticket type."));

            ReservationTicket reservationTicket = new ReservationTicket();
            reservationTicket.setTicketType(found);
            ticketsToBook.add(reservationTicket);
        }

        return ticketsToBook;
    }

}


