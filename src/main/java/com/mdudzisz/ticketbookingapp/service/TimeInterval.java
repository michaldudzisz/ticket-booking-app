package com.mdudzisz.ticketbookingapp.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.*;

import static javax.xml.bind.DatatypeConverter.parseDateTime;

@Getter
@EqualsAndHashCode
public class TimeInterval {

    private final static String QUERY_FROM_TAG = "from";
    private final static String QUERY_TO_TAG = "to";

    private final Timestamp from;
    private final Timestamp to;

    public static TimeInterval fromQueryMap(Map<String, String> queryMap) {
        TimeInterval interval;
        if (queryMap.isEmpty()) {
            interval = createIntervalWithinDayFromNow();
        } else {
            validateQueryTags(queryMap);
            try {
                interval = parseValidQueryParams(queryMap);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported query params.");
            }
        }
        return interval;
    }

    private TimeInterval(Timestamp start, Timestamp end) {
        if (start.getTime() <= end.getTime()) {
            from = start;
            to = end;
        } else {
            throw new IllegalArgumentException("End time in time interval cannot anticipate start time.");
        }
    }

    private static TimeInterval createIntervalWithinDayFromNow() {
        final int daysToAdd = 1;

        Calendar nowCalendar = new GregorianCalendar();
        Timestamp now = new Timestamp(nowCalendar.getTimeInMillis());

        nowCalendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        Timestamp dayFromNow = new Timestamp(nowCalendar.getTimeInMillis());

        return new TimeInterval(now, dayFromNow);
    }

    private static void validateQueryTags(Map<String, String> queryMap) {
        checkIfTwoTags(queryMap);
        CheckIfValidTagsNames(queryMap);
    }

    private static TimeInterval parseValidQueryParams(Map<String, String> queryMap) {
        Timestamp from = new Timestamp(parseDateTime(queryMap.get(QUERY_FROM_TAG)).getTimeInMillis());
        Timestamp to = new Timestamp(parseDateTime(queryMap.get(QUERY_TO_TAG)).getTimeInMillis());
        return new TimeInterval(from, to);
    }

    private static void checkIfTwoTags(Map<String, String> queryMap) {
        if (queryMap.size() != 2)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unsupported query string - should have 2 params.");
    }

    private static void CheckIfValidTagsNames(Map<String, String> queryMap) {
        Set<String> allowedTags = Set.of(QUERY_FROM_TAG, QUERY_TO_TAG);

        Optional<Map.Entry<String, String>> wrongQueryParamOpt = queryMap.entrySet().stream()
                .filter(queryParam -> !allowedTags.contains(queryParam.getKey()))
                .findAny();

        if (wrongQueryParamOpt.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported query tags.");
    }
}
