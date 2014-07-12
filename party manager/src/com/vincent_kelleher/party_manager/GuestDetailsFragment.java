package com.vincent_kelleher.party_manager;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.j256.ormlite.dao.Dao;
import com.vincent_kelleher.party_manager.entities.Guest;
import com.vincent_kelleher.party_manager.sqlite.DAOFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class GuestDetailsFragment extends Fragment
{
    private Guest guest;
    private Dao<Guest, Integer> guestDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.guest_details, container, false);
        view.setAlpha(0);

        guestDao = ((DAOFactory) getActivity().getApplication()).getGuestDao();

        return view;
    }

    public void updateGuest(Guest guest)
    {
        this.guest = guest;

        getView().setAlpha(1);

        ImageView guestImage = (ImageView) getView().findViewById(R.id.guest_details_image);
        guestImage.setOnClickListener(new GuestImageListener(guest));
        if (guest.getImagePath() != null) {
            Bitmap photoBitmap = BitmapFactory.decodeFile(guest.getImagePath());
            guestImage.setImageBitmap(photoBitmap);
        } else {
            guestImage.setImageResource(R.drawable.unknown_guest);
        }

        TextView guestName = (TextView) getView().findViewById(R.id.guest_details_name);
        TextView guestPhone = (TextView) getView().findViewById(R.id.guest_details_phone);
        Switch guestPresent = (Switch) getView().findViewById(R.id.guest_details_present);
        SeekBar guestHeadcount = (SeekBar) getView().findViewById(R.id.guest_details_headcount);
        TextView guestHeadcountValue = (TextView) getView().findViewById(R.id.guest_details_headcount_value);

        guestName.setText(guest.toString());
        guestPhone.setText(guest.getPhone());
        guestPresent.setChecked(guest.isPresent());

        guestHeadcount.setProgress(guest.getHeadcount() * 10);
        guestHeadcount.setOnSeekBarChangeListener(new GuestHeadcountListener());
        guestHeadcountValue.setText(String.valueOf(guest.getHeadcount()) + " personnes");

        guestPresent.setOnCheckedChangeListener(new GuestPresentListener());
    }

    private class GuestImageListener implements View.OnClickListener
    {
        private Guest guest;

        private GuestImageListener(Guest guest)
        {
            this.guest = guest;
        }

        @Override
        public void onClick(View v)
        {
            File photo = takeGuestPhoto();

            guest.setImagePath(photo.getAbsolutePath());
            try {
                guestDao.update(guest);
            } catch (SQLException e) {
                Log.e("Database", "Erreur de mise à jour de l'Invité : " + e.getMessage());
            }

            Bitmap photoBitmap = BitmapFactory.decodeFile(guest.getImagePath());
            ((ImageView) v).setImageBitmap(photoBitmap);
        }

        private File takeGuestPhoto()
        {
            PackageManager packageManager = getActivity().getPackageManager();
            File photo = null;

            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(packageManager) != null) {
                    try {
                        photo = createImageFile();
                    } catch (IOException e) {
                        Log.e("Image", "Erreur de la capture d'image : " + e.getMessage());
                    }

                    if (photo != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                        startActivityForResult(takePictureIntent, 1);
                    }
                }
            }

            return photo;
        }

        private File createImageFile() throws IOException
        {
            String imageFileName = "JPEG_" + guest.getName() + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(imageFileName, ".jpg", storageDir);

            Log.d("Image", "Image sauvegardée : " + image.getAbsolutePath());

            return image;
        }
    }

    private class GuestPresentListener implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            guest.setPresent(isChecked);

            try {
                guestDao.update(guest);
            } catch (SQLException e) {
                Log.e("Database", "Erreur de mise à jour de l'Invité : " + e.getMessage());
                e.printStackTrace();
            }

            updateGuestStatistics();
        }
    }

    private class GuestHeadcountListener implements SeekBar.OnSeekBarChangeListener
    {

        private int headcount;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            headcount = (int) (progress / 10);

            TextView guestHeadcountValue = (TextView) getView().findViewById(R.id.guest_details_headcount_value);
            guestHeadcountValue.setText(String.valueOf(headcount) + " personnes");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            guest.setHeadcount(headcount);

            try {
                guestDao.update(guest);
            } catch (SQLException e) {
                Log.e("Database", "Erreur de mise à jour de l'Invité : " + e.getMessage());
                e.printStackTrace();
            }

            updateGuestStatistics();
        }

    }

    private void updateGuestStatistics()
    {
        GuestListFragment guestListFragment = (GuestListFragment) getFragmentManager().findFragmentById(R.id.guest_list_fragment);
        guestListFragment.updateGuestStatistics(null);
    }
}
