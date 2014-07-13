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
import com.j256.ormlite.stmt.QueryBuilder;
import com.vincent_kelleher.party_manager.entities.Guest;
import com.vincent_kelleher.party_manager.sqlite.DAOFactory;
import com.vincent_kelleher.party_manager.sqlite.GuestFixture;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GuestListFragment extends Fragment
{
    private Dao<Guest, Integer> guestDao;
    private GuestListAdapter guestListAdapter;
    private List<Guest> guests = new ArrayList<Guest>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.guest_list, container, false);

        EditText guestSearch = (EditText) view.findViewById(R.id.guest_search);
        ListView guestList = (ListView) view.findViewById(R.id.guest_list);

        guestSearch.addTextChangedListener(new GuestSearchListener());

        getGuestsFromDatabase();

        guestListAdapter = new GuestListAdapter(getActivity(), guests);
        sortGuestList();
        guestList.setAdapter(guestListAdapter);
        guestList.setOnItemClickListener(new GuestClickListener());

        updateGuestStatistics(view);

        return view;
    }

    private void sortGuestList()
    {
        guestListAdapter.sort(new Comparator()
        {
            @Override
            public int compare(Object e1, Object e2)
            {
                return e1.toString().compareTo(e2.toString());
            }
        });
    }

    private void getGuestsFromDatabase()
    {
        guestDao = ((DAOFactory) getActivity().getApplication()).getGuestDao();

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

        int totalGuests = getTotalGuests();
        int presentGuests = getPresentGuests();

        guestStatisticsTotal.setText("Total : " + totalGuests);
        guestStatisticsPresent.setText("Présents : " + presentGuests);
        guestStatisticsNotPresent.setText("Restant : " + (totalGuests - presentGuests));
    }

    private int getTotalGuests()
    {
        int number = 0;
        try {
            QueryBuilder<Guest, Integer> query = guestDao.queryBuilder().selectRaw("SUM(headcount)");
            number = (int) guestDao.queryRawValue(query.prepareStatementString());
        } catch (SQLException e) {
            Log.e("Database", "Error getting Guest count : " + e.getMessage());
        }

        return number;
    }

    private int getPresentGuests()
    {
        int number = 0;
        try {
            QueryBuilder<Guest, Integer> query = guestDao.queryBuilder().selectRaw("SUM(headcount)");
            query.where().eq("present", true);
            number = (int) guestDao.queryRawValue(query.prepareStatementString());
        } catch (SQLException e) {
            Log.e("Database", "Error getting Guest count : " + e.getMessage());
        }

        return number;
    }

    public GuestListAdapter getGuestListAdapter()
    {
        return guestListAdapter;
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
            view.setSelected(true);

            GuestDetailsFragment guestDetailsFragment = (GuestDetailsFragment) getFragmentManager().findFragmentById(R.id.guest_details_fragment);
            guestDetailsFragment.updateGuest((Guest) guestListAdapter.getItem(position));
        }
    }
}
