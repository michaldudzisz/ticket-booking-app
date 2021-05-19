package com.mdudzisz.ticketbookingapp.service;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.mdudzisz.ticketbookingapp.model.Movie;
import com.mdudzisz.ticketbookingapp.model.Screening;
import com.mdudzisz.ticketbookingapp.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    ScreeningRepository screeningRepository;

    public Multimap<Movie, Timestamp> getScreeningsListing(TimeInterval interval) {

        List<Screening> queryResult = screeningRepository
                .findByDateGreaterThanEqualAndDateLessThanEqual(interval.getFrom(), interval.getTo());

        Multimap<Movie, Timestamp> screenings = LinkedListMultimap.create();
        queryResult.forEach(el -> screenings.put(el.getMovie(), el.getDate()));

        return screenings;
    }

}
