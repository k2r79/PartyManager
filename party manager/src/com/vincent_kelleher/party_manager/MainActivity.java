package com.vincent_kelleher.party_manager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.WindowManager;
import android.widget.SearchView;

public class MainActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setTitle("Party Manager");
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.icon);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchListener());

        return super.onCreateOptionsMenu(menu);
    }

    private class SearchListener implements SearchView.OnQueryTextListener
    {
        @Override
        public boolean onQueryTextSubmit(String query)
        {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String searchString)
        {
            GuestListFragment guestListFragment = (GuestListFragment) getFragmentManager().findFragmentById(R.id.guest_list_fragment);
            guestListFragment.getGuestListAdapter().getFilter().filter(searchString);

            return true;
        }
    }
}
