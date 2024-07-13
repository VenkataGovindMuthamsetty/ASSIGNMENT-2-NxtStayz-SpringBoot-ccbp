package com.example.nxtstayz;

import com.example.nxtstayz.controller.RoomController;
import com.example.nxtstayz.controller.HotelController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private RoomController roomController;

    @Autowired
    private HotelController hotelController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(roomController).isNotNull();
        assertThat(hotelController).isNotNull();
    }
}
