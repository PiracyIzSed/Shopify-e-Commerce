package com.android.project1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {


    private PrefManager prefManager;
    private ViewPager viewPager;
    private Button skip;
    private Button next;
    private int[] layouts;
    private CustomPagerAdapter pagerAdapter;


    private LinearLayout dotsLayout;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        setContentView(R.layout.welcome_main);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        viewPager=(ViewPager)findViewById(R.id.view_pager);

        skip=(Button)findViewById(R.id.skip);
        next=(Button)findViewById(R.id.next);
        dotsLayout=(LinearLayout) findViewById(R.id.layoutDots);
        layouts=new int[]{R.layout.welcome_1,
                          R.layout.welcome_2};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();


    skip.setOnClickListener(new View.OnClickListener(){
        @Override
                public void onClick(View v){
            launchHomeScreen();
        }
    });

   next.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
           int current = viewPager.getCurrentItem()+1;
            if(current< layouts.length){
                viewPager.setCurrentItem(current);
            }
            else {launchHomeScreen();}
        }
    });

        pagerAdapter = new CustomPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(pagerListener);

    }

        private void launchHomeScreen() {
            prefManager.setFirstTimeLaunch(false);
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            finish();
        }

    public class CustomPagerAdapter extends PagerAdapter{
        private LayoutInflater layoutInflater;

        public CustomPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }
    ViewPager.OnPageChangeListener pagerListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
               addBottomDots(position);
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                next.setText("DONE !");
                skip.setVisibility(View.INVISIBLE);
            } else {
                // still pages are left
                next.setText("NEXT");
                skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);


        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#6824;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }


}


