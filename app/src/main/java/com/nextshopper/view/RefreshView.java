package com.nextshopper.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Zhang_Kevin on 7/5/15.
 */
public class RefreshView extends SwipeRefreshLayout {
    public RefreshView(Context ctx) {
        super(ctx);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
}
