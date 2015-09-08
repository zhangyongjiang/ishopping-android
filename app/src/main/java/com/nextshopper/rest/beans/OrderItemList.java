package com.nextshopper.rest.beans;

import java.util.List;

/**
 * Created by siyiliu on 9/7/15.
 */
public class OrderItemList extends ListWrapper <OrderItemDetails>{
    public OrderItemList(List<OrderItemDetails> items) {
        super(items);
    }
}
