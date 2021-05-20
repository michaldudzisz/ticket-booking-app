package com.mdudzisz.ticketbookingapp.service;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TimeIntervalTest {

    @Test
    public void fromQueryMap_toGreaterFrom() {
        final String fromString = "2021-05-31T12:00:00";
        final String toString = "2021-05-31T13:00:00";

        final Timestamp from = new Timestamp(1622455200000L);
        final Timestamp to = new Timestamp(1622458800000L);

        Map<String, String> queryMap = Map.of("from", fromString, "to", toString);

        TimeInterval interval = TimeInterval.fromQueryMap(queryMap);

        assertEquals(from, interval.getFrom());
        assertEquals(to, interval.getTo());
    }

    @Test
    public void fromQueryMap_toEqualsFrom() {
        final String fromString = "2021-05-31T12:00:00";
        final String toString = "2021-05-31T12:00:00";

        final Timestamp from = new Timestamp(1622455200000L);
        final Timestamp to = new Timestamp(1622455200000L);

        Map<String, String> queryMap = Map.of("from", fromString, "to", toString);

        TimeInterval interval = TimeInterval.fromQueryMap(queryMap);

        assertEquals(from, interval.getFrom());
        assertEquals(to, interval.getTo());
    }

    @Test
    public void fromQueryMap_toLessThanFrom() {
        final String fromString = "2021-05-31T13:00:00";
        final String toString = "2021-05-31T12:00:00";

        Map<String, String> queryMap = Map.of("from", fromString, "to", toString);

        ResponseStatusException exc =
                assertThrows(ResponseStatusException.class, () -> TimeInterval.fromQueryMap(queryMap));
        assertEquals(HttpStatus.BAD_REQUEST.value(), exc.getRawStatusCode());
    }

    @Test
    public void fromQueryMap_invalidDateFormat() {
        final String fromString = "invalid-data";
        final String toString = "2021-05-31T12:00:00";

        Map<String, String> queryMap = Map.of("from", fromString, "to", toString);

        ResponseStatusException exc =
                assertThrows(ResponseStatusException.class, () -> TimeInterval.fromQueryMap(queryMap));
        assertEquals(HttpStatus.BAD_REQUEST.value(), exc.getRawStatusCode());
    }

    @Test
    public void fromQueryMap_invalidParamTag() {
        final String fromString = "invalid-data";
        final String toString = "2021-05-31T12:00:00";

        Map<String, String> queryMap = Map.of("from", fromString, "to", toString);

        ResponseStatusException exc =
                assertThrows(ResponseStatusException.class, () -> TimeInterval.fromQueryMap(queryMap));
        assertEquals(HttpStatus.BAD_REQUEST.value(), exc.getRawStatusCode());
    }

    @Test
    public void fromQueryMap_extraParam() {
        final String fromString = "invalid-data";
        final String toString = "2021-05-31T12:00:00";

        Map<String, String> queryMap = Map.of("from", fromString, "to", toString,
                "extra-param", "extra-value");

        ResponseStatusException exc =
                assertThrows(ResponseStatusException.class, () -> TimeInterval.fromQueryMap(queryMap));
        assertEquals(HttpStatus.BAD_REQUEST.value(), exc.getRawStatusCode());
    }

}
