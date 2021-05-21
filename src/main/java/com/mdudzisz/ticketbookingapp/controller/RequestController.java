package com.mdudzisz.ticketbookingapp.controller;

import com.mdudzisz.ticketbookingapp.model.RoomPlan;
import com.mdudzisz.ticketbookingapp.service.ScreeningsListingService;
import com.mdudzisz.ticketbookingapp.service.SeatBookingService;
import com.mdudzisz.ticketbookingapp.service.SortedScreeningListing;
import com.mdudzisz.ticketbookingapp.service.TimeInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static com.mdudzisz.ticketbookingapp.service.TimeInterval.fromQueryMap;

@RestController
@RequestMapping("book")
public class RequestController {

    @Autowired
    private ScreeningsListingService screeningsListingService;

    @Autowired
    private SeatBookingService seatBookingService;

    @GetMapping(value = "/list-screenings", produces = {"application/JSON"})
    public ResponseEntity<Object> listScreenings(@RequestParam Map<String, String> queryMap) {
        try {
            TimeInterval interval = fromQueryMap(queryMap);

            SortedScreeningListing sortedScreenings = screeningsListingService.fetchScreeningsListing(interval);

            return new ResponseEntity<>(sortedScreenings.getAllScreenings(), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/available-seats/{screeningId}", produces = {"application/JSON"})
    public ResponseEntity<Object> getAvailableSeats(@PathVariable("screeningId") long screeningIg) {
        try {
            RoomPlan roomPlan = seatBookingService.fetchRoomPlan(screeningIg);

            return new ResponseEntity<>(roomPlan.getFreeSeats(), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
