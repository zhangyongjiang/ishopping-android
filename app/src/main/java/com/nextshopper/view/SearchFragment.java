package com.nextshopper.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nextshopper.activity.R;
import com.nextshopper.activity.SearchResultActivity;

public class SearchFragment extends Fragment implements View.OnClickListener{
    //private List<Fragment> fragmentList = new ArrayList<Fragment>();
    //private ViewPager homeViewPager;
    //private SearchFragmentAdapter searchFragmentAdapter;
    private EditText keywords;
    private Button submitButton;
    private TitleView titleView;
    private static final String ARG_PARAM1 = "param";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
       // homeViewPager = (ViewPager)view.findViewById(R.id.search_viewpager);
        keywords =(EditText) view.findViewById(R.id.home_search_edittext);
        titleView = (TitleView) view.findViewById(R.id.home_search);
        if(getArguments()!=null&&getArguments().getBoolean(ARG_PARAM1)){
            titleView.getImageLeft().setImageResource(R.drawable.back);
        }
        submitButton = (Button)view.findViewById(R.id.search_button);
        submitButton.setOnClickListener(this);
       // fragmentList.add(WomanFragment.newInstance());
       // fragmentList.add(ManFragment.newInstance("Man"));
        //searchFragmentAdapter = new SearchFragmentAdapter(getFragmentManager(), fragmentList);
        //homeViewPager.setAdapter(searchFragmentAdapter);
        //PagerTabStrip pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.search_tab_strip);
        //pagerTabStrip.setDrawFullUnderline(false);
        //pagerTabStrip.setTabIndicatorColor(Color.RED);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
        intent.putExtra("keywords", keywords.getText().toString());
        startActivity(intent);
    }

    public static SearchFragment newInstance(boolean back){
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1,back);
        searchFragment.setArguments(args);
        return searchFragment;
    }

}
