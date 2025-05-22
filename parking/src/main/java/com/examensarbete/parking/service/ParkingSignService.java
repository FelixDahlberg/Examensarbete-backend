package com.examensarbete.parking.service;

import com.examensarbete.parking.model.ParkingSign;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface ParkingSignService {
    ParkingSign saveParkingSign(ParkingSign parkingSign);
    List<ParkingSign> getAllParkingSigns();

    ParkingSign getParkingSignById(int id);

    List<ParkingSign> searchParkingSigns(String keyword, Sort sort);
}
