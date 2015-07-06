package com.nextshopper.view;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by Zhang_Kevin on 7/5/15.
 */
public class BaseView extends RelativeLayout {
    public BaseView(Context ctx) {
        super(ctx);

        RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
    }
}
