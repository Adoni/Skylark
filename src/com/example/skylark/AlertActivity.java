package com.example.skylark;

import java.util.HashMap;

import com.example.ui.MyApplication;
import com.umeng.api.common.SnsParams;
import com.umeng.api.exp.UMSNSException;
import com.umeng.api.sns.UMSnsService;
import com.umeng.api.sns.UMSnsService.SHARE_TO;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AlertActivity extends Activity{
	int hour,min;
	String blName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myalertdialog);
		hour=getIntent().getIntExtra("hour", 0);
		min=getIntent().getIntExtra("min", 0);
		
		((Button)findViewById(R.id.yes)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doWhenYes();
			}
			
		});
		((Button)findViewById(R.id.no)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DevicePolicyManager dpm=(DevicePolicyManager)AlertActivity.this.getSystemService(Context.DEVICE_POLICY_SERVICE);
				dpm.lockNow();
				finish();
			}
			
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_HOME)
		{
			doWhenYes();
		//	Toast.makeText(MainActivity.this, "asdf", Toast.LENGTH_LONG).show();
			return false;
		}
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			doWhenYes();
			//Toast.makeText(MainActivity.this, "asdf", Toast.LENGTH_LONG).show();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	private void doWhenYes()
	{
		Intent intent=new Intent();
        intent.setClass(AlertActivity.this,WhenFail.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	intent.putExtra("hour", hour);
    	intent.putExtra("min", min);
    	intent.putExtra("blName", "静默模式");
        AlertActivity.this.startActivity(intent);

		AlertActivity.this.finish();
	}
}
