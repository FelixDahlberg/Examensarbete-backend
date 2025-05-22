package com.examensarbete.parking.controller;


import com.examensarbete.parking.service.ParkingSignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(ParkingSignController.class)
public class ParkingSignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingSignService parkingSignService;

    @Test
    public void testAddParkingSign() throws Exception {
        String json = """
            {
                "parkingSignName": "Sign A",
                "description": "Description A"
            }
        """;

        mockMvc.perform(post("/parkingSign/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("New sign is added"));
    }
}