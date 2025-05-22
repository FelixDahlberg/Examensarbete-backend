package com.examensarbete.parking.repository;

import com.examensarbete.parking.model.ParkingSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import java.util.List;


@Repository
public interface ParkingSignRepository extends JpaRepository<ParkingSign,Integer> {
    List<ParkingSign> findAllByParkingSignNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String name, String desc, Sort sort);
}
