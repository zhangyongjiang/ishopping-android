package com.nextshopper.rest.beans;

import java.util.List;

/**
 * Created by siyiliu on 9/12/15.
 */
public class ListFavoriteDetails extends ListWrapper<FavoriteDetails>{
    public ListFavoriteDetails(List<FavoriteDetails> items) {
        super(items);
    }
}
