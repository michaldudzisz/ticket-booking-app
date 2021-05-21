package com.mdudzisz.ticketbookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatRowId implements Serializable {

    private long roomId;
    private int rowNr;

}
