package com.mdudzisz.ticketbookingapp.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatRowId implements Serializable {

    private long roomId;
    private int rowNr;

}
