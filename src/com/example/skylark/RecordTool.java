package com.example.skylark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.util.EncodingUtils;

import com.example.ui.MyAdapter;

import android.content.Context;
import android.provider.OpenableColumns;
import android.text.format.Time;
import android.util.Log;

public class RecordTool {
	private Context context;
	private String failName="fail.re";
	private String succeedName="succeed.re";
	private String continuousName="continuous.re";
	
	public RecordTool(Context context,boolean isSucceed)
	{
		this.context=context;
		if(isSucceed)
		{
			write_in(succeedName);
			write_in(continuousName);
		}
		else
		{
			write_in(failName);
			clearContinuous();
		}
		
	}
	public void clearContinuous()
	{
		try {
			FileOutputStream fout=context.openFileOutput(continuousName,Context.MODE_PRIVATE);//用用户输入的黑名单名称建立文件
			fout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void write_in(String name)
	{
		try {
			FileOutputStream fout=context.openFileOutput(name,Context.MODE_APPEND);//用用户输入的黑名单名称建立文件
			String times="";
			Time t=new Time();
			t.setToNow();
			times=t.year+"/"+t.month+"/"+t.monthDay+" "+t.hour+":"+t.minute+";";
			fout.write(times.getBytes());
			fout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getTimes(String name)
	{
		FileInputStream fin;
		String fileContet="";
		try {
			fin =context.openFileInput(name);
			int length = fin.available();   
	        byte [] buffer = new byte[length];   
	        fin.read(buffer);       
	        String fileContent = ""+EncodingUtils.getString(buffer, "UTF-8");
	        int ans = 0;
	        Log.v("my",fileContent);
	        while(fileContent.length()>0)
	        {
	        	ans++;
	        	Log.v("ssd",ans+"");
	        	fileContent=fileContent.substring(fileContent.indexOf(';')+1);
	        }
	        return ans;
		} catch (FileNotFoundException e) {
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
