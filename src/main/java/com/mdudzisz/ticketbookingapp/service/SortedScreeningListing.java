package com.mdudzisz.ticketbookingapp.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import com.mdudzisz.ticketbookingapp.model.Movie;
import com.mdudzisz.ticketbookingapp.model.Screening;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;


@NoArgsConstructor
@Getter
@Setter
public class SortedScreeningListing {

    @Data
    public static class MovieEntry implements Comparable<MovieEntry> {

        private Movie movie;

        @JsonProperty("movieScreenings")
        private SortedMultiset<ScreeningDate> movieScreeningsDates;

        @Override
        public int compareTo(MovieEntry other) {
            final String ownTitle = this.getMovie().getTitle();
            final String otherTitle = other.getMovie().getTitle();

            return ownTitle.compareTo(otherTitle);
        }
    }

    private TreeMultiset<MovieEntry> allScreenings = TreeMultiset.create();

    public static SortedScreeningListing of(List<Screening> screenings) {
        SortedScreeningListing created = new SortedScreeningListing();
        screenings.forEach(created::addScreening);
        return created;
    }

    public void addScreening(Screening screening) {
        if (containsMovie(screening.getMovie())) {
            addScreeningToExistingEntry(screening);
        } else {
            addScreeningToNewEntry(screening);
        }
    }

    public boolean containsMovie(Movie movie) {
        return allScreenings.stream().anyMatch(entry -> entry.getMovie().equals(movie));
    }

    private void addScreeningToExistingEntry(Screening screening) {
        Optional<MovieEntry> movieEntryOptional = allScreenings.stream()
                .filter(entry -> entry.getMovie().equals(screening.getMovie()))
                .findAny();

        MovieEntry found = movieEntryOptional.orElseThrow();

        Multiset<ScreeningDate> movieScreeningDates = found.getMovieScreeningsDates();

        movieScreeningDates.add(ScreeningDate.fromScreening(screening));
    }

    private void addScreeningToNewEntry(Screening screening) {
        MovieEntry entryToBeAdded = new MovieEntry();
        entryToBeAdded.setMovie(screening.getMovie());

        SortedMultiset<ScreeningDate> ScreeningDates = TreeMultiset.create();
        ScreeningDates.add(ScreeningDate.fromScreening(screening));
        entryToBeAdded.setMovieScreeningsDates(ScreeningDates);

        allScreenings.add(entryToBeAdded);
    }

}
