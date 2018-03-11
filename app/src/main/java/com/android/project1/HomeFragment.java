package com.android.project1;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.*;

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
import static com.android.project1.Config.URL_CATEGORY;
import static com.android.project1.HomeActivity.viewPager;

/**
 * Created by Gaurav on 30-06-2017.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private GridView mGridView;
    private ProgressBar mProgressBar;

    private GridViewAdapter mGridAdapter;
    private ArrayList<Categories> mCategories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.gridview,container,false);
        mGridView = (GridView) v.findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        mCategories =  new ArrayList<>();
        mGridAdapter = new GridViewAdapter(getContext(),R.layout.grid_item_layout,mCategories);
        mGridView.setAdapter(mGridAdapter);
        new AsyncHttpTask().execute(URL_CATEGORY);
        mProgressBar.setVisibility(View.VISIBLE);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Categories item =(Categories) parent.getItemAtPosition(position);
                String category = item.getTitle();
                Toast.makeText(getActivity(),category,Toast.LENGTH_SHORT).show();

                getProducts(category,position+1);
            }
        });
        return v;
    }
    //Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 1) {
                mGridAdapter.setGridData(mCategories);
            } else {
                Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

            //Hide progressbar
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void getProducts(String s,int position){
     /*   android.support.v4.app.FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_main2,new ProductListFragment(s),"Home");
        ft.addToBackStack(null);

        ft.commit();*/
          viewPager.setCurrentItem(position);

    }

    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (null != stream) {
            stream.close();
        }
        return result;
    }

    /**
     * Parsing the feed results and get the list
     *
     * @param result
     */
    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("result");
            Categories item;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("category");
                String attachment = post.optString("image_url");
                item = new Categories();
                item.setTitle(title);
                item.setImage(attachment);
                mCategories.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class GridViewAdapter extends ArrayAdapter<Categories> {

        //private final ColorMatrixColorFilter grayscaleFilter;
        private Context mContext;
        private int layoutResourceId;
        private ArrayList<Categories> mCategories = new ArrayList<Categories>();

        public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<Categories> mGridData) {
            super(mContext, layoutResourceId, mGridData);
            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;
            this.mCategories = mGridData;
        }


        /**
         * Updates grid data and refresh grid items.
         *
         * @param mGridData
         */
        public void setGridData(ArrayList<Categories> mGridData) {
            this.mCategories = mGridData;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;

            if (row == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title);
                holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            Categories item = mCategories.get(position);
            holder.titleTextView.setText(Html.fromHtml(item.getTitle()));


            Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
            return row;

        }


        class ViewHolder {
            TextView titleTextView;
            ImageView imageView;
        }
    }

}
