package com.nextshopper.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.MessageDetails;
import com.nextshopper.rest.beans.MessageDetailsList;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by siyiliu on 9/13/15.
 */
public class FollowedStoreAdapter extends BaseAdapter implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener{
    private Context ctx;
    private List<MessageDetails> messageItemDetailsList = new ArrayList<>();
    private int start = 0;
    private int numOfItem = 20;
    private ListView listView;

    public FollowedStoreAdapter(Context ctx){
        this.ctx = ctx;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (lastVisibleItem == this.getCount()) {
            ApiService.getService().MessageAPI_GetUserMessages(start, numOfItem, new Callback<MessageDetailsList>() {
                @Override
                public void success(MessageDetailsList messageDetailsList, Response response) {
                    if (messageDetailsList.total == 0 && start == 0) {
                        //ctx.getFragmentManager().beginTransaction().replace(R.id.message_container, new EmptyMsgFragment());
                    } else {
                        //MessageAdapter.this.messageItemDetailsList.addAll(messageDetailsList.items);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            start = start + numOfItem;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       // OrderDetailsActivity.startActivity(ctx, getItem(position).message.id);
    }
}
