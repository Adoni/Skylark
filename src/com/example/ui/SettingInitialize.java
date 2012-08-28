package com.example.ui;

import java.util.ArrayList;

import com.example.skylark.R;
import com.example.skylark.RecordTool;

import android.R.fraction;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class SettingInitialize {
	private View view;
	private Context context;
	private Fragment fragment;
	private ListView settingList;
	private PopupWindow pop;
	public SettingInitialize(View view, TestFragment fragment,Context context, PopupWindow pop)
	{
		this.view=view;
		this.fragment=fragment;
		//this.context=MyApplication.getInstance();
		this.context=context;
		this.pop=pop;
	}
	public void iniSetting()
	{
		settingList=(ListView)view.findViewById(R.id.settingList);
		int[] icons=new int[]{R.drawable.friends,R.drawable.friends,R.drawable.friends,R.drawable.friends};
		String[] names=new String[]{"选择账户","历史记录","关于我们","帮助"};
		MyAdapter adapter=new MyAdapter(context, icons, names, false);
		settingList.setAdapter(adapter);
		
		settingList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg2==0)
				{
					LayoutInflater inflater=(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
					View popView=inflater.inflate(R.layout.pop_layout,null);
					ListView popList=(ListView)popView.findViewById(R.id.popList);
					int[] icons=new int[]{R.drawable.renren,R.drawable.tencent,R.drawable.sina};
					String[] names=new String[]{"人人网","腾讯微薄","新浪微波"};
					MyAdapter adapter=new MyAdapter(context, icons, names, true);
					popList.setAdapter(adapter);
					pop=new PopupWindow(popView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					pop.showAsDropDown(arg1);
					pop.setBackgroundDrawable(new BitmapDrawable());
					pop.setFocusable(true);
					pop.setOutsideTouchable(true);
					//pop.showAtLocation(arg0, 10, 100, 100);
					Toast.makeText(context, "sdf",Toast.LENGTH_LONG).show();
					popList.setFocusable(true);
					pop.update();
					popList.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
								pop.dismiss();
								//pop.update();
						}
					});
					
				}
				if(arg2==1)
				{
					ArrayList<Integer> icons=new ArrayList<Integer>();
					ArrayList<String> items=new ArrayList<String>();
					RecordTool re=new RecordTool(context);
					String total=""+re.getRecord(RecordTool.totalName);
					String success=re.getRecord(RecordTool.succeedName);
					String fail=re.getRecord(RecordTool.failName);
				//	Toast.makeText(context, total+success, Toast.LENGTH_LONG).show();
					
					while(total.length()>0)
					{
						String item=total.substring(0, total.indexOf(';'));
						total=total.substring(total.indexOf(';')+1);
						items.add(item);
					}
					LayoutInflater inflater=(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
					View popView=inflater.inflate(R.layout.pop_layout,null);
					ListView popList=(ListView)popView.findViewById(R.id.popList);
					MyAdapter adapter=new MyAdapter(context, items, false);
					popList.setAdapter(adapter);
					pop=new PopupWindow(popView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					pop.showAsDropDown(arg1);
				}
			}
		});
		
	}
	
}
