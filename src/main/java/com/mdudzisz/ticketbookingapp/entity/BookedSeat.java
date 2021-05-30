package com.mdudzisz.ticketbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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

    @JsonIgnore
    @ManyToOne
    private Reservation reservation;

}
