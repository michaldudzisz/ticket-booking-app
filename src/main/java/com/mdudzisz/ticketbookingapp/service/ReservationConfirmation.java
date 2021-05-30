package com.mdudzisz.ticketbookingapp.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mdudzisz.ticketbookingapp.entity.Reservation;
import com.mdudzisz.ticketbookingapp.entity.Screening;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;


@Getter
@Setter
public class ReservationConfirmation extends Reservation {

    private BigDecimal amountToPay = new BigDecimal(BigInteger.ZERO);

    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp shouldBePaidUntil;

    protected ReservationConfirmation(Reservation reservation) {
        super(reservation.getId(),
                reservation.getFirstName(),
                reservation.getLastName(),
                reservation.getDone(),
                reservation.getScreening(),
                reservation.getBookedSeats(),
                reservation.getReservationTickets()
        );
    }

    public static ReservationConfirmation fromReservation(Reservation reservation, Screening screening) {
        ReservationConfirmation confirmation = new ReservationConfirmation(reservation);

        confirmation.setScreening(screening);

        confirmation.getReservationTickets().forEach(ticket ->
                confirmation.amountToPay = confirmation.amountToPay.add(ticket.getTicketType().getPrice()));

        confirmation.setShouldBePaidUntil(calcDateToPayUntil(screening));

        return confirmation;
    }

    private static Timestamp calcDateToPayUntil(Screening screening) {
        final int minutesToAddToScreeningTime = -5;

        Calendar screeningCalendar = new GregorianCalendar();
        screeningCalendar.setTimeInMillis(screening.getDate().getTime());

        screeningCalendar.add(Calendar.MINUTE, minutesToAddToScreeningTime);

        return new Timestamp(screeningCalendar.getTimeInMillis());
    }

}
