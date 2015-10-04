package com.nextshopper.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextshopper.activity.R;
import com.nextshopper.common.Util;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.MessageDetailsList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MessageFragment extends SwipeFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void refresh() {
        ApiService.getService().MessageAPI_GetUserMessages(0, 5, new Callback<MessageDetailsList>() {
            @Override
            public void success(MessageDetailsList messageDetailsList, Response response) {
                if (messageDetailsList.total == 0)
                    getFragmentManager().beginTransaction().replace(R.id.message_container, new EmptyMsgFragment()).commit();
                else
                    getFragmentManager().beginTransaction().replace(R.id.message_container, new MsgListFragment()).commit();
            }

            @Override
            public void failure(RetrofitError error) {
                Util.alertBox(getActivity(), error);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view =  inflater.inflate(R.layout.fragment_message, container, false);
       // getFragmentManager().beginTransaction().add(R.id.message_container, new MsgListFragment()).commit();
        super.onCreateView(inflater, container, savedInstanceState);
        refresh();
        return view;
    }

}
