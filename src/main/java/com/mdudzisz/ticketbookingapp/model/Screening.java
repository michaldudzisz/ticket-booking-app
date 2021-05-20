package com.mdudzisz.ticketbookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "screenings")
@NoArgsConstructor
@AllArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int roomId;
    private Timestamp date;

    @OneToOne(fetch = FetchType.EAGER)
    private Movie movie;

    public Screening(int roomId, Timestamp date, Movie movie) {
        this.roomId = roomId;
        this.date = date;
        this.movie = movie;
    }
}
