/*
 * 主功能界面，但部分的用户体验在此。
 * 其界面包括规划时长，规划黑名单，是否发布到SNS等，其中两个菜单还可以连接到其他界面。
 */
package com.example.skylark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
	Button saveButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plan);
		tp=(TimePicker)findViewById(R.id.tp);
		tp.setIs24HourView(true);
		sns_sp=(Spinner)findViewById(R.id.sns_sp);
		bl_sp=(Spinner)findViewById(R.id.bl_sp);
		findViewById(R.id.saveButton).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				savePlan();
			}
		});
		iniSNS();
		iniBL();
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
	void iniSNS()
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
		sns_sp.setSelection(0, true);
        sns_sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(PlanActivity.this, "你选择的是第"+arg3+"项", Toast.LENGTH_LONG).show();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
        	
		});
	}
	
	/*
	 * 用以初始化BL Spinner
	 */
	void iniBL()
	{
		MyAdapter adapter=new MyAdapter(this, 
				new int[]{0,0},
				new String[]{"","自定义"},false);
		bl_sp.setAdapter(adapter);
		bl_sp.setSelection(0, true);
        bl_sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg3==0)
				{
					Intent intent=new Intent();
					intent.setClass(PlanActivity.this, DefineBlackList.class);
					PlanActivity.this.startActivity(intent);
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(PlanActivity.this, DefineBlackList.class);
				PlanActivity.this.startActivity(intent);
			}
        	
		});
	}
	
	/*
	 * 返回一个int值表示当前Plan中规划的分钟数。
	 */
	int getTimeLenth()
	{
		return 0;
	}
	
	/*
	 * 用于得到当前Plan当中的黑名单，返回一个包名的list
	 */
	ArrayList<String> getBlackList()
	{
		return null;
	}
	
	/*
	 * 返回一个String的SNS网站名称。
	 */
	String getSNS()
	{
		return null;
	}
	
	/*
	 * 用于保存当前Plan到文件中。
	 */
	void savePlan()
	{
		
	}
}
