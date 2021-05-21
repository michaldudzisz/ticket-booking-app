package com.mdudzisz.ticketbookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookedSeatId implements Serializable {

    @Column(name = "screening_id")
    private long screeningId;

    @Column(name = "row_nr")
    private int rowNr;

    @Column(name = "seat_nr")
    private int seatNr;

}
