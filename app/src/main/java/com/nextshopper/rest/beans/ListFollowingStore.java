package com.nextshopper.rest.beans;

import java.util.List;

/**
 * Created by siyiliu on 9/13/15.
 */
public class ListFollowingStore extends ListWrapper<StoreFollowDetails> {
    public ListFollowingStore(List<StoreFollowDetails> items) {
        super(items);
    }
}
