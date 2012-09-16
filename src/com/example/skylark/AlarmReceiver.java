package com.example.skylark;

import com.example.ui.MyApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
 
public class AlarmReceiver extends BroadcastReceiver {
 
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("收到广播");
		Log.v("myAlarm","recive");
		intent=new Intent();
		intent.setClass(context, WhenSucceed.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		//stopService("com.example.skylark.silencemode");
		//收到广播后启动Activity,简单起见，直接就跳到了设置alarm的Activity
        //intent必须加上Intent.FLAG_ACTIVITY_NEW_TASK flag
	}
	
}