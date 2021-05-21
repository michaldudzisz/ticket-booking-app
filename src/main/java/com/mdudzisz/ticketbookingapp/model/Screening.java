package com.mdudzisz.ticketbookingapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity(name = "screenings")
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int roomId;
    private Timestamp date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    @OneToMany(fetch = FetchType.LAZY)
    private List<BookedSeat> bookedSeats;

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
