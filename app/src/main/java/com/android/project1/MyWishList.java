package com.android.project1;

import java.util.ArrayList;

/**
 * Created by Gaurav on 07-07-2017.
 */

public class MyWishList {
    public static ArrayList<Product> myWishlist = new ArrayList<>();

    public static void addToMyWishList(Product selectedProduct) {
        myWishlist.add(selectedProduct);
    }
    public static void removeFromMyCart(Product selectedProduct) {
       myWishlist.remove(selectedProduct);
    }
}
