package com.example.skylark;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import com.example.skylark.MyAdapter.vHolder;


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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		defineBL=(ListView)findViewById(R.id.defineBL);
		saveDefine=(Button)findViewById(R.id.saveDefine);
		saveDefine.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				Intent intent=new Intent();
				intent.setClass(DefineBlackList.this, PlanActivity.class);
				DefineBlackList.this.startActivity(intent);
				*/
				
				MyAdapter adapter=(MyAdapter)defineBL.getAdapter();
				for(int i=0;i<adapter.getIsSelected().size();i++)
				{
					Log.v("aa",""+i);
					if(adapter.getIsSelected().get(i))
					{
						Toast.makeText(DefineBlackList.this, adapter.getName(i), Toast.LENGTH_LONG).show();
					}
				}
				//*/
				//Toast.makeText(DefineBlackList.this, "a", Toast.LENGTH_LONG).show();
			}
		});
		
		ActivityManager am= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningProcess = am.getRunningAppProcesses();
		PackageManager pm=this.getPackageManager();
		List<PackageInfo>pi=pm.getInstalledPackages(0);
		int i=0;
		Log.v("my","d");
		for (PackageInfo p : pi) {
            names[i]=p.applicationInfo.loadLabel(getPackageManager()).toString(); 
            i++;
            Log.v("my", "i="+i);
            icons.add(p.applicationInfo.loadIcon(getPackageManager()));
        }
        Log.v("myself","df");
		Log.v("my", "e");
		iniAppChoices();
	}
	private void iniAppChoices()
	{
		MyAdapter adapter=new MyAdapter(this, icons, names, true);
		
		defineBL.setAdapter(adapter);
		defineBL.setItemsCanFocus(true);
		defineBL.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				MyAdapter adapter=((MyAdapter)defineBL.getAdapter());
				adapter.setIsSelected(arg2);
				Toast.makeText(DefineBlackList.this, ""+arg2, Toast.LENGTH_LONG).show();
				vHolder vh=(vHolder)arg1.getTag();
				//vh.cb.setChecked(true);
			}
			
		});
	}
}
