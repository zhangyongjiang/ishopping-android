package com.nextshopper.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nextshopper.activity.R;
import com.nextshopper.activity.SearchResultActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements View.OnClickListener{
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private ViewPager homeViewPager;
    private SearchFragmentAdapter searchFragmentAdapter;
    private EditText keywords;
    private Button submitButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        homeViewPager = (ViewPager)view.findViewById(R.id.search_viewpager);
        keywords =(EditText) view.findViewById(R.id.home_search_edittext);
        submitButton = (Button)view.findViewById(R.id.search_button);
        submitButton.setOnClickListener(this);
        fragmentList.add(WomanFragment.newInstance());
       // fragmentList.add(ManFragment.newInstance("Man"));
        searchFragmentAdapter = new SearchFragmentAdapter(getFragmentManager(), fragmentList);
        homeViewPager.setAdapter(searchFragmentAdapter);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.search_tab_strip);
        pagerTabStrip.setDrawFullUnderline(false);
        pagerTabStrip.setTabIndicatorColor(Color.RED);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
        intent.putExtra("keywords", keywords.getText().toString());
        startActivity(intent);
    }
}
