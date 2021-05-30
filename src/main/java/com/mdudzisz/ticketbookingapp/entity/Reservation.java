package com.mdudzisz.ticketbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mdudzisz.ticketbookingapp.service.TimestampSerializer;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    protected long id;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "done")
    @JsonSerialize(using = TimestampSerializer.class)
    protected Timestamp done;

    @ManyToOne
    protected Screening screening;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "reservation")
    protected List<BookedSeat> bookedSeats;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "reservation")
    protected List<ReservationTicket> reservationTickets;

}
