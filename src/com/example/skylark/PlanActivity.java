/*
 * 主功能界面，但部分的用户体验在此。
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
				Intent intent=new Intent("com.example.skylark.monitorservice");
				intent.putExtra("blName", blName);
				intent.putExtra("snsName", snsName);
				intent.putExtra("hour", tp.getCurrentHour());
				intent.putExtra("min", tp.getCurrentMinute());
				startService(intent);
			}
		});
		
		/*
		ArrayAdapter<String> snsAdapter=new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,SNS){
		            @Override
		            public View getDropDownView(int position, View convertView, ViewGroup parent) {
		                View view = getLayoutInflater().inflate(R.layout.sns_item, parent, false);
		                TextView label = (TextView) view.findViewById(R.id.snsLable);
		                label.setText(getItem(position));
		                if (sns_sp.getSelectedItemPosition() == position) {
		                    //label.setTextColor(getResources().getColor(R.color.selected_fg));
		                    //view.setBackgroundColor(getResources().getColor(R.color.selected_bg));
		                    view.findViewById(R.id.onclickicon).setVisibility(View.VISIBLE);
		                    //label.setTextColor(17170437);
		                }
		                return view;
		            }
		            Drawable getIcon(int position)
		            {
		            	Drawable snsIcon;
		            	int[] iconIds={R.drawable.renren,R.drawable.tencent,R.drawable.sina};
		            	return getResources().getDrawable(iconIds[position]);
		            }
		        };
		//snsAdapter.setDropDownViewResource(R.layout.sns_item);
		sns_sp.setAdapter(snsAdapter);
		*/
	}
	
	/*
	 * 用以初始化SNS Spinner
	 */
	private void iniSNS()
	{
		/*
		ArrayList<Map<String, Object>> anslist = new ArrayList<Map<String, Object>>();
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("snsIcon", R.drawable.renren);
        map.put("snsName", "人人网");
        map.put("snsRadio",false);
        anslist.add(map);
 
        map = new HashMap<String, Object>();
        map.put("snsIcon", R.drawable.tencent);
        map.put("snsName", "腾讯微博");
        map.put("snsRadio",false);
        anslist.add(map);
 
        map = new HashMap<String, Object>();
        map.put("snsIcon", R.drawable.sina);
        map.put("snsName", "新浪微博");
        map.put("snsRadio",false);
        anslist.add(map);
        */
        /*
        SimpleAdapter adapter=new SimpleAdapter(this, anslist, R.layout.snsitem,
        		new String[]{"snsIcon","snsName","snsRadio"}, 
        		new int[]{R.id.snsIcon,R.id.snsName,R.id.snsRadio});
        */
		/*
        String[] s={"1","2","3"};
        ArrayList ss=new ArrayList<String>();
        ss.add(s[0]);
        ss.add(s[1]);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(PlanActivity.this, android.R.layout.simple_spinner_item,ss);
        sns_sp.setAdapter(adapter);
        */
		MyAdapter adapter=new MyAdapter(this, 
				new int[]{0,R.drawable.renren,R.drawable.tencent,R.drawable.sina},
				new String[]{"","人人网","腾讯微博","新浪微博"},false);
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
		
		//Toast.makeText(this, blNames, Toast.LENGTH_LONG).show();
		while(blNames.length()>0)
		{
			String name=blNames.substring(0,blNames.indexOf(" "));
			names.add(name);
			blNames=blNames.substring(blNames.indexOf(" ")+1);
		}
		names.add("自定义");
		MyAdapter adapter=new MyAdapter(this, names);
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
