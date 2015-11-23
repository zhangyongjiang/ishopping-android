package com.nextshopper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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


public class HomeActivity extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String menuText;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_home);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
       String str =  getIntent().getStringExtra("fragment");
        if(str!=null)
            this.onNavigationDrawerItemSelected(0, str);
        mTitle = mDrawerTitle = getTitle();


       // getActionBar().setDisplayHomeAsUpEnabled(true);
       // getActionBar().setHomeButtonEnabled(true);
       // mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
       // mDrawerToggle = new ActionBarDrawerToggle(
       //         this,                  /* host Activity */
       //        mDrawerLayout,         /* DrawerLayout object */
       //         R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
       //         R.string.drawer_open,  /* "open drawer" description for accessibility */
       //        R.string.drawer_close  /* "close drawer" description for accessibility */
       //) {
        /*    @Override
            public void onDrawerClosed(View view) {
       //         getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            @Override
            public void onDrawerOpened(View drawerView) {
         //       getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);*/
        mNavigationDrawerFragment.getmDrawerLayout().closeDrawer(Gravity.START);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, String menuText) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        this.menuText = menuText;
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.leave_blank)
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

    public void rightOnClick(View view){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if(fragment instanceof CartFragment) {
            Intent intent = new Intent(this, ShippingActivity.class);
            intent.putExtra("source", "checkout");
            startActivity(intent);
        } else if(fragment instanceof HomeFragment){
            //getSupportFragmentManager().beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance("Search")).commit();
            SearchActivity.startActivity(this);
        }
    }

}
