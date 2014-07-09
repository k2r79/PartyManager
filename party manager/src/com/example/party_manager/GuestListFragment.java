package com.example.party_manager;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.party_manager.entities.Guest;
import com.example.party_manager.entities.IndividualGuest;

import java.util.ArrayList;
import java.util.List;

public class GuestListFragment extends Fragment
{
    private GuestListAdapter guestListAdapter;
    private final List<Guest> guests = new ArrayList<Guest>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.guest_list, container, false);

        EditText guestSearch = (EditText) view.findViewById(R.id.guest_search);
        ListView guestList = (ListView) view.findViewById(R.id.guest_list);
        TextView guestStatisticsTotal = (TextView) view.findViewById(R.id.guest_statistics_total);
        TextView guestStatisticsPresent = (TextView) view.findViewById(R.id.guest_statistics_present);
        TextView guestStatisticsNotPresent = (TextView) view.findViewById(R.id.guest_statistics_not_present);

        guestSearch.addTextChangedListener(new GuestSearchListener());

        guests.add(new IndividualGuest("Vincent", "Kelleher"));
        guests.get(0).setPhone("06 03 88 68 11");

        guests.add(new IndividualGuest("Fabienne", "Regondaud"));
        guests.get(1).setPhone("06 92 34 23 01");

        guests.add(new IndividualGuest("Noël", "Kelleher"));
        guests.get(2).setPhone("06 92 34 23 01");

        guests.add(new IndividualGuest("Vincent", "Kelleher"));
        guests.get(3).setPhone("06 03 88 68 11");

        guests.add(new IndividualGuest("Fabienne", "Regondaud"));
        guests.get(4).setPhone("06 92 34 23 01");

        guests.add(new IndividualGuest("Noël", "Kelleher"));
        guests.get(5).setPhone("06 92 34 23 01");

        guests.add(new IndividualGuest("Vincent", "Kelleher"));
        guests.get(6).setPhone("06 03 88 68 11");

        guests.add(new IndividualGuest("Fabienne", "Regondaud"));
        guests.get(7).setPhone("06 92 34 23 01");

        guests.add(new IndividualGuest("Noël", "Kelleher"));
        guests.get(8).setPhone("06 92 34 23 01");

        guestListAdapter = new GuestListAdapter(getActivity(), guests);
        guestList.setAdapter(guestListAdapter);
        guestList.setOnItemClickListener(new GuestClickListener());

        guestStatisticsTotal.setText("Invités : " + guests.size());
        guestStatisticsPresent.setText("Présents : " + getPresentGuests());
        guestStatisticsNotPresent.setText("Restant : " + (guests.size() - getPresentGuests()));

        return view;
    }

    private int getPresentGuests()
    {
        int number = 0;
        for (Guest guest : guests) {
            if (guest.isPresent()) {
                number++;
            }
        }

        return number;
    }

    private class GuestSearchListener implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence searchString, int start, int before, int count)
        {
            guestListAdapter.getFilter().filter(searchString.toString());
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    }

    private class GuestClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            GuestDetailsFragment guestDetailsFragment = (GuestDetailsFragment) getFragmentManager().findFragmentById(R.id.guest_details_fragment);
            guestDetailsFragment.updateGuest((Guest) guestListAdapter.getItem(position));
        }
    }
}
