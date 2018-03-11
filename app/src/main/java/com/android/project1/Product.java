package com.android.project1;

/**
 * Created by Gaurav on 03-07-2017.
 */

public class Product {

    private String image;
    private String title;
    private String price;
    private String desc;
    private String id;

    public Product() {
        super();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice(){return price;}

    public void setPrice(String price){this.price=price;}

    public String getDesc(){return desc;}

    public void setDesc(String desc){this.desc=desc;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


