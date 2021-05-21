package com.mdudzisz.ticketbookingapp.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RoomPlan {

    private Map<Integer, ArrayList<Boolean>> freeSeats = new HashMap<>();

    public RoomPlan(List<SeatRow> seatRows, List<BookedSeat> bookedSeats) {

        for (SeatRow row : seatRows) {
            ArrayList<Boolean> freeRowSeats = new ArrayList<>(row.getLength());

            for (int i = 0; i < row.getLength(); i++) {
                freeRowSeats.add(true);
            }

            for (BookedSeat seat : bookedSeats) {
                if (seat.getRowNr() == row.getRowNr())
                    freeRowSeats.set(seat.getSeatNr() - 1, false); // seats are numbered from 1, not 0
            }

            freeSeats.put(row.getRowNr(), freeRowSeats);
        }
    }

}
