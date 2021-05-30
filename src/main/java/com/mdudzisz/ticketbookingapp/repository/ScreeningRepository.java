package com.mdudzisz.ticketbookingapp.repository;

import com.mdudzisz.ticketbookingapp.entity.Screening;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ScreeningRepository extends CrudRepository<Screening, Long> {

    List<Screening> findByDateGreaterThanEqualAndDateLessThanEqual(Timestamp start, Timestamp end);

}
