package com.mdudzisz.ticketbookingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Multimap;
import com.mdudzisz.ticketbookingapp.model.Movie;
import com.mdudzisz.ticketbookingapp.model.Screening;
import com.mdudzisz.ticketbookingapp.service.BookingService;
import com.mdudzisz.ticketbookingapp.service.TimeInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Map;

import static com.mdudzisz.ticketbookingapp.service.TimeInterval.fromQueryMap;

@Controller
@RequestMapping("book")
public class RequestController {

    @Autowired
    private BookingService bookingService;

    @GetMapping(value = "/list-screenings", produces = {"application/JSON"})
    public ResponseEntity<String> listScreenings(@RequestParam Map<String, String> queryMap) {
        try {
            TimeInterval interval = fromQueryMap(queryMap);

            Multimap<Movie, Timestamp> screenings = bookingService.getScreeningsListing(interval);

            ObjectMapper mapper = new ObjectMapper();

            ScreeningList queryResult = ScreeningList.fromMultimap(screenings);

            String responseBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryResult);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
