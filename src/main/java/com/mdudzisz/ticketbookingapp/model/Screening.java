package com.mdudzisz.ticketbookingapp.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int roomId;
    private Timestamp date;

    @OneToOne(fetch = FetchType.EAGER)
    private Movie movie = new Movie();

    public Screening() {
    }

    public Screening(int roomId, Timestamp date, Movie movie) {
        this.roomId = roomId;
        this.date = date;
        this.movie = movie;
    }
}
