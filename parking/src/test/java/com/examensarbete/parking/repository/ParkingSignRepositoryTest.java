package com.examensarbete.parking.repository;

import com.examensarbete.parking.model.ParkingSign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ParkingSignRepositoryTest {

    @Autowired
    private ParkingSignRepository repository;

    @Test
    public void testFindByNameOrDescription() {
        ParkingSign sign = new ParkingSign();
        sign.setParkingSignName("Test");
        sign.setDescription("Demo");
        repository.save(sign);

        List<ParkingSign> results = repository
                .findAllByParkingSignNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase("test", "demo", Sort.by("id"));

        assertFalse(results.isEmpty());
    }
}