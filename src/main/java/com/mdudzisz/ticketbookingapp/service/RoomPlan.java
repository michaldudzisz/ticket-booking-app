package com.mdudzisz.ticketbookingapp.service;

import com.mdudzisz.ticketbookingapp.entity.BookedSeat;
import com.mdudzisz.ticketbookingapp.entity.SeatRow;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RoomPlan {

    private Map<Integer, ArrayList<Boolean>> seatingSchema = new HashMap<>();

    public RoomPlan(List<SeatRow> seatRows, List<BookedSeat> bookedSeats) {

        for (SeatRow row : seatRows) {
            ArrayList<Boolean> freeRowSeats = new ArrayList<>(row.getLength());

            for (int i = 0; i < row.getLength(); i++) {
                freeRowSeats.add(true);
            }

            for (BookedSeat seat : bookedSeats) {
                if (seat.getRowNr() == row.getRowNr())
                    freeRowSeats.set(seat.getSeatNr() - 1, false); // seats are numbered from 1
            }

            seatingSchema.put(row.getRowNr(), freeRowSeats);
        }
    }

    public boolean seatsMayBeBooked(List<BookedSeat> seats) {
        if (!checkIfSeatsNotAlreadyBooked(seats)) return false;
        markRequestedSeats(seats);
        return checkIfNoSingleSeatsLefts();
    }

    private boolean checkIfSeatsNotAlreadyBooked(List<BookedSeat> seats) {
        for (BookedSeat seat : seats) {
            if (!seatingSchema.get(seat.getRowNr()).get(seat.getSeatNr() - 1))
                return false;
        }
        return true;
    }

    private void markRequestedSeats(List<BookedSeat> seats) {
        for (BookedSeat seat : seats)
            seatingSchema.get(seat.getRowNr()).set(seat.getSeatNr() - 1, false);
    }

    private boolean checkIfNoSingleSeatsLefts() {
        for (ArrayList<Boolean> row : new ArrayList<>(seatingSchema.values())) {
            for (int i = 0; i < row.size() - 2; i++) {
                if (row.get(i) && !row.get(i + 1) && row.get(i + 2))
                    return false;
            }
        }
        return true;
    }

}
