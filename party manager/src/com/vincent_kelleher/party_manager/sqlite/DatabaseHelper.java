package com.vincent_kelleher.party_manager.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.vincent_kelleher.party_manager.entities.CoupleGuest;
import com.vincent_kelleher.party_manager.entities.IndividualGuest;
import com.vincent_kelleher.party_manager.entities.Room;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{

    private static final String DATABASE_NAME = "party_manager.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, IndividualGuest.class);
            TableUtils.createTable(connectionSource, CoupleGuest.class);
            TableUtils.createTable(connectionSource, Room.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, IndividualGuest.class, true);
            TableUtils.dropTable(connectionSource, CoupleGuest.class, true);
            TableUtils.dropTable(connectionSource, Room.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
