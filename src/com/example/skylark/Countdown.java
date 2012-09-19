package com.example.skylark;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Countdown {
	public static void Countdown(Context context)
	{
		Intent intent=new Intent();
		int sHour,sMin,sSec,fHour,fMin;
		SharedPreferences setting=context.getSharedPreferences("Setting", 0);
		sHour=setting.getInt("sHour", 0);
		sMin=setting.getInt("sMin", 0);
		sSec=setting.getInt("sSec", 0);
		fHour=setting.getInt("Hour", 0);
		fMin=setting.getInt("Min", 0);
		
		ArrayList<Integer> list = new ArrayList<Integer>();  
		list.add(fHour);                                     
		list.add(fMin);
		list.add(sHour);                                     
		list.add(sMin);
		list.add(sSec);
		
		intent.setClass(context, StartCountdown.class); 
		
		intent.putIntegerArrayListExtra("times", list);      
		context.startActivity(intent);
	}
}
