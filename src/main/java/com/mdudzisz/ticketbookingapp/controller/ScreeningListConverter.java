package com.mdudzisz.ticketbookingapp.controller;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.google.common.collect.Multimap;
import com.mdudzisz.ticketbookingapp.model.Movie;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScreeningListConverter extends StdConverter<Multimap<Movie, Timestamp>, List<Map<String, Object>>> {

    public static final String MOVIE_TAG = "movie";
    public static final String DATES_TAG = "dates";

    public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    @Override
    public List<Map<String, Object>> convert(Multimap<Movie, Timestamp> multimap) {
        List<Map<String, Object>> list = new LinkedList<>();

        multimap.asMap().forEach((movie, dates) -> {

            Map<String, Object> entry = new HashMap<>();
            entry.put(MOVIE_TAG, movie);

            List<Timestamp> timestamps = parseTimestampList(dates);
            List<String> dateStrings = parseFormattedDateStrings(timestamps);
            entry.put(DATES_TAG, dateStrings);

            list.add(entry);
        });

        return list;
    }

    private List<Timestamp> parseTimestampList(Collection<Timestamp> collection) {
        List<Timestamp> timestamps;

        if (collection instanceof List) {
            timestamps = (List<Timestamp>) collection;
        } else {
            timestamps = new LinkedList<>(collection);
        }

        return timestamps;
    }

    private List<String> parseFormattedDateStrings(List<Timestamp> timestamps) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_8601_FORMAT);
        List<String> dateStrings = new LinkedList<>();

        for (Timestamp timestamp : timestamps) {
            calendar.setTimeInMillis(timestamp.getTime());
            dateStrings.add(dateFormat.format(calendar.getTime()));
        }

        return dateStrings;
    }

}
