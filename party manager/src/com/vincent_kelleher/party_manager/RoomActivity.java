package com.vincent_kelleher.party_manager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.j256.ormlite.dao.Dao;
import com.vincent_kelleher.party_manager.entities.Guest;
import com.vincent_kelleher.party_manager.entities.Room;
import com.vincent_kelleher.party_manager.room.RoomManager;
import com.vincent_kelleher.party_manager.sqlite.DAOFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomActivity extends Activity
{
    private Dao<Room, Integer> roomDao;
    private Dao<Guest, Integer> guestDao;
    private Map<Integer, FrameLayout> roomFrames = new HashMap<Integer, FrameLayout>();
    private List<Room> guestRooms;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        roomDao = ((DAOFactory) getApplication()).getRoomDao();
        guestDao = ((DAOFactory) getApplication()).getGuestDao();

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
            if (room.getGuest() != null) {
                RoomManager.indicateGuestRoom(room, roomFrames, new GuestRoomClickListener(room), false);
            }
        }
    }

    private class GuestRoomClickListener implements View.OnClickListener
    {
        private Room room;

        private GuestRoomClickListener(Room room)
        {
            this.room = room;
        }

        @Override
        public void onClick(View v)
        {
            createGuestRoomPopup(v, room);
        }
    }

    private void createGuestRoomPopup(View v, final Room room)
    {
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.guest_room_popup, null);

        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.showAsDropDown(v);

        TextView roomGuestName = (TextView) popupView.findViewById(R.id.room_popup_guest_name);
        TextView roomGuestHeadcount = (TextView) popupView.findViewById(R.id.room_popup_guest_headcount);

        roomGuestName.setText(room.getGuest().getName());
        roomGuestHeadcount.setText(String.valueOf(room.getGuest().getHeadcount()) + " personnes");

        Button okButton = (Button) popupView.findViewById(R.id.room_popup_ok);
        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popupWindow.dismiss();
            }
        });

        Button removeFromRoomButton = (Button) popupView.findViewById(R.id.room_popup_remove_from_room);
        removeFromRoomButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    Guest guest = room.getGuest();
                    guest.setRoom(null);
                    guestDao.update(guest);

                    room.setGuest(null);
                    roomDao.update(room);
                } catch (SQLException e) {
                    Log.e("Database", "Erreur de changement de Chambre : " + e.getMessage());
                }

                Log.d("Rooms", room.getName().toCharArray()[1] + "" + room.getName().toCharArray()[2]);
                RoomManager.removeGuestRoomIndication(roomFrames.get(Integer.valueOf(room.getName().toCharArray()[1] + "" + room.getName().toCharArray()[2])), null);

                popupWindow.dismiss();
            }
        });
    }
}
