package com.mdudzisz.ticketbookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rooms")
public class Room {

    @Id
    @Column(name = "id")
    private int number;

    @OneToMany(fetch = FetchType.EAGER)
    private List<SeatRow> rows;
}
