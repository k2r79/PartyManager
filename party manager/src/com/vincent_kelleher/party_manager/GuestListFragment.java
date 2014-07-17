package com.vincent_kelleher.party_manager;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vincent_kelleher.party_manager.entities.Guest;
import com.vincent_kelleher.party_manager.entities.Room;
import com.vincent_kelleher.party_manager.sqlite.DAOFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GuestListFragment extends Fragment
{
    private Dao<Guest, Integer> guestDao;
    private Dao<Room, Integer> roomDao;
    private GuestListAdapter guestListAdapter;
    private List<Guest> guests = new ArrayList<Guest>();
    private View mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mainView = inflater.inflate(R.layout.guest_list, container, false);

        ListView guestList = (ListView) mainView.findViewById(R.id.guest_list);

        guestDao = ((DAOFactory) getActivity().getApplication()).getGuestDao();
        roomDao = ((DAOFactory) getActivity().getApplication()).getRoomDao();
        getGuestsFromDatabase();

        guestListAdapter = new GuestListAdapter(getActivity(), guests);
        sortGuestList();
        guestList.setAdapter(guestListAdapter);
        guestList.setOnItemClickListener(new GuestClickListener());
        guestList.setOnItemLongClickListener(new GuestLongClickListener());

        updateGuestStatistics(mainView);

        return mainView;
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
        extractGuests();
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

    private class GuestLongClickListener implements AdapterView.OnItemLongClickListener
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View dialogView = layoutInflater.inflate(R.layout.guest_deletion_dialog, null);

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setView(dialogView);

            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Guest guest = (Guest) guestListAdapter.getItem(position);

                    if (guest.getRoom() != null) {
                        Room room = guest.getRoom();
                        room.setGuest(null);

                        try {
                            roomDao.update(room);
                        } catch (SQLException e) {
                            Log.e("Database", "Erreur de modification de la Chambre : " + e.getMessage());
                        }
                    }

                    try {
                        guestDao.delete(guest);
                    } catch (SQLException e) {
                        Log.e("Database", "Erreur de suppression : " + e.getMessage());
                    }

                    guestListAdapter.remove(guest);
                    guestListAdapter.notifyDataSetChanged();
                    updateGuestStatistics(mainView);
                }
            });
            alertDialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            return true;
        }
    }
}
