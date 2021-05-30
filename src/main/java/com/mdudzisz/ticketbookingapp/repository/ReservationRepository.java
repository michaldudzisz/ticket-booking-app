package com.mdudzisz.ticketbookingapp.repository;

import com.mdudzisz.ticketbookingapp.entity.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

}