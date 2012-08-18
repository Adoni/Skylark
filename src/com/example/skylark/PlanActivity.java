/*
 * 主功能界面，大部分的用户体验在此。
 * 其界面包括规划时长，规划黑名单，是否发布到SNS等，其中两个菜单还可以连接到其他界面。
 */
package com.example.skylark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.EncodingUtils;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.skylark.MyAdapter;

public class PlanActivity extends Activity{
	TimePicker tp;
	Spinner sns_sp,bl_sp;
	Button startButton;
	String snsName="";
	String blName="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plan);
		tp=(TimePicker)findViewById(R.id.tp);
		tp.setIs24HourView(true);
		
		sns_sp=(Spinner)findViewById(R.id.sns_sp);
		bl_sp=(Spinner)findViewById(R.id.bl_sp);
		iniSNS();
		iniBL();
		findViewById(R.id.start).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(blName.equals(""))
				{
					Toast.makeText(PlanActivity.this, "请选择黑名单", Toast.LENGTH_SHORT).show();
				}
				Intent intent=new Intent("com.example.skylark.monitorservice");
				intent.putExtra("blName", blName);
				intent.putExtra("snsName", snsName);
				intent.putExtra("hour", tp.getCurrentHour());
				intent.putExtra("min", tp.getCurrentMinute());
				startService(intent);
			}
		});
	}
	
	/*
	 * 用以初始化SNS Spinner
	 */
	private void iniSNS()
	{
		MyAdapter adapter=new MyAdapter(this, 
				new int[]{0,R.drawable.renren,R.drawable.tencent,R.drawable.sina},
				new String[]{"不发布","人人网","腾讯微博","新浪微博"},false);
		sns_sp.setAdapter(adapter);
        sns_sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String[] snsNames={"","renren","tencent","sina"};
				snsName=snsNames[arg2];
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
        	
		});
	}
	
	/*
	 * 用以初始化BL Spinner
	 */
	private void iniBL()
	{
		final ArrayList<String> names=new ArrayList<String>();
		names.add("");
		String blNames = "";
		FileInputStream fin;
		try {
			fin = openFileInput(getResources().getString(R.string.blListNames));
			int length = fin.available();   
        byte [] buffer = new byte[length];   
        fin.read(buffer);       
        blNames = ""+EncodingUtils.getString(buffer, "UTF-8");
        fin.close();  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(blNames.length()>0)
		{
			String name=blNames.substring(0,blNames.indexOf(" "));
			names.add(name);
			blNames=blNames.substring(blNames.indexOf(" ")+1);
		}
		names.add("自定义");
		MyAdapter adapter=new MyAdapter(this, names,false);
		bl_sp.setAdapter(adapter);
		bl_sp.setSelection(0, true);
        bl_sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg2==arg0.getCount()-1)
				{
					Intent intent=new Intent();
					intent.setClass(PlanActivity.this, DefineBlackList.class);
					PlanActivity.this.startActivity(intent);
				}
				else blName=names.get(arg2);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(PlanActivity.this, DefineBlackList.class);
				PlanActivity.this.startActivity(intent);
			}
        	
		});
	}
}
