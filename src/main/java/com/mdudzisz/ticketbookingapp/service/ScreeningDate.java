package com.mdudzisz.ticketbookingapp.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mdudzisz.ticketbookingapp.entity.Screening;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScreeningDate implements Comparable<ScreeningDate> {

    private long id;

    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp date;

    public static ScreeningDate fromScreening(Screening screening) {
        return new ScreeningDate(screening.getId(), screening.getDate());
    }

    @Override
    public int compareTo(ScreeningDate other) {
        long ownDate = this.getDate().getTime();
        long otherDate = other.getDate().getTime();

        return Long.compare(ownDate, otherDate);
    }
}
