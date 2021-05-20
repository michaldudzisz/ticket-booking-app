package com.mdudzisz.ticketbookingapp.repository;

import com.mdudzisz.ticketbookingapp.model.Movie;
import com.mdudzisz.ticketbookingapp.model.Screening;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ScreeningRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Test
    public void findByDateGreaterThanEqualAndDateLessThanEqualTest() {
        List<Movie> storedMovies = List.of(
                new Movie("Gladiator", "A cool movie."),
                new Movie("La Vita E Bella", "A nice movie.")
        );
        storedMovies.forEach(entityManager::persistAndFlush);

        List<Screening> storedScreenings = List.of(
                new Screening(1, new Timestamp(100), storedMovies.get(0)),
                new Screening(2, new Timestamp(200), storedMovies.get(0)),
                new Screening(2, new Timestamp(300), storedMovies.get(1))
        );
        storedScreenings.forEach(entityManager::persistAndFlush);

        List<Screening> found = screeningRepository
                .findByDateGreaterThanEqualAndDateLessThanEqual(new Timestamp(100), new Timestamp(200));

        assertTrue(found.contains(storedScreenings.get(0)), "Should find specified element.");
        assertTrue(found.contains(storedScreenings.get(1)), "Should find specified element.");
        assertFalse(found.contains(storedScreenings.get(2)), "Should not find this element.");

        assertTrue(found.removeAll(storedScreenings), "Should find and remove properly stored elements.");
        assertTrue(found.isEmpty(), "Should not store any other elements than created earlier.");
    }

}
