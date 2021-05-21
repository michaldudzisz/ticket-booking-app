package com.mdudzisz.ticketbookingapp.service;

import com.mdudzisz.ticketbookingapp.model.Screening;
import com.mdudzisz.ticketbookingapp.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreeningsListingService {

    @Autowired
    ScreeningRepository screeningRepository;

    public SortedScreeningListing fetchScreeningsListing(TimeInterval interval) {

        List<Screening> queryResult = screeningRepository
                .findByDateGreaterThanEqualAndDateLessThanEqual(interval.getFrom(), interval.getTo());

        return SortedScreeningListing.of(queryResult);
    }

}
