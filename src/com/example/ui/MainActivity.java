/*
 * 功能主界面，包括三个按钮：规划、黑名单、设置。
 * By Adoni
 */
package com.example.ui;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.skylark.R;
import com.example.skylark.R.id;
import com.example.skylark.R.layout;
import com.example.skylark.R.menu;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

public class MainActivity extends BaseSampleActivity {
	TimePicker tp;
	Spinner sns_sp,bl_sp;
	Button startButton;
	String snsName="";
	String blName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_titles);
        mAdapter = new TestTitleFragmentAdapter(getSupportFragmentManager(),MainActivity.this);
        
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(1);
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
        mIndicator = indicator;
        
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//Toast.makeText(MainActivity.this, "d", Toast.LENGTH_LONG).show();
		Intent intent=new Intent("com.example.skylark.monitorservice");
		stopService(intent);
		finish();
		((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancelAll();
		return super.onOptionsItemSelected(item);
	}
    
}