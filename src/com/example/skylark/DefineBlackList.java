package com.example.skylark;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class DefineBlackList extends Activity {
	Button saveDefine;
	ListView defineBL;
	ArrayList<Drawable> icons=new ArrayList<Drawable>();
	String names[]=new String[1000];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.define);
		
		saveDefine=(Button)findViewById(R.id.saveButton);
		/*
		saveDefine.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(DefineBlackList.this, PlanActivity.class);
				DefineBlackList.this.startActivity(intent);
			}
		});
		*/
		ActivityManager am= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningProcess = am.getRunningAppProcesses();
		PackageManager pm=this.getPackageManager();
		List<PackageInfo>pi=pm.getInstalledPackages(0);
		int i=0;
		Log.v("my","d");
		for (PackageInfo p : pi) {
			
			//if(i>10) break;
            names[i]=p.applicationInfo.loadLabel(getPackageManager()).toString(); 
            i++;
            Log.v("my", "i="+i);
            //try {
				//icons.add(pm.getApplicationIcon(ti.processName));
            	icons.add(p.applicationInfo.loadIcon(getPackageManager()));
		//	} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
		//		e.printStackTrace();
		//	}
        }
        Log.v("myself","df");
		Log.v("my", "e");
		iniAppChoices();
	}
	private void iniAppChoices()
	{
		defineBL=(ListView)findViewById(R.id.defineBL);
		
		MyAdapter adapter=new MyAdapter(this, icons, names, true);
		
		defineBL.setAdapter(adapter);
	}
}
