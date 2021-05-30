package com.mdudzisz.ticketbookingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdudzisz.ticketbookingapp.entity.Movie;
import com.mdudzisz.ticketbookingapp.entity.Screening;
import com.mdudzisz.ticketbookingapp.service.ScreeningsListingService;
import com.mdudzisz.ticketbookingapp.service.SeatBookingService;
import com.mdudzisz.ticketbookingapp.service.SortedScreeningListing;
import com.mdudzisz.ticketbookingapp.service.TimeInterval;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static com.mdudzisz.ticketbookingapp.service.TimeInterval.fromQueryMap;
import static javax.xml.bind.DatatypeConverter.parseDateTime;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RequestController.class)
public class RequestControllerIntegrationTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private ScreeningsListingService screeningsListingService;

    @MockBean
    private SeatBookingService seatBookingService;

    private SortedScreeningListing serviceResult = new SortedScreeningListing();

    @Before
    public void setUp() {

        serviceResult.addScreening(new Screening(1, 1,
                new Timestamp(parseDateTime("2121-05-17T12:00:00").getTimeInMillis()),
                new Movie(1, "La Vita E Belle", "A nice movie.")));

        serviceResult.addScreening(new Screening(1, 1,
                new Timestamp(parseDateTime("2121-05-17T14:00:00").getTimeInMillis()),
                new Movie(1, "La Vita E Belle", "A nice movie.")));

        serviceResult.addScreening(new Screening(1, 1,
                new Timestamp(parseDateTime("2121-05-17T14:00:00").getTimeInMillis()),
                new Movie(2, "Gladiator", "A cool movie.")));
    }

    @Test
    public void listScreeningsTest_validTwoParamQuery() throws Exception {

        final String fromString = "2121-05-17T12:00:00";
        final String toString = "2121-05-31T12:00:00";

        Map<String, String> query = Map.of("from", fromString, "to", toString);

        given(screeningsListingService.fetchScreeningsListing(fromQueryMap(query))).willReturn(serviceResult);

        MockHttpServletResponse controllerResponse = client.perform(
                get("/book/list-screenings?from=" + fromString + "&to=" + toString))
                .andReturn().getResponse();

        String expectedBody = new ObjectMapper().writeValueAsString(serviceResult.getAllScreenings());
        assertEquals(expectedBody, controllerResponse.getContentAsString());
    }

    @Test
    public void listScreeningsTest_emptyQuery() throws Exception {

        given(screeningsListingService.fetchScreeningsListing(any(TimeInterval.class))).willReturn(serviceResult);

        MockHttpServletResponse controllerResponse = client.perform(get("/book/list-screenings"))
                .andReturn().getResponse();

        String expectedBody = new ObjectMapper().writeValueAsString(serviceResult.getAllScreenings());
        assertEquals(expectedBody, controllerResponse.getContentAsString());
    }

    @Test
    public void listScreeningsTest_invalidQueryTags_oneParam() throws Exception {

        MockHttpServletResponse controllerResponse = client
                .perform(get("/book/list-screenings?to=2021-05-31T12:00:00"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), controllerResponse.getStatus());
    }

    @Test
    public void listScreeningsTest_invalidQueryParams() throws Exception {

        MockHttpServletResponse controllerResponse = client.perform(
                get("/book/list-screenings?from=bad-value&to=2021-05-31T12:00:00"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), controllerResponse.getStatus());
    }
}
