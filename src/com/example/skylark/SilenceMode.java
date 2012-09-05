package com.example.skylark;
import android.app.Activity;  
import android.os.Bundle;  
import android.util.Log;  
import com.example.skylark.ScreenMonitor;
import com.example.skylark.ScreenMonitor.ScreenStateListener;
public class SilenceMode extends Activity {  
    private String TAG = "ScreenObserverActivity";  
    private ScreenMonitor mScreenObserver;  
      
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.aboutus);  
  
        mScreenObserver = new ScreenMonitor(this);  
        mScreenObserver.requestScreenStateUpdate(new ScreenStateListener() {  
            public void onScreenOn() {  
                doSomethingOnScreenOn();  
            }  
  
            public void onScreenOff() {  
                doSomethingOnScreenOff();  
            }  
        });  
    }  
  
    private void doSomethingOnScreenOn() {  
        Log.i(TAG, "Screen is on");
    }  
  
    private void doSomethingOnScreenOff() {  
        Log.i(TAG, "Screen is off");  
    }  
      
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //停止监听screen状态  
        mScreenObserver.stopScreenStateUpdate();  
    }  
}  
