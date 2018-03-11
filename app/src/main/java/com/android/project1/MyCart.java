package com.android.project1;

import java.util.ArrayList;

/**
 * Created by Gaurav on 06-07-2017.
 */

public class MyCart {
    public static ArrayList<Product> myCart = new ArrayList<>();
    public static int totalPrice;

    public static void addToMyCart(Product selectedProduct) {
        MyCart.myCart.add(selectedProduct);
        totalPrice+=Integer.parseInt(selectedProduct.getPrice());

    }
    public static void removeFromMyCart(Product selectedProduct) {
        MyCart.myCart.remove(selectedProduct);
        totalPrice-=Integer.parseInt(selectedProduct.getPrice());

    }


}
