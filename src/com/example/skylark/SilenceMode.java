package com.example.skylark;
import android.app.Activity;  
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;  
import android.os.IBinder;
import android.util.Log;  
import android.widget.Toast;

import com.example.skylark.ScreenMonitor;
import com.example.skylark.ScreenMonitor.ScreenStateListener;
public class SilenceMode extends Service{  
    private String TAG = "ScreenObserverActivity";  
    private ScreenMonitor mScreenObserver;  
  
    private void doSomethingOnScreenOn() {  
        Log.i(TAG, "Screen is on");
        Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
    }  
  
    private void doSomethingOnScreenOff() {  
        Log.i(TAG, "Screen is off");  
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
}  
