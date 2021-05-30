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

    private final static int MINUTES_BEFORE_CLOSEST_SCREENING = 15;

    private final Timestamp from;
    private final Timestamp to;

    public static TimeInterval fromQueryMap(Map<String, String> queryMap) {
        TimeInterval interval;
        if (queryMap.isEmpty()) {
            interval = createIntervalWithinDayFromNow();
        } else {
            validateQueryTags(queryMap);
            try {
                interval = parseQueryWithValidTags(queryMap);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported query params.");
            }
        }
        return interval;
    }

    public TimeInterval(Timestamp start, Timestamp end) {
        from = start;
        to = end;
    }

    private static TimeInterval createIntervalWithinDayFromNow() {
        final int daysToAdd = 1;

        Calendar nowCalendar = new GregorianCalendar();
        nowCalendar.add(Calendar.MINUTE, MINUTES_BEFORE_CLOSEST_SCREENING);
        Timestamp now = new Timestamp(nowCalendar.getTimeInMillis());

        nowCalendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        Timestamp dayFromNow = new Timestamp(nowCalendar.getTimeInMillis());

        return new TimeInterval(now, dayFromNow);
    }

    private static void validateQueryTags(Map<String, String> queryMap) {
        checkIfTwoTags(queryMap);
        checkIfValidTagsNames(queryMap);
    }

    private static TimeInterval parseQueryWithValidTags(Map<String, String> queryMap) {
        Timestamp from = new Timestamp(parseDateTime(queryMap.get(QUERY_FROM_TAG)).getTimeInMillis());
        Timestamp to = new Timestamp(parseDateTime(queryMap.get(QUERY_TO_TAG)).getTimeInMillis());

        checkIfToGreaterThanFrom(from, to);

        from = ensureThatFromIsAtLeast15MinsFromNow(from);

        return new TimeInterval(from, to);
    }

    private static void checkIfTwoTags(Map<String, String> queryMap) {
        if (queryMap.size() != 2)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unsupported query string - should have 2 params.");
    }

    private static void checkIfValidTagsNames(Map<String, String> queryMap) {
        Set<String> allowedTags = Set.of(QUERY_FROM_TAG, QUERY_TO_TAG);

        Optional<Map.Entry<String, String>> wrongQueryParamOpt = queryMap.entrySet().stream()
                .filter(queryParam -> !allowedTags.contains(queryParam.getKey()))
                .findAny();

        if (wrongQueryParamOpt.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported query tags.");
    }

    private static void checkIfToGreaterThanFrom(Timestamp from, Timestamp to) {
        if (from.getTime() > to.getTime())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Screenings time interval should be a valid time interval.");
    }

    private static Timestamp ensureThatFromIsAtLeast15MinsFromNow(Timestamp from) {
        Calendar firstAvailableFromCalendar = new GregorianCalendar();
        firstAvailableFromCalendar.add(Calendar.MINUTE, MINUTES_BEFORE_CLOSEST_SCREENING);

        if (from.getTime() < firstAvailableFromCalendar.getTimeInMillis())
            return new Timestamp(firstAvailableFromCalendar.getTimeInMillis());
        else
            return from;
    }
}
