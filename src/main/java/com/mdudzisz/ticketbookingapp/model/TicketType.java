package com.mdudzisz.ticketbookingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ticket_types")
public class TicketType {

    @Id
    @Column(name = "id")
    @JsonIgnore
    private long id;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "price")
    private BigDecimal price;

    @JsonIgnore
    private int valid;

}
