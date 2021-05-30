package com.mdudzisz.ticketbookingapp.controller;

import com.mdudzisz.ticketbookingapp.service.RoomPlan;
import com.mdudzisz.ticketbookingapp.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(RequestController.class);

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
            logger.error("Exception log message", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/available-seats/{screeningId}", produces = {"application/JSON"})
    public ResponseEntity<Object> getAvailableSeats(@PathVariable("screeningId") long screeningIg) {
        try {
            RoomPlan roomPlan = seatBookingService.fetchRoomPlan(screeningIg);

            return new ResponseEntity<>(roomPlan.getSeatingSchema(), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Exception log message", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/make-reservation", consumes = {"application/JSON"}, produces = {"application/JSON"})
    public ResponseEntity<Object> makeReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            reservationRequest.validate();
            reservationRequest.fillData();

            ReservationConfirmation confirmation = seatBookingService.makeReservation(reservationRequest);

            return new ResponseEntity<>(confirmation, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Exception log message", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
