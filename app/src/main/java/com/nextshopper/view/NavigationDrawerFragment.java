package com.nextshopper.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nextshopper.activity.MainActivity;
import com.nextshopper.activity.R;
import com.nextshopper.common.Constant;

import java.io.File;

import static android.widget.AdapterView.OnItemClickListener;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements View.OnClickListener {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    private View topView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private ListView menuListView;
    private boolean login;
    private static final String ARG_PARAM = "param";

    public NavigationDrawerFragment() {
    }

    public static NavigationDrawerFragment newIntance(String str){
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, str);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition, "Shop");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout  layout= (RelativeLayout) inflater.inflate(R.layout.fragment_navigation_drawer_bakee, container, false);
        ListView listview = (ListView)layout.findViewById(R.id.menu_listview);
        topView = layout.findViewById(R.id.top);
        topView.setOnClickListener(this);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position, ((TextView) view).getText().toString());
            }
        });

        if (getActivity().getIntent().getStringExtra(Constant.USER_ID) != null) {
            listview.setAdapter(new MenuAdapter(getString(R.string.login_menu).split(","),getActivity()));
            TextView userTextView = (TextView) layout.findViewById(R.id.menu_user);
            SharedPreferences pref = getActivity().getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
            userTextView.setText(pref.getString(Constant.FIRST_NAME, "") + " " + pref.getString(Constant.LAST_NAME, ""));
            ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
            File imageFile = new File(cw.getDir("imageDir", Context.MODE_PRIVATE), Constant.THUMNAIL);
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            Drawable drawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 240, 240, true));
            userTextView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }
        else{
            listview.setAdapter(new MenuAdapter(getString(R.string.guest_menu).split(","), getActivity()));
        }
        return layout;
    }

    @Override
    public void onClick(View v) {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (getActivity().getIntent().getStringExtra(Constant.USER_ID) != null) {
            mCallbacks.onNavigationDrawerItemSelected(0,"Settings");
        }
        else{
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }
    }

    private void selectItem(int position, String menuText) {
        mCurrentSelectedPosition = position;

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position, menuText);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position, String menuText);
    }

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

}
