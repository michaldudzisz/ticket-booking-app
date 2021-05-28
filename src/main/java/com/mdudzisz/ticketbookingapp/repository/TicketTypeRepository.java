package com.mdudzisz.ticketbookingapp.repository;

import com.mdudzisz.ticketbookingapp.model.TicketType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TicketTypeRepository extends CrudRepository<TicketType, Long> {

    int NON_VALID = 0;
    int VALID = 1;

    List<TicketType> findByValid(int valid);
}
