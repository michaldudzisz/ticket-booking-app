package com.mdudzisz.ticketbookingapp.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Multimap;
import com.mdudzisz.ticketbookingapp.model.Movie;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class ScreeningList {

    @JsonSerialize(converter = ScreeningListConverter.class)
    private Multimap<Movie, Timestamp> screenings;

    public static ScreeningList fromMultimap(Multimap<Movie, Timestamp> map) {
        ScreeningList list = new ScreeningList();
        list.setScreenings(map);
        return list;
    }

}
