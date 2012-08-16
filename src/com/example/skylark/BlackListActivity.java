/*
 * 黑名单界面，包括一个黑名单组的列表，每一条同时对应一个黑名单
 * 在黑名单之下是添加按钮和修改按钮
 */
package com.example.skylark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.util.EncodingUtils;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class BlackListActivity extends Activity{
	private ListView blList;
	private ArrayList<String> names=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blacklist);
		
		blList=(ListView)findViewById(R.id.BLList);
		iniList();
		((Button)findViewById(R.id.addBL)).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(BlackListActivity.this, DefineBlackList.class);
				BlackListActivity.this.startActivity(intent);
			}
		});
		((Button)findViewById(R.id.manageBL)).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void iniList()
	{
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
		if(names.size()==0)
		{
			Toast.makeText(this, "您当前没有任何黑名单", Toast.LENGTH_SHORT).show();
		}
		ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,names);
		blList.setAdapter(adapter);
		blList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(BlackListActivity.this, ShowBlackList.class);
				intent.putExtra("BLName", names.get(arg2));
				//Toast.makeText(BlackListActivity.this, names.get(arg2), Toast.LENGTH_SHORT).show();
				BlackListActivity.this.startActivity(intent);
			}
		});
	}
}
