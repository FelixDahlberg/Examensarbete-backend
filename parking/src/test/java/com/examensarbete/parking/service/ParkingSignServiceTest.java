package com.examensarbete.parking.service;

import com.examensarbete.parking.model.ParkingSign;
import com.examensarbete.parking.repository.ParkingSignRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class ParkingSignServiceTest {

    @Mock
    private ParkingSignRepository parkingSignRepository;

    @InjectMocks
    private ParkingSignServiceImpl parkingSignService;

    @Test
    public void testSaveParkingSign() {
        ParkingSign sign = new ParkingSign();
        sign.setParkingSignName("Test Sign");
        sign.setDescription("Test Desc");

        parkingSignService.saveParkingSign(sign);

        verify(parkingSignRepository).save(sign);
    }
}