package com.android.project1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project1.notification.NotificationCountSetClass;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {


    private SimpleDraweeView mImageView;
    private TextView productName,productPrice,productDesc,textViewAddToCart ;
    private LinearLayout wishList;
    private ImageView wishlistIcon;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);
        setContentView(R.layout.activity_item_details);

        new GetAccount().execute();



       // Set Collapsing Toolbar layout to the screen
       /* CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();
        String[] places = resources.getStringArray(R.array.places);
        collapsingToolbar.setTitle(places[postion % places.length]);

        String[] placeDetails = resources.getStringArray(R.array.place_details);
        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText(placeDetails[postion % placeDetails.length]);

        String[] placeLocations = resources.getStringArray(R.array.place_locations);
        TextView placeLocation =  (TextView) findViewById(R.id.place_location);
        placeLocation.setText(placeLocations[postion % placeLocations.length]);

        TypedArray placePictures = resources.obtainTypedArray(R.array.places_picture);
        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        placePicutre.setImageDrawable(placePictures.getDrawable(postion % placePictures.length()));

        placePictures.recycle();*/

    }

    class GetAccount extends AsyncTask<Void, Void, String> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(DetailActivity.this, "Fetching...", "Wait...", false, false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            String json = s;
            try {
                JSONObject obj = new JSONObject(json);
                JSONArray result = obj.getJSONArray(Config.TAG_JSON_ARRAY);
                JSONObject c = result.getJSONObject(0);
                final String id = c.getString(Config.TAG_ID);
                final String productname = c.getString(Config.TAG_PRODUCT_NAME);
                final String productprice = c.getString(Config.TAG_PRODUCT_PRICE);
                final String productdesc = c.getString(Config.TAG_PRODUCT_DESC);
                final String productimage=c.getString(Config.TAG__PRODUCT_IMAGE);

                Product product= new Product();

                product.setImage(productimage);
                    product.setTitle(productname);
                    product.setDesc(productdesc);
                    product.setPrice(productprice);
                    Log.d("product details",product.getTitle()+" "+product.getDesc());

                    showProduct(getApplicationContext(),product);



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... v) {
            Intent i = getIntent();
            String productId = i.getStringExtra("productId").trim();
            Log.d("productId",productId);

            RequestHandler rh = new RequestHandler();
            String s = rh.sendGetRequestParam(Config.URL_GET_PRODUCT,productId);
            Log.d("test",s);
            return s;
        }
    }

    public void showProduct(Context mC,Product product){

        mImageView = (SimpleDraweeView)findViewById(R.id.image1);
        productName = (TextView) findViewById(R.id.product_title);
        productPrice = (TextView) findViewById(R.id.product_price);
        productDesc=(TextView) findViewById(R.id.product_desc);
        textViewAddToCart= (TextView)findViewById(R.id.text_action_add_to_cart);
        wishList=(LinearLayout) findViewById(R.id.wishlist_product_detail);

        final Product currentProduct = product;
        productName.setText(product.getTitle());
        productPrice.setText("Rs. "+product.getPrice());
        productDesc.setText(product.getDesc());
        Picasso.with(mC).load(product.getImage()).into(mImageView);
        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(DetailActivity.this,"Item added to cart.",Toast.LENGTH_SHORT).show();
                HomeActivity.notificationCountCart++;
                MyCart.addToMyCart(currentProduct);
                Log.d("Cart Size",""+MyCart.myCart.size()+"name "+MyCart.myCart.get(0).getTitle());

                NotificationCountSetClass.setNotifyCount(HomeActivity.notificationCountCart);
            }
        });
        wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyWishList.addToMyWishList(currentProduct);
                Toast.makeText(DetailActivity.this,"Item Added To WishList",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

