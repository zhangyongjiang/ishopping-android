package com.nextshopper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.nextshopper.view.SearchFragment;
import com.nextshopper.view.TrendingFragment;

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        View view = findViewById(R.id.search_frameview);
        getSupportFragmentManager().beginTransaction().add(R.id.search_frameview, SearchFragment.newInstance(true)).commit();
        InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);

    }

    public void catOnClick(View view){
        Intent intent = new Intent(this, SearchResultActivity.class);
        TextView textView = (TextView) view;
        intent.putExtra("cat",textView.getText().toString());
        startActivity(intent);
    }


}
