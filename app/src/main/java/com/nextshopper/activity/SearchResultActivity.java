package com.nextshopper.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nextshopper.view.TitleView;
import com.nextshopper.view.TrendingFragment;


public class SearchResultActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        String cat  = getIntent().getStringExtra("cat");
        String keywords = getIntent().getStringExtra("keywords");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            keywords = intent.getStringExtra(SearchManager.QUERY);

        }
        TitleView titleView = (TitleView)findViewById(R.id.cat_search);
        titleView.setTextMiddle(cat);
        getSupportFragmentManager().beginTransaction().add(R.id.search_result_gridview, TrendingFragment.newInstance("", cat, keywords)).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void imageLeftOnClick(View view) {
        finish();
    }
}
