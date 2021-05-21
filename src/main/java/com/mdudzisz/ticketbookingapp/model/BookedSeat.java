package com.mdudzisz.ticketbookingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BookedSeatId.class)
@Entity(name = "booked_seats")
public class BookedSeat {

    @Id
    @Column(name = "screening_id")
    @JsonIgnore
    private long screeningId;

    @Id
    @Column(name = "row_nr")
    private int rowNr;

    @Id
    @Column(name = "seat_nr")
    private int seatNr;

    @Column(name = "reservation_id")
    @JsonIgnore
    private int reservationId;

}
