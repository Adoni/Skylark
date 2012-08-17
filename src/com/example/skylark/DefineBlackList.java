package com.example.skylark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

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
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class DefineBlackList extends Activity {
	Button saveDefine;
	ListView defineBL;
	ArrayList<Drawable> icons=new ArrayList<Drawable>();
	String names[]=new String[1000];
	String packageNames[]=new String[1000];
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
				saveDefine();
				Intent intent=new Intent();
				intent.setClass(DefineBlackList.this, PlanActivity.class);
				DefineBlackList.this.startActivity(intent);
				
			}
			
		});
		
		iniAppChoices();
	}
	private void iniAppChoices()
	{
		ActivityManager am= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningProcess = am.getRunningAppProcesses();
		PackageManager pm=this.getPackageManager();
		List<PackageInfo>pi=pm.getInstalledPackages(0);
		int i=0;
		for (PackageInfo p : pi) {
			if(p.packageName.contains("android"))
			{
				continue;
			}
            names[i]=p.applicationInfo.loadLabel(getPackageManager()).toString();
            packageNames[i]=p.packageName;
            i++;
            icons.add(p.applicationInfo.loadIcon(getPackageManager()));
        }
		MyAdapter adapter=new MyAdapter(this, icons, names, true);
		
		defineBL.setAdapter(adapter);
		defineBL.setItemsCanFocus(true);
		defineBL.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				MyAdapter adapter=((MyAdapter)defineBL.getAdapter());
				adapter.setIsSelected(arg2);
				//Toast.makeText(DefineBlackList.this, ""+arg2, Toast.LENGTH_LONG).show();
				//vHolder vh=(vHolder)arg1.getTag();
				//vh.cb.setChecked(true);
			}
			
		});
	}
	
	/*
	 * 保存选中的选项。
	 */
	private void saveDefine()
	{
		String blName=((EditText)findViewById(R.id.nameEdit)).getText().toString();
		if(blName.equals(""))
		{
			blName="Default";
		}
		String blNames="";
		FileInputStream fin;
		try {
			fin = openFileInput(getResources().getString(R.string.blListNames));
			int length = fin.available();   
        byte [] buffer = new byte[length];   
        fin.read(buffer);       
        blNames = ""+EncodingUtils.getString(buffer, "UTF-8");
        if(blNames.contains(blName) && (! blName.equals("Default")))
        {
        	Toast.makeText(DefineBlackList.this, "黑名单"+blName+"已经存在,请重新输入黑名单名字", Toast.LENGTH_LONG).show();
        }
        fin.close();  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FileOutputStream fout=openFileOutput(blName+"bl",Context.MODE_PRIVATE);//用用户输入的黑名单名称建立文件
			MyAdapter adapter=(MyAdapter)defineBL.getAdapter();
			for(int i=0;i<adapter.getIsSelected().size();i++)
			{
				if(adapter.getIsSelected().get(i))
				{
					fout.write((packageNames[i]+" ").getBytes());
					//Toast.makeText(DefineBlackList.this, packageNames[i], Toast.LENGTH_SHORT).show();
				}
			}
			fout.close();
			/*
			 * 保存黑名单名称
			 */
			fout=openFileOutput(getResources().getString(R.string.blListNames),Context.MODE_PRIVATE);
			if(!blNames.contains("Default"))
			{
				blNames=blNames+blName+" ";
			}
			fout.write(blNames.getBytes());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
