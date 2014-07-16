package com.vincent_kelleher.party_manager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.j256.ormlite.dao.Dao;
import com.vincent_kelleher.party_manager.entities.Room;
import com.vincent_kelleher.party_manager.sqlite.DAOFactory;
import com.vincent_kelleher.party_manager.utilities.RoomManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomActivity extends Activity
{
    private Dao<Room, Integer> roomDao;
    private Map<Integer, FrameLayout> roomFrames = new HashMap<Integer, FrameLayout>();
    private List<Room> guestRooms;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        roomDao = ((DAOFactory) getApplication()).getRoomDao();

        setTitle("Party Manager");
        setContentView(R.layout.rooms);

        generateRooms();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void generateRooms()
    {
        LinearLayout oddRoomContainer = (LinearLayout) findViewById(R.id.rooms_odd);
        LinearLayout evenRoomContainer = (LinearLayout) findViewById(R.id.rooms_even);

        roomFrames = RoomManager.generateRooms(this, oddRoomContainer, evenRoomContainer);

        try {
            guestRooms = roomDao.queryForAll();
        } catch (SQLException e) {
            Log.e("Database", "Erreur d'extraction des Chambres : " + e.getMessage());
        }

        for (Room room : guestRooms) {
            RoomManager.indicateGuestRoom(room, roomFrames, false);
        }
    }
}
