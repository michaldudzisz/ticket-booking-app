package com.mdudzisz.ticketbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookedSeatId implements Serializable {

    @Column(name = "screening_id")
    @JsonIgnore
    private long screeningId;

    @Column(name = "row_nr")
    private int rowNr;

    @Column(name = "seat_nr")
    private int seatNr;

}
