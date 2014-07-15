package com.vincent_kelleher.party_manager;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.vincent_kelleher.party_manager.utilities.RoomManager;

import java.util.HashMap;
import java.util.Map;

public class RoomActivity extends Activity
{
    private Map<Integer, FrameLayout> roomFrames = new HashMap<Integer, FrameLayout>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setTitle("Party Manager");
        setContentView(R.layout.rooms);

        LinearLayout oddRoomContainer = (LinearLayout) findViewById(R.id.rooms_odd);
        LinearLayout evenRoomContainer = (LinearLayout) findViewById(R.id.rooms_even);

        roomFrames = RoomManager.generateRooms(this, oddRoomContainer, evenRoomContainer);
    }
}
