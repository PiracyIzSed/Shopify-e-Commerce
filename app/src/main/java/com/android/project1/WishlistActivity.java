package com.android.project1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {


        private static Context mContext;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            Fresco.initialize(this);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_wishlist);

            mContext = WishlistActivity.this;



            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

            recyclerView.setLayoutManager(recylerViewLayoutManager);

            setCartLayout();


            recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(MyWishList.myWishlist));

        }

        public static class SimpleStringRecyclerViewAdapter
                extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

            private ArrayList<Product> mWishlist;

            public static class ViewHolder extends RecyclerView.ViewHolder {
                public final View mView;
                public final SimpleDraweeView mImageView;
                public final LinearLayout mLayoutItem, mLayoutRemove, mLayoutEdit;

                public final TextView mProductTitle,mProductDesc,mProductPrice;
                public ViewHolder(View view) {
                    super(view);
                    mView = view;
                    mImageView = (SimpleDraweeView) view.findViewById(R.id.image_cartlist);
                    mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                    mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
                    mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);
                    mProductTitle = (TextView) view.findViewById(R.id.cart_item_title);
                    mProductDesc = (TextView) view.findViewById(R.id.cart_item_desc);
                    mProductPrice = (TextView) view.findViewById(R.id.cart_item_price);

                }
            }

            public SimpleStringRecyclerViewAdapter(ArrayList<Product> myCart) {
                mWishlist = myCart;
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wishlist_item, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onViewRecycled(SimpleStringRecyclerViewAdapter.ViewHolder holder) {
                if (holder.mImageView.getController() != null) {
                    holder.mImageView.getController().onDetach();
                }
                if (holder.mImageView.getTopLevelDrawable() != null) {
                    holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
                }
            }

            @Override
            public void onBindViewHolder(final ViewHolder holder, final int position) {


                holder.mImageView.setImageURI(mWishlist.get(position).getImage());
                holder.mProductPrice.setText("Rs. "+mWishlist.get(position).getPrice());
                holder.mProductDesc.setText(mWishlist.get(position).getDesc());
                holder.mProductTitle.setText(mWishlist.get(position).getTitle());

                holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Intent intent = new Intent(mContext,DetailActivity.class);

                        intent.putExtra("productName",mWishlist.get(position).getTitle());
                        mContext.startActivity(intent);
                    }
                });

                //Set click action
                holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //   ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                        mWishlist.remove(position);
                        notifyDataSetChanged();
                        //Decrease notification count


                    }
                });


            }

            @Override
            public int getItemCount() {
                return mWishlist.size();
            }
        }

        protected void setCartLayout(){
            LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_wishlist_items);
            LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_wishlist_empty);

            if(MyWishList.myWishlist.size() >0){
                layoutCartNoItems.setVisibility(View.GONE);
                layoutCartItems.setVisibility(View.VISIBLE);
            }else {
                layoutCartNoItems.setVisibility(View.VISIBLE);
                layoutCartItems.setVisibility(View.GONE);



            }
        }

    }
