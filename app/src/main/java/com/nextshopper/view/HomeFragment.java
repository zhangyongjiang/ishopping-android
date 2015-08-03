package com.nextshopper.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextshopper.activity.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager homeViewPager;
    private FragmentAdapter fragmentAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewPager = (ViewPager)view.findViewById(R.id.id_home_viewpager);
        fragmentList.add(TrendingFragment.newInstance("Trending", null, null));
        fragmentList.add(TrendingFragment.newInstance("Newest", null, null));
        fragmentList.add(TrendingFragment.newInstance("Just For You", null, null));
        fragmentAdapter = new FragmentAdapter(getFragmentManager(), fragmentList);
        homeViewPager.setAdapter(fragmentAdapter);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.home_tab_strip);
        pagerTabStrip.setDrawFullUnderline(false);
        pagerTabStrip.setTabIndicatorColor(Color.RED);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
