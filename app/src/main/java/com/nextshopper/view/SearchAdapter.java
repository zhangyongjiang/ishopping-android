package com.nextshopper.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nextshopper.activity.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by siyiliu on 10/3/15.
 */
public class SearchAdapter extends BaseAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    ArrayList<String> list = new ArrayList<String>();
    private Set<Integer> sectionHeader = new HashSet<>();

    private LayoutInflater mInflater;

    public SearchAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addSectionHeader(String sec){
        list.add(sec);
        sectionHeader.add(list.size()-1);
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int rowType = getItemViewType(position);
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.search_line_view, null);
                    TextView leftView = (TextView) convertView.findViewById(R.id.search_line_left);
                    TextView middleView =(TextView)convertView.findViewById(R.id.search_line_middle);
                    TextView rightView =(TextView)convertView.findViewById(R.id.search_line_right);
                    String[] strs = list.get(position).split(",");
                    leftView.setText(strs[0]);
                    if(strs.length==1) {
                        middleView.setVisibility(View.GONE);
                        rightView.setVisibility(View.GONE);
                    }
                    if(strs.length==2) {
                        middleView.setText(strs[1]);
                        rightView.setVisibility(View.GONE);

                    }
                    if(strs.length==3) {
                        middleView.setText(strs[1]);
                        rightView.setText(strs[2]);
                    }
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.section_header, null);
                    TextView textView = (TextView) convertView.findViewById(R.id.section_header);
                    textView.setText(list.get(position));
                    break;
        }

        return convertView;
    }
}
