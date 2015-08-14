package com.nextshopper.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.nextshopper.activity.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private ViewPager homeViewPager;
    private SearchFragmentAdapter searchFragmentAdapter;
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        homeViewPager = (ViewPager)view.findViewById(R.id.search_viewpager);
        searchView = (SearchView) view.findViewById(R.id.home_search_input);
        searchView.setSubmitButtonEnabled(true);

        fragmentList.add(WomanFragment.newInstance());
       // fragmentList.add(ManFragment.newInstance("Man"));
        searchFragmentAdapter = new SearchFragmentAdapter(getFragmentManager(), fragmentList);
        homeViewPager.setAdapter(searchFragmentAdapter);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.search_tab_strip);
        pagerTabStrip.setDrawFullUnderline(false);
        pagerTabStrip.setTabIndicatorColor(Color.RED);
        return view;
    }

}
