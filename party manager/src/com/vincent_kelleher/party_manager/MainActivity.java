package com.vincent_kelleher.party_manager;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity
{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle("Party Manager");
        setContentView(R.layout.main);
    }
}
