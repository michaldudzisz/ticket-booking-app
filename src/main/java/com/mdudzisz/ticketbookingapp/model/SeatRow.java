package com.mdudzisz.ticketbookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SeatRowId.class)
@Entity(name = "seat_rows")
public class SeatRow {

    @Id
    @Column(name = "room_id")
    private long roomId;

    @Id
    @Column(name = "row_nr")
    private int rowNr;

    @Column(name = "row_length")
    private int length;
/*
    @OneToMany(fetch = FetchType.EAGER)
    private List<BookedSeat> bookedSeats;
*/
}
