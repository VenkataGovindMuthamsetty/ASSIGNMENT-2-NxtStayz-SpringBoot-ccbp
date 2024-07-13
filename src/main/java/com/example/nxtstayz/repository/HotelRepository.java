package com.example.nxtstayz.repository;

import com.example.nxtstayz.model.Hotel;
import com.example.nxtstayz.model.Room;

import java.util.*;

public interface HotelRepository {
    ArrayList<Hotel> getHotels();

    Hotel getHotelById(int hotelId);

    Hotel addHotel(Hotel hotel);

    Hotel updateHotel(int hotelId, Hotel hotel);

    void deleteHotel(int hotelId);

    List<Room> getHotelRooms(int hotelId);
}