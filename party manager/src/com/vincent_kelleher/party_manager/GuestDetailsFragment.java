package com.vincent_kelleher.party_manager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.vincent_kelleher.party_manager.entities.Guest;

public class GuestDetailsFragment extends Fragment
{
    private Guest guest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.guest_details, container, false);
        view.setAlpha(0);

        return view;
    }

    public void updateGuest(Guest guest)
    {
        this.guest = guest;

        getView().setAlpha(1);

        TextView guestName = (TextView) getView().findViewById(R.id.guest_details_name);
        TextView guestPhone = (TextView) getView().findViewById(R.id.guest_details_phone);
        Switch guestPresent = (Switch) getView().findViewById(R.id.guest_details_present);

        guestName.setText(guest.toString());
        guestPhone.setText(guest.getPhone());
        guestPresent.setChecked(guest.isPresent());

        guestPresent.setOnCheckedChangeListener(new GuestPresentListener());
    }

    private class GuestPresentListener implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            guest.setPresent(isChecked);

            GuestListFragment guestListFragment = (GuestListFragment) getFragmentManager().findFragmentById(R.id.guest_list_fragment);
            guestListFragment.updateGuestStatistics(null);
        }
    }
}
