package com.examensarbete.parking.controller;

import com.examensarbete.parking.model.ParkingSign;
import com.examensarbete.parking.service.ParkingSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/parkingSign")
public class ParkingSignController {

    @Autowired
    private ParkingSignService parkingSignService;

    private static final String IMAGE_DIR = "uploads/images/";

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        try {
            ParkingSign sign = parkingSignService.getParkingSignById(id);

            File dir = new File(IMAGE_DIR);
            if (!dir.exists()) dir.mkdirs();

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(IMAGE_DIR, filename);
            Files.write(filePath, file.getBytes());

            sign.setImageUrl("/images/" + filename);
            parkingSignService.saveParkingSign(sign);

            return ResponseEntity.ok("Image uploaded and linked to ParkingSign.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public String add(@RequestBody ParkingSign parkingSign){
        System.out.println(parkingSign);
        parkingSignService.saveParkingSign(parkingSign);
        return "New sign is added";
    }
    @GetMapping("/getById/{id}")
    public ParkingSign ParkingSign(@PathVariable int id){
        return parkingSignService.getParkingSignById(id);
    }

    @GetMapping("/getAll")
    public List<ParkingSign> getAllParkingSigns(){
        return parkingSignService.getAllParkingSigns();
    }

    @GetMapping("/search")
    public List<ParkingSign> search(@RequestParam String keyword,
                                    @RequestParam(defaultValue = "parkingSignName") String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        return parkingSignService.searchParkingSigns(keyword, sort);
    }
}
