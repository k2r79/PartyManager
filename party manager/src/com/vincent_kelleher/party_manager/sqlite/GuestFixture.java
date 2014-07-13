package com.vincent_kelleher.party_manager.sqlite;

import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.vincent_kelleher.party_manager.entities.Guest;
import com.vincent_kelleher.party_manager.entities.Room;

import java.sql.SQLException;

public class GuestFixture
{
    public static void hydrateDatabase(Dao<Guest, Integer> guestDao)
    {
        try {
            guestDao.create(new Guest("Papy Mamie", 2, new Room("205")));
            guestDao.create(new Guest("Buisson", 4));
            guestDao.create(new Guest("Cassiède-Berjon Genevieve JC", 2));
            guestDao.create(new Guest("Berjon Colette", 1));
            guestDao.create(new Guest("Paddy Mandy", 2));
            guestDao.create(new Guest("Adrian Karen", 2));
            guestDao.create(new Guest("Rigal Joël", 1, new Room("221")));
            guestDao.create(new Guest("Pinoit Anne Denis", 2));
            guestDao.create(new Guest("Laroche", 2));
            guestDao.create(new Guest("Le Toquin Annie Jean-Luc", 2));
            guestDao.create(new Guest("O'Brien", 1));
            guestDao.create(new Guest("Treguer Victor Françoise", 2));
            guestDao.create(new Guest("Mohand", 4));
            guestDao.create(new Guest("Jaubertie Françoise", 1));
            guestDao.create(new Guest("Trignol", 3));
            guestDao.create(new Guest("Delannoy", 3));
            guestDao.create(new Guest("Freygefond Daniel Colette", 2));
            guestDao.create(new Guest("Joyeux Nathalie", 1));
            guestDao.create(new Guest("Châtain Denis Fabienne", 2));
        } catch (SQLException e) {
            Log.e("Database", "Erreur d'hydratation des Invités : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
