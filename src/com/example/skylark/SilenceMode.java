package com.example.skylark;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;  
import android.widget.Toast;

import com.example.skylark.ScreenMonitor;
import com.example.skylark.ScreenMonitor.ScreenStateListener;
public class SilenceMode extends Service{  
    private String TAG = "ScreenObserverActivity";  
    private ScreenMonitor mScreenObserver;  
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
        this.stopSelf();
    }  
  
    private void doSomethingOnScreenOff() {  
        Log.i("mysilence", "Screen is off");  
    }  
    
}  
