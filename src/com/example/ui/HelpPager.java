package com.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.skylark.R;
import com.example.ui.BaseSampleActivity;
import com.viewpagerindicator.LinePageIndicator;

public class HelpPager extends BaseSampleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_lines);

        myAdapter = new MyFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(myAdapter);
        mIndicator = (LinePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                //Toast.makeText(HelpPager.this, "Changed to page " + position, Toast.LENGTH_SHORT).show();
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            	if(position==3)
            	{
            		Intent intent=new Intent();
            		intent.setClass(HelpPager.this, MainActivity.class);
            		HelpPager.this.startActivity(intent);
            	}
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}