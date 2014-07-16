package com.vincent_kelleher.party_manager.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.vincent_kelleher.party_manager.entities.Guest;
import com.vincent_kelleher.party_manager.entities.Room;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{

    private static final String DATABASE_NAME = "party_manager.db";
    private static final int DATABASE_VERSION = 14;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Guest.class);
            TableUtils.createTable(connectionSource, Room.class);
            useFixtures();
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Guest.class, true);
            TableUtils.dropTable(connectionSource, Room.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    private void useFixtures()
    {
        try {
            DataFixture.hydrateDatabase((Dao<Room, Integer>) getDao(Room.class), (Dao<Guest, Integer>) getDao(Guest.class));
        } catch (SQLException e) {
            Log.e("Database", "Erreur d'extraction des DAO : " + e.getMessage());
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
