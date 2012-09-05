package com.example.skylark;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

public class Countdown {
	public static void Countdown(Context context,int hour, int min)
	{
		Intent intent=new Intent();
		ArrayList<Integer> list = new ArrayList<Integer>();  
		list.add(hour);                                     
		list.add(min);                                    
		intent.setClass(context, StartCountdown.class); 
		
		intent.putIntegerArrayListExtra("times", list);      
		context.startActivity(intent);
	}
}
