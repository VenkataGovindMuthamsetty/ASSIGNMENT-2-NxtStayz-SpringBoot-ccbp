package com.example.nxtstayz;

import com.example.nxtstayz.model.Hotel;
import com.example.nxtstayz.model.Room;
import com.example.nxtstayz.repository.HotelJpaRepository;
import com.example.nxtstayz.repository.RoomJpaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.HashMap;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = { "/schema.sql", "/data.sql" })
public class NxtStayzControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private RoomJpaRepository roomJpaRepository;

        @Autowired
        private HotelJpaRepository hotelJpaRepository;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        private HashMap<Integer, Object[]> hotelsHashMap = new HashMap<>();
        {
                hotelsHashMap.put(1, new Object[] { "The Plaza Hotel", "New York", 4 });
                hotelsHashMap.put(2, new Object[] { "The Beverly Hills Hotel", "Los Angeles", 5 });
                hotelsHashMap.put(3, new Object[] { "The Langham", "Chicago", 3 });
                hotelsHashMap.put(4, new Object[] { "Fontain Miami Beach", "Miami", 4 }); // POST
                hotelsHashMap.put(5, new Object[] { "Fontainebleau Miami Beach", "Miami", 5 }); // PUT
        }

        private HashMap<Integer, Object[]> roomsHashMap = new HashMap<>();
        {
                roomsHashMap.put(1, new Object[] { "A-101", "Deluxe Room", 375.00, 1 });
                roomsHashMap.put(2, new Object[] { "A-205", "Suite", 950.00, 1 });
                roomsHashMap.put(3, new Object[] { "B-106", "Penthouse Suite", 2500.00, 1 });
                roomsHashMap.put(4, new Object[] { "C-401", "Superior Guest Room", 465.00, 2 });
                roomsHashMap.put(5, new Object[] { "D-202", "Bungalow", 1250.00, 2 });
                roomsHashMap.put(6, new Object[] { "A-107", "Penthouse Suite", 3300.00, 2 });
                roomsHashMap.put(7, new Object[] { "A-301", "Grand Room", 410.00, 3 });
                roomsHashMap.put(8, new Object[] { "C-313", "Executive Suite", 700.00, 3 });
                roomsHashMap.put(9, new Object[] { "D-404", "Premier Suite", 880.00, 3 });
                roomsHashMap.put(10, new Object[] { "D-201", "Ocean Room", 300.00, 2 }); // POST
                roomsHashMap.put(11, new Object[] { "D-401", "Oceanfront Room", 350.00, 4 }); // PUT
        }

        @Test
        @Order(1)
        public void testGetHotels() throws Exception {
                mockMvc.perform(get("/hotels")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].hotelId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].hotelName", Matchers.equalTo(hotelsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].location", Matchers.equalTo(hotelsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].rating", Matchers.equalTo(hotelsHashMap.get(1)[2])))

                                .andExpect(jsonPath("$[1].hotelId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].hotelName", Matchers.equalTo(hotelsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].location", Matchers.equalTo(hotelsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].rating", Matchers.equalTo(hotelsHashMap.get(2)[2])))

                                .andExpect(jsonPath("$[2].hotelId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].hotelName", Matchers.equalTo(hotelsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].location", Matchers.equalTo(hotelsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].rating", Matchers.equalTo(hotelsHashMap.get(3)[2])));
        }

        @Test
        @Order(2)
        public void testGetHotelNotFound() throws Exception {
                mockMvc.perform(get("/hotels/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(3)
        public void testGetHotelById() throws Exception {
                mockMvc.perform(get("/hotels/1")).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(1)[2])));

                mockMvc.perform(get("/hotels/2")).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(2)[2])));

                mockMvc.perform(get("/hotels/3")).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(3)[2])));
        }

        @Test
        @Order(4)
        public void testPostHotel() throws Exception {
                String content = "{\n    \"hotelName\": \"" + hotelsHashMap.get(4)[0] + "\",\n    \"location\": \""
                                + hotelsHashMap.get(4)[1] + "\",\n    \"rating\": " + hotelsHashMap.get(4)[2] + "\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/hotels")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(4)[2])));
        }

        @Test
        @Order(5)
        public void testAfterPostHotel() throws Exception {
                mockMvc.perform(get("/hotels/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(4)[2])));
        }

        @Test
        @Order(6)
        public void testDbAfterPostHotel() throws Exception {
                Hotel hotel = hotelJpaRepository.findById(4).get();

                assertEquals(hotel.getHotelId(), 4);
                assertEquals(hotel.getHotelName(), hotelsHashMap.get(4)[0]);
                assertEquals(hotel.getLocation(), hotelsHashMap.get(4)[1]);
                assertEquals(hotel.getRating(), hotelsHashMap.get(4)[2]);
        }

        @Test
        @Order(7)
        public void testPutHotelNotFound() throws Exception {
                String content = "{\n    \"hotelName\": \"" + hotelsHashMap.get(5)[0] + "\",\n    \"location\": \""
                                + hotelsHashMap.get(5)[1] + "\",\n    \"rating\": " + hotelsHashMap.get(5)[2] + "\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/hotels/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(8)
        public void testPutHotel() throws Exception {
                String content = "{\n    \"hotelName\": \"" + hotelsHashMap.get(5)[0] + "\",\n    \"location\": \""
                                + hotelsHashMap.get(5)[1] + "\",\n    \"rating\": " + hotelsHashMap.get(5)[2] + "\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/hotels/4")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(5)[2])));
        }

        @Test
        @Order(9)
        public void testAfterPutHotel() throws Exception {
                mockMvc.perform(get("/hotels/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(5)[2])));
        }

        @Test
        @Order(10)
        public void testDbAfterPutHotel() throws Exception {
                Hotel hotel = hotelJpaRepository.findById(4).get();

                assertEquals(hotel.getHotelId(), 4);
                assertEquals(hotel.getHotelName(), hotelsHashMap.get(5)[0]);
                assertEquals(hotel.getLocation(), hotelsHashMap.get(5)[1]);
                assertEquals(hotel.getRating(), hotelsHashMap.get(5)[2]);
        }

        @Test
        @Order(11)
        public void testGetRooms() throws Exception {
                mockMvc.perform(get("/hotels/rooms")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(9)))

                                .andExpect(jsonPath("$[0].roomId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].roomNumber", Matchers.equalTo(roomsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].roomType", Matchers.equalTo(roomsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].price", Matchers.equalTo(roomsHashMap.get(1)[2])))
                                .andExpect(jsonPath("$[0].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(1)[3])))

                                .andExpect(jsonPath("$[1].roomId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].roomNumber", Matchers.equalTo(roomsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].roomType", Matchers.equalTo(roomsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].price", Matchers.equalTo(roomsHashMap.get(2)[2])))
                                .andExpect(jsonPath("$[1].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(2)[3])))

                                .andExpect(jsonPath("$[2].roomId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].roomNumber", Matchers.equalTo(roomsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].roomType", Matchers.equalTo(roomsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].price", Matchers.equalTo(roomsHashMap.get(3)[2])))
                                .andExpect(jsonPath("$[2].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(3)[3])))

                                .andExpect(jsonPath("$[3].roomId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].roomNumber", Matchers.equalTo(roomsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].roomType", Matchers.equalTo(roomsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].price", Matchers.equalTo(roomsHashMap.get(4)[2])))
                                .andExpect(jsonPath("$[3].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(4)[3])))

                                .andExpect(jsonPath("$[4].roomId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$[4].roomNumber", Matchers.equalTo(roomsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$[4].roomType", Matchers.equalTo(roomsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$[4].price", Matchers.equalTo(roomsHashMap.get(5)[2])))
                                .andExpect(jsonPath("$[4].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(5)[3])))

                                .andExpect(jsonPath("$[5].roomId", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$[5].roomNumber", Matchers.equalTo(roomsHashMap.get(6)[0])))
                                .andExpect(jsonPath("$[5].roomType", Matchers.equalTo(roomsHashMap.get(6)[1])))
                                .andExpect(jsonPath("$[5].price", Matchers.equalTo(roomsHashMap.get(6)[2])))
                                .andExpect(jsonPath("$[5].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(6)[3])))

                                .andExpect(jsonPath("$[6].roomId", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$[6].roomNumber", Matchers.equalTo(roomsHashMap.get(7)[0])))
                                .andExpect(jsonPath("$[6].roomType", Matchers.equalTo(roomsHashMap.get(7)[1])))
                                .andExpect(jsonPath("$[6].price", Matchers.equalTo(roomsHashMap.get(7)[2])))
                                .andExpect(jsonPath("$[6].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(7)[3])))

                                .andExpect(jsonPath("$[7].roomId", Matchers.equalTo(8)))
                                .andExpect(jsonPath("$[7].roomNumber", Matchers.equalTo(roomsHashMap.get(8)[0])))
                                .andExpect(jsonPath("$[7].roomType", Matchers.equalTo(roomsHashMap.get(8)[1])))
                                .andExpect(jsonPath("$[7].price", Matchers.equalTo(roomsHashMap.get(8)[2])))
                                .andExpect(jsonPath("$[7].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(8)[3])))

                                .andExpect(jsonPath("$[8].roomId", Matchers.equalTo(9)))
                                .andExpect(jsonPath("$[8].roomNumber", Matchers.equalTo(roomsHashMap.get(9)[0])))
                                .andExpect(jsonPath("$[8].roomType", Matchers.equalTo(roomsHashMap.get(9)[1])))
                                .andExpect(jsonPath("$[8].price", Matchers.equalTo(roomsHashMap.get(9)[2])))
                                .andExpect(jsonPath("$[8].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(9)[3])));
        }

        @Test
        @Order(12)
        public void testGetRoomNotFound() throws Exception {
                mockMvc.perform(get("/hotels/rooms/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(13)
        public void testGetRoomById() throws Exception {
                mockMvc.perform(get("/hotels/rooms/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(1)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(1)[3])));

                mockMvc.perform(get("/hotels/rooms/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(2)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(2)[3])));

                mockMvc.perform(get("/hotels/rooms/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(3)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(3)[3])));

                mockMvc.perform(get("/hotels/rooms/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(4)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(4)[3])));

                mockMvc.perform(get("/hotels/rooms/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(5)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(5)[3])));

                mockMvc.perform(get("/hotels/rooms/6")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(6)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(6)[3])));

                mockMvc.perform(get("/hotels/rooms/7")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(7)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(7)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(7)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(7)[3])));

                mockMvc.perform(get("/hotels/rooms/8")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(8)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(8)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(8)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(8)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(8)[3])));

                mockMvc.perform(get("/hotels/rooms/9")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(9)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(9)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(9)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(9)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(9)[3])));
        }

        @Test
        @Order(14)
        public void testPostRoom() throws Exception {
                String content = "{\n    \"roomNumber\": \"" + roomsHashMap.get(10)[0] + "\",\n    \"roomType\": \""
                                + roomsHashMap.get(10)[1] + "\",\n    \"price\": " + roomsHashMap.get(10)[2]
                                + ",\n    \"hotel\": {\n        \"hotelId\": " + roomsHashMap.get(10)[3] + "\n    }\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/hotels/rooms")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(10)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(10)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(10)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(10)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(10)[3])));
        }

        @Test
        @Order(15)
        public void testAfterPostRoom() throws Exception {
                mockMvc.perform(get("/hotels/rooms/10")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(10)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(10)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(10)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(10)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(10)[3])));
        }

        @Test
        @Order(16)
        public void testDbAfterPostRoom() throws Exception {
                Room room = roomJpaRepository.findById(10).get();

                assertEquals(room.getRoomId(), 10);
                assertEquals(room.getRoomNumber(), roomsHashMap.get(10)[0]);
                assertEquals(room.getRoomType(), roomsHashMap.get(10)[1]);
                assertEquals(room.getPrice(), roomsHashMap.get(10)[2]);
                assertEquals(room.getHotel().getHotelId(), roomsHashMap.get(10)[3]);
        }

        @Test
        @Order(17)
        public void testPutRoomNotFound() throws Exception {
                String content = "{\n    \"roomNumber\": \"" + roomsHashMap.get(11)[0] + "\",\n    \"roomType\": \""
                                + roomsHashMap.get(11)[1] + "\",\n    \"price\": " + roomsHashMap.get(11)[2]
                                + ",\n    \"hotel\": {\n        \"hotelId\": " + roomsHashMap.get(11)[3] + "\n    }\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/hotels/rooms/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(18)
        public void testPutRoom() throws Exception {
                String content = "{\n    \"roomNumber\": \"" + roomsHashMap.get(11)[0] + "\",\n    \"roomType\": \""
                                + roomsHashMap.get(11)[1] + "\",\n    \"price\": " + roomsHashMap.get(11)[2]
                                + ",\n    \"hotel\": {\n        \"hotelId\": " + roomsHashMap.get(11)[3] + "\n    }\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/hotels/rooms/10")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(10)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(11)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(11)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(11)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(11)[3])));
        }

        @Test
        @Order(19)
        public void testAfterPutRoom() throws Exception {
                mockMvc.perform(get("/hotels/rooms/10")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(10)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(11)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(11)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(11)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId", Matchers.equalTo(roomsHashMap.get(11)[3])));
        }

        @Test
        @Order(20)
        public void testDbAfterPutRoom() throws Exception {
                Room room = roomJpaRepository.findById(10).get();

                assertEquals(room.getRoomId(), 10);
                assertEquals(room.getRoomNumber(), roomsHashMap.get(11)[0]);
                assertEquals(room.getRoomType(), roomsHashMap.get(11)[1]);
                assertEquals(room.getPrice(), roomsHashMap.get(11)[2]);
                assertEquals(room.getHotel().getHotelId(), roomsHashMap.get(11)[3]);
        }

        @Test
        @Order(21)
        public void testDeleteHotelNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/hotels/148")
                                .contentType(MediaType.APPLICATION_JSON);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(22)
        public void testDeleteHotel() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/hotels/4")
                                .contentType(MediaType.APPLICATION_JSON);

                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(23)
        public void testAfterDeleteHotel() throws Exception {
                mockMvc.perform(get("/hotels")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].hotelId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].hotelName", Matchers.equalTo(hotelsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].location", Matchers.equalTo(hotelsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].rating", Matchers.equalTo(hotelsHashMap.get(1)[2])))

                                .andExpect(jsonPath("$[1].hotelId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].hotelName", Matchers.equalTo(hotelsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].location", Matchers.equalTo(hotelsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].rating", Matchers.equalTo(hotelsHashMap.get(2)[2])))

                                .andExpect(jsonPath("$[2].hotelId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].hotelName", Matchers.equalTo(hotelsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].location", Matchers.equalTo(hotelsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].rating", Matchers.equalTo(hotelsHashMap.get(3)[2])));

                mockMvc.perform(get("/hotels/rooms/10")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.roomId", Matchers.equalTo(10)))
                                .andExpect(jsonPath("$.roomNumber", Matchers.equalTo(roomsHashMap.get(11)[0])))
                                .andExpect(jsonPath("$.roomType", Matchers.equalTo(roomsHashMap.get(11)[1])))
                                .andExpect(jsonPath("$.price", Matchers.equalTo(roomsHashMap.get(11)[2])))
                                .andExpect(jsonPath("$.hotel.hotelId").doesNotExist());
        }

        @Test
        @Order(24)
        public void testDeleteRoomNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/hotels/rooms/148")
                                .contentType(MediaType.APPLICATION_JSON);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(25)
        public void testDeleteRoom() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/hotels/rooms/10")
                                .contentType(MediaType.APPLICATION_JSON);

                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(26)
        public void testAfterDeleteRoom() throws Exception {
                mockMvc.perform(get("/hotels/rooms")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(9)))

                                .andExpect(jsonPath("$[0].roomId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].roomNumber", Matchers.equalTo(roomsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].roomType", Matchers.equalTo(roomsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].price", Matchers.equalTo(roomsHashMap.get(1)[2])))
                                .andExpect(jsonPath("$[0].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(1)[3])))

                                .andExpect(jsonPath("$[1].roomId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].roomNumber", Matchers.equalTo(roomsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].roomType", Matchers.equalTo(roomsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].price", Matchers.equalTo(roomsHashMap.get(2)[2])))
                                .andExpect(jsonPath("$[1].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(2)[3])))

                                .andExpect(jsonPath("$[2].roomId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].roomNumber", Matchers.equalTo(roomsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].roomType", Matchers.equalTo(roomsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].price", Matchers.equalTo(roomsHashMap.get(3)[2])))
                                .andExpect(jsonPath("$[2].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(3)[3])))

                                .andExpect(jsonPath("$[3].roomId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].roomNumber", Matchers.equalTo(roomsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].roomType", Matchers.equalTo(roomsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].price", Matchers.equalTo(roomsHashMap.get(4)[2])))
                                .andExpect(jsonPath("$[3].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(4)[3])))

                                .andExpect(jsonPath("$[4].roomId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$[4].roomNumber", Matchers.equalTo(roomsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$[4].roomType", Matchers.equalTo(roomsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$[4].price", Matchers.equalTo(roomsHashMap.get(5)[2])))
                                .andExpect(jsonPath("$[4].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(5)[3])))

                                .andExpect(jsonPath("$[5].roomId", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$[5].roomNumber", Matchers.equalTo(roomsHashMap.get(6)[0])))
                                .andExpect(jsonPath("$[5].roomType", Matchers.equalTo(roomsHashMap.get(6)[1])))
                                .andExpect(jsonPath("$[5].price", Matchers.equalTo(roomsHashMap.get(6)[2])))
                                .andExpect(jsonPath("$[5].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(6)[3])))

                                .andExpect(jsonPath("$[6].roomId", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$[6].roomNumber", Matchers.equalTo(roomsHashMap.get(7)[0])))
                                .andExpect(jsonPath("$[6].roomType", Matchers.equalTo(roomsHashMap.get(7)[1])))
                                .andExpect(jsonPath("$[6].price", Matchers.equalTo(roomsHashMap.get(7)[2])))
                                .andExpect(jsonPath("$[6].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(7)[3])))

                                .andExpect(jsonPath("$[7].roomId", Matchers.equalTo(8)))
                                .andExpect(jsonPath("$[7].roomNumber", Matchers.equalTo(roomsHashMap.get(8)[0])))
                                .andExpect(jsonPath("$[7].roomType", Matchers.equalTo(roomsHashMap.get(8)[1])))
                                .andExpect(jsonPath("$[7].price", Matchers.equalTo(roomsHashMap.get(8)[2])))
                                .andExpect(jsonPath("$[7].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(8)[3])))

                                .andExpect(jsonPath("$[8].roomId", Matchers.equalTo(9)))
                                .andExpect(jsonPath("$[8].roomNumber", Matchers.equalTo(roomsHashMap.get(9)[0])))
                                .andExpect(jsonPath("$[8].roomType", Matchers.equalTo(roomsHashMap.get(9)[1])))
                                .andExpect(jsonPath("$[8].price", Matchers.equalTo(roomsHashMap.get(9)[2])))
                                .andExpect(jsonPath("$[8].hotel.hotelId", Matchers.equalTo(roomsHashMap.get(9)[3])));
        }

        @Test
        @Order(27)
        public void testGetHotelByRoomId() throws Exception {
                mockMvc.perform(get("/rooms/1/hotel")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(1)[2])));

                mockMvc.perform(get("/rooms/2/hotel")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(1)[2])));

                mockMvc.perform(get("/rooms/3/hotel")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(1)[2])));

                mockMvc.perform(get("/rooms/4/hotel")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(2)[2])));

                mockMvc.perform(get("/rooms/5/hotel")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(2)[2])));

                mockMvc.perform(get("/rooms/6/hotel")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(2)[2])));

                mockMvc.perform(get("/rooms/7/hotel")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(3)[2])));

                mockMvc.perform(get("/rooms/8/hotel")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(3)[2])));

                mockMvc.perform(get("/rooms/9/hotel")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.hotelId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.hotelName", Matchers.equalTo(hotelsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(hotelsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(hotelsHashMap.get(3)[2])));
        }

        @Test
        @Order(28)
        public void testGetRoomsByHotelId() throws Exception {
                mockMvc.perform(get("/hotels/1/rooms")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                                .andExpect(jsonPath("$[*].roomId", hasItem(1)))
                                .andExpect(jsonPath("$[*].roomId", hasItem(2)))
                                .andExpect(jsonPath("$[*].roomId", hasItem(3)));

                mockMvc.perform(get("/hotels/2/rooms")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                                .andExpect(jsonPath("$[*].roomId", hasItem(4)))
                                .andExpect(jsonPath("$[*].roomId", hasItem(5)))
                                .andExpect(jsonPath("$[*].roomId", hasItem(6)));

                mockMvc.perform(get("/hotels/3/rooms")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                                .andExpect(jsonPath("$[*].roomId", hasItem(7)))
                                .andExpect(jsonPath("$[*].roomId", hasItem(8)))
                                .andExpect(jsonPath("$[*].roomId", hasItem(9)));

        }

        @AfterAll
        public void cleanup() {
                jdbcTemplate.execute("drop table room");
                jdbcTemplate.execute("drop table hotel");
        }

}