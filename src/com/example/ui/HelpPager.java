package com.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.example.skylark.R;
import com.example.ui.BaseSampleActivity;
import com.viewpagerindicator.LinePageIndicator;

public class HelpPager extends BaseSampleActivity {
	private float lastOffset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_lines);

        myAdapter = new MyFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(myAdapter);
        mIndicator = (LinePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        lastOffset=0;
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                //Toast.makeText(HelpPager.this, "Changed to page " + position, Toast.LENGTH_SHORT).show();
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            	Log.v("myScroll",position+" "+positionOffset+" "+positionOffsetPixels);
            	if(position==3 && lastOffset==0.0 && positionOffset==0.0)
            	{
            		HelpPager.this.finish();
            		/*
            		Intent intent=new Intent();
            		intent.setClass(HelpPager.this, MainActivity.class);
            		HelpPager.this.startActivity(intent);
            		*/
            	}
            	lastOffset=positionOffset;
            }

            public void onPageScrollStateChanged(int state) {
            	Log.v("myScroll",""+state);
            }
        });
    }
}