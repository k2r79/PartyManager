package com.example.party_manager;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import com.example.party_manager.entities.Guest;
import com.example.party_manager.entities.IndividualGuest;

import java.util.ArrayList;
import java.util.List;

public class GuestListFragment extends Fragment
{
    private GuestListAdapter guestListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.guest_list, container, false);

        EditText guestSearch = (EditText) view.findViewById(R.id.guest_search);
        ListView guestList = (ListView) view.findViewById(R.id.guest_list);

        guestSearch.addTextChangedListener(new GuestSearchListener());

        List<Guest> guests = new ArrayList<Guest>();
        guests.add(new IndividualGuest("Vincent", "Kelleher"));
        guests.add(new IndividualGuest("Fabienne", "Regondaud"));
        guests.add(new IndividualGuest("Noël", "Kelleher"));

        guestListAdapter = new GuestListAdapter(getActivity(), guests);
        guestList.setAdapter(guestListAdapter);

        return view;
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
}
