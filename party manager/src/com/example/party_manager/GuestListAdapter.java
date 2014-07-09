package com.example.party_manager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.party_manager.entities.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestListAdapter extends ArrayAdapter
{
    private List<Guest> guests;
    private List<Guest> displayedGuests;

    public GuestListAdapter(Context context, List guests)
    {
        super(context, R.layout.guest_list_row, guests);

        this.guests = guests;
        this.displayedGuests = guests;
    }

    @Override
    public int getCount()
    {
        return displayedGuests.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.guest_list_row, null);

        ImageView guestImage = (ImageView) view.findViewById(R.id.guest_image);
        TextView guestName = (TextView) view.findViewById(R.id.guest_name);

        Guest guest = displayedGuests.get(position);

        guestName.setText(guest.toString());

        return view;
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                FilterResults filterResults = new FilterResults();
                List<Guest> filteredArrayList = new ArrayList<Guest>();

                for (Guest guest : guests) {
                    if (guest.toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredArrayList.add(guest);
                    }
                }

                filterResults.count = filteredArrayList.size();
                filterResults.values = filteredArrayList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                displayedGuests = (ArrayList<Guest>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
