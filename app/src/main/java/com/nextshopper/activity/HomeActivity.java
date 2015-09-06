package com.nextshopper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.nextshopper.view.AboutFragment;
import com.nextshopper.view.CartFragment;
import com.nextshopper.view.ConfirmFragment;
import com.nextshopper.view.HomeFragment;
import com.nextshopper.view.MessageFragment;
import com.nextshopper.view.NavigationDrawerFragment;
import com.nextshopper.view.OrderHistoryFragment;
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
       String str =  getIntent().getStringExtra("fragment");
        if(str!=null)
            this.onNavigationDrawerItemSelected(0, str);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, String menuText) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(menuText))
                .addToBackStack(null)
                .commit();
    }


    public void imageLeftOnClick(View view){
        mNavigationDrawerFragment.getmDrawerLayout().openDrawer(Gravity.START);
    }

    public NavigationDrawerFragment getmNavigationDrawerFragment() {
        return mNavigationDrawerFragment;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public static Fragment newInstance(String menuText) {
         switch (menuText){
             case "Shop": return new HomeFragment();
             case "Search": return new SearchFragment();
             case "Shopping Cart": return new CartFragment();
             case "Settings": return new SettingsFragment();
             case "Message": return new MessageFragment();
             case "Order History": return new OrderHistoryFragment();
             case "About": return new AboutFragment();
             case "Confirm": return new ConfirmFragment();
             default:return new AboutFragment();
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
