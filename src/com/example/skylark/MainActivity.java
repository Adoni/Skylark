/*
 * 功能主界面，包括三个按钮：规划、黑名单、设置。
 * By Adoni
 */
package com.example.skylark;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button startButton,blButton,settingButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        startButton=(Button)findViewById(R.id.startButton);
        blButton=(Button)findViewById(R.id.blButton);
        settingButton=(Button)findViewById(R.id.settingButton);
        
        startButton.setOnClickListener(new myOnClickListener());
        blButton.setOnClickListener(new myOnClickListener());
        settingButton.setOnClickListener(new myOnClickListener());
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
}
