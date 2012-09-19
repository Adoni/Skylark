package com.example.skylark;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class StartCountdown extends Activity{

    static int[] bgs = { R.drawable.zero , R.drawable.one, R.drawable.two,R.drawable.three, R.drawable.four , R.drawable.five , R.drawable.six , R.drawable.seven , R.drawable.eight , R.drawable.nine };
	static int hour = -1;
	static int minute = -1;
	static int second = -1;
	static int starthour = -1;
	static int startminute = -1;
	static int startsecond = -1;
	float Degree=0;
	float currentDegree=0;
	int repeat=0;
	int count;
	final static String tag = "tag";
	ImageView clock;
	ImageView mImageView0;
	ImageView mImageView1;
	ImageView mImageView2;
	ImageView mImageView3;
	ImageView mImageView4;
	ImageView mImageView5;
	ImageView mImageView6;
	Timer timer;
	TimerTask  timerTask;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			System.out.println("handle!");
			if (hour == 0)
			{	
			if (minute == 0) 
			{
				if (second == 0) 
				{
					if (timer != null) 
					{
						timer.cancel();
						timer = null;
					}
					if (timerTask != null) 
					{
						timerTask = null;
					}
					finish();
				}
				else 
				{
					second--;
				}
			}
			else 
			{
				if (second == 0) 
				{
					second =59;
					minute--;
				}
				else 
				{
					second--;
				}
			}
			}
			else
			{
				if (minute == 0) 
				{
					if (second == 0) 
					{
						hour--;
						minute = 59;
						second = 59;
					}
					else 
					{
						second--;
					}
				}
				else 
				{
					if (second == 0) 
					{
						second =59;
						minute--;
					}
					else 
					{
						second--;
					}
				}	
				}
			mImageView1.setImageDrawable(getResources().getDrawable(bgs[hour / 10]));
			mImageView2.setImageDrawable(getResources().getDrawable(bgs[hour % 10]));
			mImageView3.setImageDrawable(getResources().getDrawable(bgs[minute / 10]));
			mImageView4.setImageDrawable(getResources().getDrawable(bgs[minute % 10]));
			mImageView5.setImageDrawable(getResources().getDrawable(bgs[second / 10]));
			mImageView6.setImageDrawable(getResources().getDrawable(bgs[second % 10]));
			Degree = (float) ((float)(1-(float)((float)(hour*3600+minute*60+second)/(count)))*(250*((float)1+((float)1/count))));			
	    	if ( Degree < 250 )
	    	{
			RotateAnimation ra = new RotateAnimation(currentDegree,Degree,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f) ;
	    	ra.setDuration(1);      
           clock.setAnimation(ra);                   
	    	 ra.cancel();
	    	 ra.setFillAfter(true);
	    	 currentDegree = Degree;
	    	}
	    	else if ( repeat!= 1 )
	    	{
	    		repeat=1;
	    		RotateAnimation ra = new RotateAnimation(currentDegree,250,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f) ;
		    	ra.setDuration(1);      
	           clock.setAnimation(ra);                   
		    	 ra.cancel();
		    	 ra.setFillAfter(true);
		    	 currentDegree = Degree;
	    	}
		};
	};

	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(tag, "log---------->onCreate!");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		clock = (ImageView) findViewById(R.id.clock);		
		mImageView1 = (ImageView) findViewById(R.id.ImageView01);
		mImageView2 = (ImageView) findViewById(R.id.ImageView02);
		mImageView3 = (ImageView) findViewById(R.id.ImageView03);
		mImageView4 = (ImageView) findViewById(R.id.ImageView04);
		mImageView5 = (ImageView) findViewById(R.id.ImageView05);
		mImageView6 = (ImageView) findViewById(R.id.ImageView06);
		
		if (hour == -1 && minute == -1) {
			Intent intent = getIntent();
			ArrayList<Integer> times = intent.getIntegerArrayListExtra("times");  ///here
			hour = times.get(0);
			minute = times.get(1);
			second = 0;
			starthour = times.get(2);
			startminute = times.get(3);
			startsecond = times.get(4);
			//startsecond=;
			Calendar c = Calendar.getInstance();
			int mHour = c.get(Calendar.HOUR_OF_DAY);
          int mMinute = c.get(Calendar.MINUTE);
          int mSecond = c.get(Calendar.SECOND);
           
          if ( startsecond > second )
          {
      	  startminute++;
      	  startsecond = second + 60 - startsecond ;
          }
          
          if ( startminute > minute )
            {
        	  starthour++;
        	  startminute = minute + 60 - startminute ;
            }
          else startminute = minute - startminute ;
          
          if ( starthour > hour )
          {
        	  starthour = hour + 24 - starthour ;
          }
          else starthour = hour - starthour;
          
          if ( mSecond > second )
          {
      	  minute--;
      	  second = second + 60 - mSecond ;
          }
          
          if ( mMinute > minute )
            {
        	  hour--;
        	  minute = minute + 60 - mMinute ;
            }
          else minute = minute - mMinute ;
          
          if ( mHour > hour )
          {
      	  hour = hour + 24 - mHour ;
          }
        else hour = hour - mHour;
            
			
		}
		count = starthour * 3600 + startminute * 60 + startsecond ;
		clock.setImageDrawable(getResources().getDrawable(R.drawable.clock));
		mImageView1.setImageDrawable(getResources().getDrawable(bgs[hour / 10]));
		mImageView2.setImageDrawable(getResources().getDrawable(bgs[hour % 10]));
		mImageView3.setImageDrawable(getResources().getDrawable(bgs[minute / 10]));
		mImageView4.setImageDrawable(getResources().getDrawable(bgs[minute % 10]));
		mImageView5.setImageDrawable(getResources().getDrawable(bgs[second / 10]));
		mImageView6.setImageDrawable(getResources().getDrawable(bgs[second % 10]));
		
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = 0;
				handler.sendMessage(msg);
			}
		};
		
		timer = new Timer();
		timer.schedule(timerTask,0,1000);
	}
	
	
	
	
	
	@Override
	protected void onDestroy() {
		Log.v(tag, "log---------->onDestroy!");
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (timerTask != null) {
			timerTask = null;
		}
		hour = -1;
		minute = -1;
		second = -1;
		starthour = -1;
		startminute = -1;
		startsecond = -1;
		repeat = 0;
		super.onDestroy();
	}
	
	@Override
	protected void onStart() {
		Log.v(tag, "log---------->onStart!");
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		Log.v(tag, "log---------->onStop!");
		super.onStop();
	}

	@Override
	protected void onResume() {
		Log.v(tag, "log---------->onResume!");
		super.onResume();
	}
	
	@Override
	protected void onRestart() {
		Log.v(tag, "log---------->onRestart!");
		super.onRestart();
	}
	
	@Override
	protected void onPause() {
		Log.v(tag, "log---------->onPause!");
		super.onPause();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_HOME)
		{
			finish();
		//	Toast.makeText(MainActivity.this, "asdf", Toast.LENGTH_LONG).show();
			return false;
		}
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			finish();
			//Toast.makeText(MainActivity.this, "asdf", Toast.LENGTH_LONG).show();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.check_bl, menu);
        return true;
    }
    
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//Toast.makeText(MainActivity.this, "d", Toast.LENGTH_LONG).show();
		Intent intent=new Intent();
		intent.setClass(StartCountdown.this, ShowBlackList.class);
		String blName=getSharedPreferences("Setting", 0).getString("blName", "");
		if(blName.equals(""))
		{
			Toast.makeText(StartCountdown.this, "静默模式下无法查看黑名单", Toast.LENGTH_SHORT).show();
			return true;
		}
		intent.putExtra("BLName", blName);
		StartCountdown.this.startActivity(intent);
		return super.onOptionsItemSelected(item);
	}
	
}
