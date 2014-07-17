package com.vincent_kelleher.party_manager.sqlite;

import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.vincent_kelleher.party_manager.entities.Guest;
import com.vincent_kelleher.party_manager.entities.Room;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.vincent_kelleher.party_manager.room.RoomManager.NUMBER_OF_EVEN_ROOMS;
import static com.vincent_kelleher.party_manager.room.RoomManager.NUMBER_OF_ODD_ROOMS;

public class DataFixture
{
    public static void hydrateDatabase(Dao<Room, Integer> roomDao, Dao<Guest, Integer> guestDao)
    {
        try {
            // Create Rooms
            Map<String, Room> rooms = new HashMap<String, Room>();
            for (int roomIndex = 0; roomIndex < NUMBER_OF_ODD_ROOMS * 2; roomIndex += 2) {
                int roomNumber = roomIndex + 1;
                createRoomFromIndex(roomDao, rooms, roomNumber);
            }

            for (int roomIndex = 0; roomIndex < NUMBER_OF_EVEN_ROOMS * 2; roomIndex += 2) {
                int roomNumber = roomIndex + 2;
                createRoomFromIndex(roomDao, rooms, roomNumber);
            }

            createGuest(guestDao, roomDao, "Papy Mamie", 2, null);
            createGuest(guestDao, roomDao, "Buisson", 2, rooms.get("225"));
            createGuest(guestDao, roomDao, "Buisson Juniors", 2, rooms.get("208"));
            createGuest(guestDao, roomDao, "Cassiède-Berjon Genevieve JC", 2, rooms.get("227"));
            createGuest(guestDao, roomDao, "Berjon Colette", 1, rooms.get("214"));
            createGuest(guestDao, roomDao, "Paddy Mandy", 2, null);
            createGuest(guestDao, roomDao, "Adrian Karen", 2, null);
            createGuest(guestDao, roomDao, "Rigal Joël", 1, rooms.get("204"));
            createGuest(guestDao, roomDao, "Pinoit Anne Denis", 2, rooms.get("221"));
            createGuest(guestDao, roomDao, "Laroche", 2, rooms.get("215"));
            createGuest(guestDao, roomDao, "Le Toquin Annie Jean-Luc", 2, rooms.get("219"));
            createGuest(guestDao, roomDao, "O'Brien", 1, rooms.get("206"));
            createGuest(guestDao, roomDao, "Treguer Victor Françoise", 2, rooms.get("217"));
            createGuest(guestDao, roomDao, "Mohand", 4, null);
            createGuest(guestDao, roomDao, "Jaubertie Françoise", 1, null);
            createGuest(guestDao, roomDao, "Trignol", 3, null);
            createGuest(guestDao, roomDao, "Delannoy", 3, null);
            createGuest(guestDao, roomDao, "Freygefond Daniel Colette", 2, rooms.get("215"));
            createGuest(guestDao, roomDao, "Joyeux Nathalie", 1, rooms.get("202"));
            createGuest(guestDao, roomDao, "Châtain Denis Fabienne", 2, rooms.get("223"));
            createGuest(guestDao, roomDao, "Cousines Adoptives", 3, rooms.get("201"));
        } catch (SQLException e) {
            Log.e("Database", "Erreur d'hydratation des Invités : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createGuest(Dao<Guest, Integer> guestDao, Dao<Room, Integer> roomDao, String name, int headcount,  Room room) throws SQLException
    {
        Guest guest = new Guest(name, headcount, room);

        guestDao.create(guest);

        if (room != null) {
            room.setGuest(guest);
            roomDao.update(room);
        }
    }

    private static void createRoomFromIndex(Dao<Room, Integer> roomDao, Map<String, Room> rooms, int roomNumber) throws SQLException
    {
        String roomTitle = "2" + (roomNumber < 10 ? "0" + roomNumber : roomNumber);

        Room room = new Room(roomTitle);
        roomDao.create(room);

        rooms.put(roomTitle, room);
    }
}
