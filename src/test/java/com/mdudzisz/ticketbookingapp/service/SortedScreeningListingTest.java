package com.mdudzisz.ticketbookingapp.service;

import com.google.common.collect.SortedMultiset;
import com.mdudzisz.ticketbookingapp.model.Movie;
import com.mdudzisz.ticketbookingapp.model.Screening;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SortedScreeningListingTest {

    List<Movie> storedMovies;
    List<Screening> screeningRawList;

    @Before
    public void setUp() {
        storedMovies = List.of(
                new Movie("La Vita E Bella", "A nice movie."),
                new Movie("Gladiator", "A cool movie.")
        );

        screeningRawList = List.of(
                new Screening(1, new Timestamp(100), storedMovies.get(0)),
                new Screening(2, new Timestamp(200), storedMovies.get(1)),
                new Screening(3, new Timestamp(300), storedMovies.get(0))
        );
    }

    @Test
    public void of_validArgument() {
        SortedScreeningListing listing = SortedScreeningListing.of(screeningRawList);

        assertEquals(storedMovies.get(1), listing.getAllScreenings().firstEntry().getElement().getMovie(),
                "Movies should be sorted by titles.");

        assertEquals(storedMovies.get(0), listing.getAllScreenings().lastEntry().getElement().getMovie(),
                "Movies should be sorted by titles");

        SortedMultiset<ScreeningDate> twoScreeningMovieDates =
                listing.getAllScreenings().lastEntry().getElement().getMovieScreeningsDates();

        ScreeningDate firstScreeningDate = twoScreeningMovieDates.firstEntry().getElement();
        ScreeningDate secondScreeningDate = twoScreeningMovieDates.lastEntry().getElement();

        assertEquals(screeningRawList.get(0).getDate().getTime(), firstScreeningDate.getDate().getTime(),
                "Screenings should be sorted by time");

        assertEquals(screeningRawList.get(2).getDate().getTime(), secondScreeningDate.getDate().getTime(),
                "Screenings should be sorted by time");
    }

    @Test
    public void of_emptyArgument() {
        List<Screening> emptyList = new LinkedList<>();
        SortedScreeningListing listing = SortedScreeningListing.of(emptyList);

        assertTrue(listing.getAllScreenings().isEmpty(),
                "Created from empty list, should be also empty collection.");
    }

    @Test
    public void containsMovie_contains() {
        SortedScreeningListing listing = SortedScreeningListing.of(screeningRawList);
        assertTrue(listing.containsMovie(storedMovies.get(0)), "Should contain stored movies.");
        assertTrue(listing.containsMovie(storedMovies.get(1)), "Should contain stored movies.");
    }

    @Test
    public void containsMovie_doesNotContain() {
        SortedScreeningListing listing = SortedScreeningListing.of(screeningRawList);
        assertFalse(listing.containsMovie(new Movie("Batman", "A nice movie.")));
    }

    @Test
    public void containsMovie_emptyListing() {
        List<Screening> emptyList = new LinkedList<>();
        SortedScreeningListing listing = SortedScreeningListing.of(emptyList);
        assertFalse(listing.containsMovie(new Movie("Batman", "A nice movie.")));
    }

    @Test
    public void addScreening_toEmptyListing() {
        List<Screening> emptyList = new LinkedList<>();
        SortedScreeningListing listing = SortedScreeningListing.of(emptyList);

        listing.addScreening(screeningRawList.get(0));
        assertTrue(listing.containsMovie(storedMovies.get(0)));

        assertEquals(listing.getAllScreenings().firstEntry(), listing.getAllScreenings().lastEntry(),
                "Movie with dates list should contain only one entry.");

        SortedMultiset<ScreeningDate> movieScreeningsDates = listing.getAllScreenings()
                .firstEntry().getElement().getMovieScreeningsDates();

        assertEquals(movieScreeningsDates.firstEntry(), movieScreeningsDates.lastEntry(),
                "Date list should contain only one entry.");

        Timestamp addedTime = movieScreeningsDates.firstEntry().getElement().getDate();
        long addedId = movieScreeningsDates.firstEntry().getElement().getId();

        assertEquals(screeningRawList.get(0).getDate(), addedTime);
        assertEquals(screeningRawList.get(0).getId(), addedId);
    }

}
