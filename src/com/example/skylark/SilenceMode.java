package com.example.skylark;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;  
import android.widget.Toast;

import com.example.skylark.ScreenMonitor;
import com.example.skylark.ScreenMonitor.ScreenStateListener;
import com.example.ui.MyApplication;
public class SilenceMode extends Service{  
    private String TAG = "ScreenObserverActivity";  
    private ScreenMonitor mScreenObserver;  
    private int year,mon,day,hour,min;
    private Calendar c;
    private Context context;
    
    @Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		mScreenObserver = new ScreenMonitor(this);  
        mScreenObserver.requestScreenStateUpdate(new ScreenStateListener() {  
            public void onScreenOn() {  
                doSomethingOnScreenOn();  
            }  
  
            public void onScreenOff() {  
                doSomethingOnScreenOff();  
            }  
        });
        Time t=new Time();
        t.setToNow();
        year=t.year;
        mon=t.month;
        day=t.monthDay;
        hour=intent.getIntExtra("hour", 0);
        min=intent.getIntExtra("min", 0);
        startAlarm();
		Log.v("mysilence","start");
	}

	@Override
	public void onDestroy() {  
        super.onDestroy();  
        //停止监听screen状态  
        mScreenObserver.stopScreenStateUpdate();  
        
    }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}  
	
	private void doSomethingOnScreenOn() {  
        Log.i("mysilence", "Screen is on");
        Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
        Intent intent=new Intent();
        intent.setClass(SilenceMode.this,WhenFail.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	intent.putExtra("hour", hour);
    	intent.putExtra("min", min);
        MyApplication.getInstance().startActivity(intent);
        this.stopSelf();
    }  
  
    private void doSomethingOnScreenOff() {  
        Log.i("mysilence", "Screen is off");  
    }
    
    private void startAlarm()
    {
    	c=Calendar.getInstance();
    	 
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,mon);//也可以填数字，0-11,一月为0
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);
        //设定时间为 2011年6月28日19点50分0秒
        //c.set(2011, 05,28, 19,50, 0);
        //也可以写在一行里
        Log.v("myAlarm",""+year+" "+mon+" "+day+" "+hour+" "+min);
        Intent intent=new Intent(this,AlarmReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(this, 0, intent,0);
        //设置一个PendingIntent对象，发送广播
        AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
        //获取AlarmManager对象
        am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
        //时间到时，执行PendingIntent，只执行一次
        //AlarmManager.RTC_WAKEUP休眠时会运行，如果是AlarmManager.RTC,在休眠时不会运行
        //am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 10000, pi);
        //如果需要重复执行，使用上面一行的setRepeating方法，倒数第二参数为间隔时间,单位为毫秒
 
    }
}  
