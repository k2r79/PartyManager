package com.vincent_kelleher.party_manager.sqlite;

import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.vincent_kelleher.party_manager.entities.IndividualGuest;

import java.sql.SQLException;

public class GuestFixture
{
    public static void hydrateDatabase(Dao<IndividualGuest, Integer> guestDao)
    {
        try {
            guestDao.create(new IndividualGuest("Jean", "Regondaud"));
            guestDao.create(new IndividualGuest("Pierrette", "Regondaud"));
            guestDao.create(new IndividualGuest("Geneviève", "Cassiède-Berjon"));
            guestDao.create(new IndividualGuest("Jean-Claude", "Cassiède-Berjon"));
            guestDao.create(new IndividualGuest("Colette", "Berjon"));
            guestDao.create(new IndividualGuest("Patrick", "Kelleher"));
            guestDao.create(new IndividualGuest("Mandy", "Kelleher"));
            guestDao.create(new IndividualGuest("Adrian", "Kelleher"));
            guestDao.create(new IndividualGuest("Karen", "Kelleher"));
            guestDao.create(new IndividualGuest("Joël", "Rigal"));
            guestDao.create(new IndividualGuest("Anne", "Pinoit"));
            guestDao.create(new IndividualGuest("Denis", "Pinoit"));
            guestDao.create(new IndividualGuest("Chantale", "Laroche"));
            guestDao.create(new IndividualGuest("Philippe", "Laroche"));
            guestDao.create(new IndividualGuest("Annie", "Le Toquin"));
            guestDao.create(new IndividualGuest("Jean-Luc", "Le Toquin"));
            guestDao.create(new IndividualGuest("Maryse", "O'Brien"));
            guestDao.create(new IndividualGuest("Victor", "Treguer"));
            guestDao.create(new IndividualGuest("Françoise", "Treguer"));
            guestDao.create(new IndividualGuest("Jean-Claude", "Mohand"));
            guestDao.create(new IndividualGuest("Sandrine", "Mohand"));
            guestDao.create(new IndividualGuest("Françoise", "Jaubertie"));
            guestDao.create(new IndividualGuest("François", "Trignol"));
            guestDao.create(new IndividualGuest("Femme de François", "Trignol"));
            guestDao.create(new IndividualGuest("", "Delannoy"));
            guestDao.create(new IndividualGuest("Daniel", "Freygefond"));
            guestDao.create(new IndividualGuest("Colette", "Freygefond"));
            guestDao.create(new IndividualGuest("Nathalie", "Joyeux"));
            guestDao.create(new IndividualGuest("Denis", "Chatain"));
            guestDao.create(new IndividualGuest("Fabienne", "Chatain"));
        } catch (SQLException e) {
            Log.e("Database", "Erreur d'hydratation des Invités : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
