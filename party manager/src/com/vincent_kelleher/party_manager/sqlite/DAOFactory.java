package com.vincent_kelleher.party_manager.sqlite;

import android.app.Application;
import android.util.Log;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.vincent_kelleher.party_manager.entities.Guest;
import com.vincent_kelleher.party_manager.entities.Room;

import java.sql.SQLException;

public class DAOFactory extends Application
{
    private static DatabaseHelper databaseHelper = null;

    private static Dao<Guest, Integer> guestDao = null;
    private static Dao<Room, Integer> roomDao = null;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseHelper = new DatabaseHelper(this);
    }

    public static Dao<Guest, Integer> getGuestDao()
    {
        if (guestDao == null) {
            try {
                guestDao = databaseHelper.getDao(Guest.class);
            } catch (SQLException e) {
                Log.e("Database", "Erreur de création du DAO : " + e.getMessage());
                e.printStackTrace();
            }
        }

        return guestDao;
    }

    public static Dao<Room, Integer> getRoomDao()
    {
        if (roomDao == null) {
            try {
                roomDao = databaseHelper.getDao(Room.class);
            } catch (SQLException e) {
                Log.e("Database", "Erreur de création du DAO : " + e.getMessage());
                e.printStackTrace();
            }
        }

        return roomDao;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
