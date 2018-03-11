package com.android.project1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.os.Parcelable.CONTENTS_FILE_DESCRIPTOR;
import static com.android.project1.Config.URL_CATEGORY;
import static com.android.project1.Config.URL_PRODUCT;

/**
 * Created by Gaurav on 22-06-2017.
 */

public class ProductListFragment extends Fragment {


    private String category;

    private ArrayList<Product> products = new ArrayList<>();

    ProgressDialog loading;
    private RecyclerView recyclerView;
    private GridView gridView;

    private GridViewAdapter mGridAdapter;
    private ProgressBar mProgressBar;


    public ProductListFragment(String s) {
        this.category = s;
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      /*   recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new GetProducts().execute();

        return recyclerView;*/

        View v = inflater.inflate(R.layout.gridview, container, false);
        gridView = (GridView) v.findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        new GetProducts().execute();

        mProgressBar.setVisibility(View.VISIBLE);


        return v;

    }

  /*  public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public TextView price;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            price = (TextView) itemView.findViewById(R.id.product_price);



            // Adding Snackbar to Action Button inside card





            ImageButton shareImageButton = (ImageButton) itemView.findViewById(R.id.share_button);
            shareImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Share article",
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }

*/


    /**
     * Adapter to display recycler view.
     */
   /* public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private Context context;
        private LayoutInflater inflater;
        ArrayList<Product> products;
        Product current;

        // create constructor to innitilize context and data sent from MainActivity
        public ContentAdapter(Context context, ArrayList<Product> data){
            this.context=context;
            inflater= LayoutInflater.from(context);
            this.products=data;
        }

        // Set numbers of Card in RecyclerView.
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ViewHolder myHolder =  holder;
            current= products.get(position);
            myHolder.name.setText(current.getTitle());
            myHolder.price.setText(current.getPrice());
            myHolder.description.setText(current.getDesc());
            Picasso.with(context).load(current.getImage()).into(myHolder.picture);



        }

        @Override
        public int getItemCount() {
            return products.size();
        }


    }*/


        class GetProducts extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showProducts(s);
                mGridAdapter = new GridViewAdapter(getContext(),R.layout.item_card,products);
                gridView.setAdapter(mGridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Product item =(Product) parent.getItemAtPosition(position);
                        String productId = item.getId();
                        Intent i = new Intent(getContext(),DetailActivity.class);
                        i.putExtra("productId",productId);
                        startActivity(i);


                    }
                });
                mProgressBar.setVisibility(View.GONE);

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_PRODUCT,category);
                Log.d("test",s);
                return s;
            }
        }



    private void showProducts(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            for(int i=0;i<result.length();i++) {
                JSONObject c = result.getJSONObject(i);
                String id = c.getString(Config.TAG_PRODUCT_ID);
                String name = c.getString(Config.TAG_PRODUCT_NAME);
                String image = c.getString(Config.TAG__PRODUCT_IMAGE);
                String desc = c.getString(Config.TAG_PRODUCT_DESC);
                String price = c.getString(Config.TAG_PRODUCT_PRICE);
                Product product = new Product();
                product.setPrice("Rs. "+price);
                product.setImage(image);
                product.setTitle(name);
                product.setDesc(desc);
                product.setId(id);
                products.add(product);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class GridViewAdapter extends ArrayAdapter<Product> {

        //private final ColorMatrixColorFilter grayscaleFilter;
        private Context mContext;
        private int layoutResourceId;
        private ArrayList<Product> products = new ArrayList<>();

        public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<Product> mGridData) {
            super(mContext, layoutResourceId, mGridData);
            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;
            this.products = mGridData;
        }




        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            final ViewHolder holder;
            final Product item = products.get(position);


            if (row == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.productTitle = (TextView) row.findViewById(R.id.card_title);
                holder.productPrice = (TextView) row.findViewById(R.id.product_price);
                holder.productDescription = (TextView) row.findViewById(R.id.card_text);
                holder.imageView = (ImageView) row.findViewById(R.id.card_image);
                holder.wishList = (ImageView)row.findViewById(R.id.wishlist_button);
                Log.d("clickTest","All Items Recognised");

                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            holder.productTitle.setText(Html.fromHtml(item.getTitle()));
            holder.productPrice.setText(Html.fromHtml(item.getPrice()));
            holder.productDescription.setText(Html.fromHtml(item.getDesc()));
            Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
            holder.wishList.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d("cLickTest","Wishlist Image CLicked");

                    if(!alreadyInWishlist(item)){
                    MyWishList.addToMyWishList(item);
                    Toast.makeText(getActivity(),"Item Added To WishList",Toast.LENGTH_SHORT).show();
                        holder.wishList.setImageResource(R.drawable.ic_favorite_black_18dp);
                     }
                    else{
                        MyWishList.removeFromMyCart(item);
                        Toast.makeText(getActivity(),"Item Removed From WishList",Toast.LENGTH_SHORT).show();
                    holder.wishList.setImageResource(R.drawable.ic_favorite_border_black_18dp);}
                }
                private boolean alreadyInWishlist(Product item){
                    for(int i=0;i<MyWishList.myWishlist.size();i++)
                        if(item==MyWishList.myWishlist.get(i)) {
                            return true;
                        }
                            return false;

            }});



            return row;

        }


        class ViewHolder {
            TextView productTitle,productPrice,productDescription;
            ImageView imageView;
             ImageView wishList;
                    }
    }


}






