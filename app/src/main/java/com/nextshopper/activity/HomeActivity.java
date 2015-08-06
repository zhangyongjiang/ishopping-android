package com.nextshopper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nextshopper.view.CartFragment;
import com.nextshopper.view.HomeFragment;
import com.nextshopper.view.MessageFragment;
import com.nextshopper.view.NavigationDrawerFragment;
import com.nextshopper.view.SearchFragment;
import com.nextshopper.view.SettingsFragment;


public class HomeActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_home);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void imageLeftOnClick(View view){
        mNavigationDrawerFragment.getmDrawerLayout().openDrawer(Gravity.START);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public static Fragment newInstance(int sectionNumber) {
         switch (sectionNumber){
             case 1: return new HomeFragment();
             case 2: return new SearchFragment();
             case 3: return new CartFragment();
             case 4: return new SettingsFragment();
             case 5: return new MessageFragment();
             default:return new MessageFragment();
         }
        }

    }

    public void catOnClick(View view){
        Intent intent = new Intent(this, SearchResultActivity.class);
        TextView textView = (TextView) view;
        intent.putExtra("cat",textView.getText().toString());
        startActivity(intent);
    }

}
