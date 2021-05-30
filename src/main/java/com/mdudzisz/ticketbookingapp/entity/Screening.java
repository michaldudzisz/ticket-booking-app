package com.mdudzisz.ticketbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mdudzisz.ticketbookingapp.service.TimestampSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity(name = "screenings")
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int roomId;

    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<BookedSeat> bookedSeats;

    public Screening(long id) {
        this.id = id;
    }

    public Screening(int roomId, Timestamp date, Movie movie) {
        this.roomId = roomId;
        this.date = date;
        this.movie = movie;
    }

    public Screening(long id, int roomId, Timestamp date, Movie movie) {
        this.id = id;
        this.roomId = roomId;
        this.date = date;
        this.movie = movie;
    }
}
