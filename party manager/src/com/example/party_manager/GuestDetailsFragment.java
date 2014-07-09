package com.example.party_manager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.party_manager.entities.Guest;

public class GuestDetailsFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.guest_details, container, false);

        return view;
    }

    public void updateGuest(Guest guest)
    {
        TextView guestName = (TextView) getView().findViewById(R.id.guest_details_name);
        TextView guestPhone = (TextView) getView().findViewById(R.id.guest_details_phone);

        guestName.setText(guest.toString());
        guestPhone.setText(guest.getPhone());
    }
}
