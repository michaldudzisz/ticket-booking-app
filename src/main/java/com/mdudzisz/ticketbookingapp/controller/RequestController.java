package com.mdudzisz.ticketbookingapp.controller;

import com.mdudzisz.ticketbookingapp.service.BookingService;
import com.mdudzisz.ticketbookingapp.service.SortedScreeningListing;
import com.mdudzisz.ticketbookingapp.service.TimeInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static com.mdudzisz.ticketbookingapp.service.TimeInterval.fromQueryMap;

@RestController
@RequestMapping("book")
public class RequestController {

    @Autowired
    private BookingService bookingService;

    @GetMapping(value = "/list-screenings", produces = {"application/JSON"})
    public ResponseEntity<Object> listScreenings(@RequestParam Map<String, String> queryMap) {
        try {
            TimeInterval interval = fromQueryMap(queryMap);

            SortedScreeningListing sortedScreenings = bookingService.getScreeningsListing(interval);

            return new ResponseEntity<>(sortedScreenings.getAllScreenings(), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
