package com.example.skylark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.util.EncodingUtils;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.example.ui.MyAdapter;

public class ShowBlackList extends Activity {
	ListView showBL;
	String BLName="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showbl);
        
        BLName=getIntent().getStringExtra("BLName");
        showBL=(ListView)findViewById(R.id.showBL);
        iniList();
    }
    
    /*
     * 根据黑名单名称获取对应的被禁应用名称
     */
    private ArrayList<String> getNamesFromFile(String BLName)
    {
    	String appNames="";
    	ArrayList<String> names=new ArrayList<String>();
    	FileInputStream fin;
		try {
			fin = openFileInput(BLName+"bl");
			int length = fin.available();   
	        byte [] buffer = new byte[length];   
	        fin.read(buffer);       
	        appNames = ""+EncodingUtils.getString(buffer, "UTF-8");
	        while(appNames.length()>0)
			{
				String name=appNames.substring(0,appNames.indexOf(" "));
				names.add(name);
				appNames=appNames.substring(appNames.indexOf(" ")+1);
			}
	        fin.close();  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return names;
    }
    
    public void iniList()
    {
    	ArrayList<String> names=getNamesFromFile(BLName);
        ArrayList<Drawable> icons=new ArrayList<Drawable>();
        ArrayList<String> appNames =new ArrayList<String>();
        for(int i=0;i<names.size();i++)
        {
        	String name=names.get(i);
        	PackageManager pm=this.getPackageManager();
        	try {
				icons.add(pm.getApplicationIcon(name));
				appNames.add(pm.getApplicationInfo(name, 0).loadLabel(pm).toString());
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        MyAdapter adapter=new MyAdapter(this, icons, appNames, false);
        showBL.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
