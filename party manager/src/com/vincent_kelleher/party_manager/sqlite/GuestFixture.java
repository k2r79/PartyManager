package com.vincent_kelleher.party_manager.sqlite;

import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.vincent_kelleher.party_manager.entities.Guest;

import java.sql.SQLException;

public class GuestFixture
{
    public static void hydrateDatabase(Dao<Guest, Integer> guestDao)
    {
        try {
            guestDao.create(new Guest("Parents", 2));
            guestDao.create(new Guest("Buisson famille", 4));
            guestDao.create(new Guest("Cassiède-Berjon Gene-JC", 2));
            guestDao.create(new Guest("Berjon Colette", 1));
            guestDao.create(new Guest("Paddy-Mandy", 2));
            guestDao.create(new Guest("Adrian-Karen", 2));
            guestDao.create(new Guest("Nous + Hélène", 4));
            guestDao.create(new Guest("Rigal Joël", 1));
            guestDao.create(new Guest("Pinoit Anne-Denis", 2));
            guestDao.create(new Guest("Laroche famille", 2));
            guestDao.create(new Guest("Le Toquin Annie-Jean-Luc", 2));
            guestDao.create(new Guest("O'Brien Maryse-Anna?", 1));
            guestDao.create(new Guest("Treguer Victor-Françoise", 2));
            guestDao.create(new Guest("Mohand famille", 4));
            guestDao.create(new Guest("Jaubertie", 1));
            guestDao.create(new Guest("Trignol famille", 3));
            guestDao.create(new Guest("Delannoy famille", 3));
            guestDao.create(new Guest("Freygefond Daniel-Colette", 2));
            guestDao.create(new Guest("Joyeux Nathalie", 1));
            guestDao.create(new Guest("Châtain Denis-Fabienne", 2));
        } catch (SQLException e) {
            Log.e("Database", "Erreur d'hydratation des Invités : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
