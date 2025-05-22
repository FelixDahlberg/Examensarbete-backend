package com.examensarbete.parking.service;

import com.examensarbete.parking.model.ParkingSign;
import com.examensarbete.parking.repository.ParkingSignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ParkingSignServiceImpl implements ParkingSignService{

    @Autowired
    private ParkingSignRepository parkingSignRepository;

    @Override
    public ParkingSign saveParkingSign(ParkingSign parkingSign) {
        return parkingSignRepository.save(parkingSign);
    }
    @Override
    public List<ParkingSign> getAllParkingSigns() {
        return parkingSignRepository.findAll();
    }

    @Override
    public ParkingSign getParkingSignById(int id) {
        return parkingSignRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ParkingSign with ID " + id + " not found"));
    }

    @Override
    public List<ParkingSign> searchParkingSigns(String keyword, Sort sort) {
        return parkingSignRepository
                .findAllByParkingSignNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(keyword, keyword, sort);
    }



}
