/*
 * 主功能界面，但部分的用户体验在此。
 * 其界面包括规划时长，规划黑名单，是否发布到SNS等，其中两个菜单还可以连接到其他界面。
 */
package com.example.skylark;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;

public class PlanActivity extends Activity{
	TimePicker tp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plan);
		tp=(TimePicker)findViewById(R.id.tp);
		tp.setIs24HourView(true);
	}
	
	protected class MyAdaper extends SimpleAdapter{
		
	}
	/*
	 * 返回一个int值表示当前Plan中规划的分钟数。
	 */
	int getTimeLenth()
	{
		
	}
	
	/*
	 * 用于得到当前Plan当中的黑名单，返回一个包名的list
	 */
	ArrayList<String> getBlackList()
	{
		
	}
	
	/*
	 * 返回一个String的SNS网站名称。
	 */
	String getSNS()
	{
		
	}
	
	/*
	 * 用于保存当前Plan到文件中。
	 */
	void savePlan()
	{
		
	}
}
