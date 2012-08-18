package com.example.skylark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.util.EncodingUtils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class MonitorService extends Service{
	private static final String TAG="MonitorService";
	private int hour;
	private int min;
	private Timer timer;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("myservice","stop");
		timer.cancel();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		String blName=""+intent.getStringExtra("blName");
		String snsName=""+intent.getStringExtra("snsName");
		hour=intent.getIntExtra("hour", 0);
		min=intent.getIntExtra("min", 0);
		timer=new Timer();
		final String appNames=getAppNames(blName);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(timeover())
				{
					timer.cancel();
					stopService(new Intent("com.example.skylark.monitorservice"));
				}
				if(!monitor(appNames))
				{
					timer.cancel();
					stopService(new Intent("com.example.skylark.monitorservice"));
				}
			}
		}, 0,60000);
		
	}
	boolean timeover()
	{
		final Time t=new Time();
		t.setToNow();
		int nowHour=t.hour;
		int nowMin=t.minute;
		if(nowHour>hour) return true;
		if(nowHour<hour) return false;
		if(nowMin>=min) return true;
		return false;
	}
	String getAppNames(String blName)
	{
		String appNames="";
    	FileInputStream fin;
		try {
			fin = openFileInput(blName+"bl");
			int length = fin.available();   
	        byte [] buffer = new byte[length];   
	        fin.read(buffer);       
	        appNames = ""+EncodingUtils.getString(buffer, "UTF-8");
	        fin.close();  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appNames;
	}
	/*
	 * 用于判断当前Plan的执行情况。
	 */
	boolean monitor(String appNames)
	{
		ActivityManager am=(ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> runningProcess=am.getRunningServices(1000);
		String processName="";
		Log.v("myservice",appNames);
		for(int i=0;i<runningProcess.size();i++)
		{
			//Log.v("myservice",runningProcess.get(i).processName);
			processName=runningProcess.get(i).process;
			//Log.v("myservice","+"+processName);
			if(appNames.contains(processName))
			{
				fail();
				am.killBackgroundProcesses(processName);
				return false;
			}
		}
		return true;
	}
	
	/*
	 * 用于在用户触犯规则时，杀死进程，当前先实现提醒和阻碍
	 * 若成功则返回真，否则返回假
	 */
	boolean killProcess(String processName)
	{
		
		return true;
	}
	
	/*
	 * 用于在SNS上发布状态，使用的是泽辰提供的API
	 * 若成功则返回真，否则返回假
	 */
	boolean publishAtSNS()
	{
		return true;
	}
	
	void success()
	{
		Log.v("myservice","success");
	}
	void fail()
	{
		Log.v("myservice","fail");
	}
}
