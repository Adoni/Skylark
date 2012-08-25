/*
 * 功能主界面，包括三个按钮：规划、黑名单、设置。
 * By Adoni
 */
package com.example.ui;
/*
import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button startButton,blButton,settingButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancelAll();
        startButton=(Button)findViewById(R.id.startButton);
        blButton=(Button)findViewById(R.id.blButton);
        settingButton=(Button)findViewById(R.id.settingButton);
        
        startButton.setOnClickListener(new myOnClickListener());
        blButton.setOnClickListener(new myOnClickListener());
        settingButton.setOnClickListener(new myOnClickListener());
        
        
        //showNotification(R.drawable.icon,"name","name1","name2");
    }
    private class myOnClickListener implements OnClickListener{
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			
			if((Button)v==startButton){
				intent.setClass(MainActivity.this, PlanActivity.class);
			}
			if((Button)v==blButton){
				intent.setClass(MainActivity.this, BlackListActivity.class);
			}
			if((Button)v==settingButton){
				intent.setClass(MainActivity.this, SettingActivity.class);
			}
			MainActivity.this.startActivity(intent);
		}
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
		//nm.cancelAll();
		((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancelAll();
		return super.onOptionsItemSelected(item);
	}
	
}
*/

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
        setContentView(R.layout.themed_titles);
        mAdapter = new TestTitleFragmentAdapter(getSupportFragmentManager(),MainActivity.this);
        
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(1);
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
        //mIndicator = indicator;
        
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