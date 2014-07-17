package com.vincent_kelleher.party_manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.vincent_kelleher.party_manager.bitmap.BitmapUtils;
import com.vincent_kelleher.party_manager.bitmap.RoundedImageView;
import com.vincent_kelleher.party_manager.entities.Guest;

import java.util.List;

public class GuestSpinnerAdapter extends ArrayAdapter
{
    private List<Guest> guests;
    private RoundedImageView guestImage;

    public GuestSpinnerAdapter(Context context, List<Guest> guests)
    {
        super(context, R.layout.guest_list_row, guests);

        this.guests = guests;
    }

    @Override
    public int getCount()
    {
        return guests.size();
    }

    @Override
    public Object getItem(int position)
    {
        return guests.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.guest_list_row, null);

        guestImage = (RoundedImageView) view.findViewById(R.id.guest_image);
        TextView guestName = (TextView) view.findViewById(R.id.guest_name);
        TextView guestHeadcount = (TextView) view.findViewById(R.id.guest_headcount);

        Guest guest = guests.get(position);

        updateGuestImage(guest);

        if (guest.isPresent()) {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.guest_selected));
        } else {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.guest_default));
        }

        guestName.setText(guest.toString());
        guestHeadcount.setText(guest.getHeadcount() + " personnes");

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View view = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.guest_list_row, null);

        guestImage = (RoundedImageView) view.findViewById(R.id.guest_image);
        TextView guestName = (TextView) view.findViewById(R.id.guest_name);
        TextView guestHeadcount = (TextView) view.findViewById(R.id.guest_headcount);

        Guest guest = guests.get(position);

        updateGuestImage(guest);

        if (guest.isPresent()) {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.guest_selected));
        } else {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.guest_default));
        }

        guestName.setText(guest.toString());
        guestHeadcount.setText(guest.getHeadcount() + " personnes");

        return view;
    }

    public void updateGuestImage(Guest guest)
    {
        if (guest.getImagePath() != null) {
            Bitmap photoBitmap = BitmapUtils.compressImage(guest.getImagePath(), 300, 300);
            guestImage.setImageBitmap(photoBitmap);
        } else {
            guestImage.setImageResource(R.drawable.unknown_guest);
        }
    }
}
