package com.mdudzisz.ticketbookingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.mdudzisz.ticketbookingapp.model.Movie;
import com.mdudzisz.ticketbookingapp.service.BookingService;
import com.mdudzisz.ticketbookingapp.service.TimeInterval;
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
import java.util.List;
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
    private BookingService bookingService;

    public static Multimap<Movie, Timestamp> serviceResult;

    static {
        serviceResult = LinkedListMultimap.create();
        serviceResult.putAll(new Movie("La Vite E Bella", "A nice movie."),
                List.of(
                        new Timestamp(parseDateTime("2021-05-17T12:00:00Z").getTimeInMillis()),
                        new Timestamp(parseDateTime("2021-05-17T14:00:00Z").getTimeInMillis())
                ));
        serviceResult.put(new Movie("Gladiator", "A cool movie."),
                new Timestamp(parseDateTime("2021-05-17T14:00:00Z").getTimeInMillis()));
    }

    @Test
    public void listScreeningsTest_validTwoParamQuery() throws Exception {
        Map<String, String> query = Map.of(
                "from", "2021-05-17T12:00:00+02:00", "to", "2021-05-31T12:00:00+02:00"
        );

        given(bookingService.getScreeningsListing(fromQueryMap(query))).willReturn(serviceResult);

        MockHttpServletResponse controllerResponse = client.perform(
                get("/book/list-screenings?from=2021-05-17T12:00:00+02:00&to=2021-05-31T12:00:00+02:00"))
                .andReturn().getResponse();

        ScreeningList queryResult = ScreeningList.fromMultimap(serviceResult);

        ObjectMapper mapper = new ObjectMapper();
        String expectedBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryResult);

        assertEquals(expectedBody, controllerResponse.getContentAsString());
    }

    @Test
    public void listScreeningsTest_emptyQuery() throws Exception {

        given(bookingService.getScreeningsListing(any(TimeInterval.class))).willReturn(serviceResult);

        MockHttpServletResponse controllerResponse = client.perform(get("/book/list-screenings"))
                .andReturn().getResponse();

        ScreeningList queryResult = ScreeningList.fromMultimap(serviceResult);

        ObjectMapper mapper = new ObjectMapper();
        String expectedBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryResult);

        assertEquals(expectedBody, controllerResponse.getContentAsString());
    }

    @Test
    public void listScreeningsTest_invalidQueryTags_oneParam() throws Exception {

        MockHttpServletResponse controllerResponse = client
                .perform(get("/book/list-screenings?to=2021-05-31T12:00:00+02:00"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), controllerResponse.getStatus());
    }

    @Test
    public void listScreeningsTest_invalidQueryParams() throws Exception {

        MockHttpServletResponse controllerResponse = client.perform(
                get("/book/list-screenings?from=bad-value&to=2021-05-31T12:00:00+02:00"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), controllerResponse.getStatus());
    }

}
