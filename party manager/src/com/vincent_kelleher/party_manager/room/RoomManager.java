package com.vincent_kelleher.party_manager.room;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.vincent_kelleher.party_manager.R;
import com.vincent_kelleher.party_manager.entities.Room;

import java.util.HashMap;
import java.util.Map;

public class RoomManager
{
    public static final int NUMBER_OF_ODD_ROOMS = 14;
    public static final int NUMBER_OF_EVEN_ROOMS = 7;
    public static final int ROOM_FRAME_PADDING = 10;

    public static Map<Integer, FrameLayout> generateRooms(Activity activity, LinearLayout oddRoomContainer, LinearLayout evenRoomContainer)
    {
        Map<Integer, FrameLayout> roomFrames = new HashMap<Integer, FrameLayout>();

        for (int roomIndex = 0; roomIndex < NUMBER_OF_ODD_ROOMS * 2; roomIndex += 2) {
            int roomNumber = roomIndex + 1;
            FrameLayout roomFrame = createEmptyRoom(activity);

            TextView roomName = new TextView(activity);
            roomName.setText("2" + (roomNumber < 10 ? "0" + roomNumber : roomNumber));
            roomName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.00f);

            roomFrame.addView(roomName);
            roomFrames.put(roomNumber, roomFrame);

            oddRoomContainer.addView(roomFrame);
        }

        for (int roomIndex = 0; roomIndex < NUMBER_OF_EVEN_ROOMS * 2; roomIndex += 2) {
            int roomNumber = roomIndex + 2;
            FrameLayout roomFrame = createEmptyRoom(activity);

            TextView roomName = new TextView(activity);
            roomName.setText("2" + (roomNumber < 10 ? "0" + roomNumber : roomNumber));
            roomName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.00f);

            roomFrame.addView(roomName);
            roomFrames.put(roomNumber, roomFrame);

            switch (roomNumber) {
                case 2:
                    evenRoomContainer.addView(createEmptyRoom(activity));
                    evenRoomContainer.addView(createEmptyRoom(activity));
                    evenRoomContainer.addView(createEmptyRoom(activity));
                    break;
                case 12:
                    evenRoomContainer.addView(createEmptyRoom(activity));
                    evenRoomContainer.addView(createEmptyRoom(activity));
                    break;
            }

            evenRoomContainer.addView(roomFrame);

            if (roomNumber == 14) {
                evenRoomContainer.addView(createEmptyRoom(activity));
            }
        }

        return roomFrames;
    }

    private static FrameLayout createEmptyRoom(Activity activity)
    {
        FrameLayout roomFrame = new FrameLayout(activity);
        LinearLayout.LayoutParams roomFrameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        roomFrame.setLayoutParams(roomFrameParams);
        roomFrame.setBackgroundResource(R.drawable.border);
        roomFrame.setPadding(10, 10, 10, 10);

        return roomFrame;
    }

    public static void indicateGuestRoom(Room room, Map<Integer, FrameLayout> roomFrames, View.OnClickListener onClickListener, boolean resetOtherRooms)
    {
        if (resetOtherRooms) {
            for (FrameLayout roomFrame : roomFrames.values()) {
                roomFrame.setBackgroundResource(R.drawable.border);
                roomFrame.setPadding(ROOM_FRAME_PADDING, ROOM_FRAME_PADDING, ROOM_FRAME_PADDING, ROOM_FRAME_PADDING);
                roomFrame.setOnClickListener(null);
            }
        }

        if (room != null) {
            char[] roomNameExploded = room.getName().toCharArray();
            String roomNumber = String.valueOf(roomNameExploded[1]) + String.valueOf(roomNameExploded[2]);
            FrameLayout guestRoom = roomFrames.get(Integer.valueOf(roomNumber));
            guestRoom.setBackgroundResource(R.drawable.background_green);
            guestRoom.setPadding(ROOM_FRAME_PADDING, ROOM_FRAME_PADDING, ROOM_FRAME_PADDING, ROOM_FRAME_PADDING);
            guestRoom.setOnClickListener(onClickListener);
        }
    }

    public static void removeGuestRoomIndication(FrameLayout roomFrame, View.OnClickListener onClickListener)
    {
        roomFrame.setBackgroundResource(R.drawable.border);
        roomFrame.setPadding(ROOM_FRAME_PADDING, ROOM_FRAME_PADDING, ROOM_FRAME_PADDING, ROOM_FRAME_PADDING);
        roomFrame.setOnClickListener(onClickListener);
    }
}
