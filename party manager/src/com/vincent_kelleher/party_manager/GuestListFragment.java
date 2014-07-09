package com.vincent_kelleher.party_manager;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.j256.ormlite.dao.Dao;
import com.vincent_kelleher.party_manager.entities.Guest;
import com.vincent_kelleher.party_manager.entities.IndividualGuest;
import com.vincent_kelleher.party_manager.sqlite.DAOFactory;
import com.vincent_kelleher.party_manager.sqlite.GuestFixture;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestListFragment extends Fragment
{
    private Dao<IndividualGuest, Integer> guestDao;
    private GuestListAdapter guestListAdapter;
    private List<IndividualGuest> guests = new ArrayList<IndividualGuest>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.guest_list, container, false);

        EditText guestSearch = (EditText) view.findViewById(R.id.guest_search);
        ListView guestList = (ListView) view.findViewById(R.id.guest_list);

        guestSearch.addTextChangedListener(new GuestSearchListener());

        getGuestsFromDatabase();

        guestListAdapter = new GuestListAdapter(getActivity(), guests);
        guestList.setAdapter(guestListAdapter);
        guestList.setOnItemClickListener(new GuestClickListener());

        updateGuestStatistics(view);

        return view;
    }

    private void getGuestsFromDatabase()
    {
        guestDao = ((DAOFactory) getActivity().getApplication()).getIndividualGuestDao();

        extractGuests();

        if (guests.size() < 1) {
            GuestFixture.hydrateDatabase(guestDao);
            extractGuests();
        }
    }

    private void extractGuests()
    {
        try {
            guests = guestDao.queryForAll();
        } catch (SQLException e) {
            Log.e("Database", "Erreur d'extraction des Invités : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateGuestStatistics(View view)
    {
        if (view == null) {
            view = getView();
        }

        TextView guestStatisticsTotal = (TextView) view.findViewById(R.id.guest_statistics_total);
        TextView guestStatisticsPresent = (TextView) view.findViewById(R.id.guest_statistics_present);
        TextView guestStatisticsNotPresent = (TextView) view.findViewById(R.id.guest_statistics_not_present);

        guestStatisticsTotal.setText("Invités : " + guests.size());
        guestStatisticsPresent.setText("Présents : " + getPresentGuests());
        guestStatisticsNotPresent.setText("Restant : " + (guests.size() - getPresentGuests()));
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
