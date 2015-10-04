package com.nextshopper.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nextshopper.activity.R;

import java.util.Arrays;


public class WomanFragment extends Fragment {
    private ListView listview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_listview, container, false);
        listview =(ListView)view.findViewById(R.id.search_listview);
        SearchAdapter searchAdapter = new SearchAdapter(getActivity());
        String headers[] = getString(R.string.section_header).split(";");
        String women_cloth[] = getString(R.string.women_cloth).split(";");
        String men_cloth[] = getString(R.string.men_cloth).split(";");
        String health[]=getString(R.string.health).split(";");
        String shoes[]=getString(R.string.shoes).split(";");
        String sports[] =getString(R.string.sports).split(";");
        String comsumers[] = getString(R.string.comsumer).split(";");
        for(int i =0; i<headers.length; i++){
            searchAdapter.addSectionHeader(headers[i]);
            switch (i) {
                case 0: searchAdapter.list.addAll(Arrays.asList(women_cloth)); break;
                case 1: searchAdapter.list.addAll(Arrays.asList(men_cloth));break;
                case 2: searchAdapter.list.addAll(Arrays.asList(health));break;
                case 3:searchAdapter.list.addAll(Arrays.asList(shoes));break;
                case 4:searchAdapter.list.addAll(Arrays.asList(sports)); break;
                case 5: searchAdapter.list.addAll(Arrays.asList(comsumers));break;

            }
        }
        listview.setAdapter(searchAdapter);
        return view;
    }

}
