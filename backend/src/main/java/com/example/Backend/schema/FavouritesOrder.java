package com.example.Backend.schema;

import lombok.Data;

import java.util.List;

@Data
public class FavouritesOrder {
    private List<FavouriteOrder> favouriteOrders;
}
