package com.example.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.skylark.R;
import com.example.skylark.RecordTool;
import com.example.ui.MyAdapter.ViewHolder;
import com.umeng.api.exp.UMSNSException;
import com.umeng.api.sns.UMSnsService;
import com.umeng.api.sns.UMSnsService.SHARE_TO;

import android.R.fraction;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
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
		int[] icons=new int[]{R.drawable.account,R.drawable.history,R.drawable.aboutus_button,R.drawable.help_button};
		String[] names=new String[]{"选择账户","历史记录","关于我们","帮助"};
		MyAdapter adapter=new MyAdapter(context, icons, names, false, R.layout.nomal_item);
		settingList.setAdapter(adapter);
		
		settingList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg2==0)
				{
					showSNSSetting(arg1, arg2);
				}
				if(arg2==1)
				{
					showHistory(arg1, arg2);
				}
				if(arg2==2)
				{
					showAboutUs(arg1, arg2);
				}
				if(arg2==3)
				{
					showHelp(arg1, arg2);
				}
			}
		});
		
	}
	public void saveSetting(int sns)
	{
		SharedPreferences setting=context.getSharedPreferences("Setting", 0);
		SharedPreferences.Editor editor=setting.edit();
		editor.putInt("SNS", sns);
		editor.commit();
	}
	
	public void showSNSSetting(View arg1, int arg2)
	{
		if(pop!=null && pop.isShowing())
		{
			pop.dismiss();
			return;
		}
		final ViewHolder viewHolder=(ViewHolder)arg1.getTag();
		viewHolder.img.setBackgroundResource(R.drawable.account_pressed);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View popView=inflater.inflate(R.layout.pop_layout,null);
		final ListView popList=(ListView)popView.findViewById(R.id.popList);
		int[] icons=new int[]{R.drawable.renren,R.drawable.tencent,R.drawable.sina};
		String[] names=new String[]{"人 人 网","腾 讯 微 薄","新 浪 微 博"};
		MyAdapter adapter=new MyAdapter(context, icons, names, true, R.layout.spinner_item);
		adapter.setIsSelected(context.getSharedPreferences("Setting", 0)
				.getInt("SNS", 0)-1);
		popList.setAdapter(adapter);
		popList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "sdf", Toast.LENGTH_LONG).show();
				saveSetting(arg2+1);
				pop.dismiss();
				viewHolder.img.setBackgroundResource(R.drawable.account);
				ArrayList<SHARE_TO> Name=new ArrayList<UMSnsService.SHARE_TO>();
				Name.add(SHARE_TO.RENR);
				Name.add(SHARE_TO.TENC);
				Name.add(SHARE_TO.SINA);
				if(!UMSnsService.isAuthorized(MyApplication.getInstance(), Name.get(arg2)))
				{
					UMSnsService.OauthCallbackListener listener = new UMSnsService.OauthCallbackListener(){
				        public void onComplete(Bundle value, SHARE_TO platform) {
				        	Toast.makeText(context, "绑定成功", Toast.LENGTH_LONG).show();
				        }
				        public void onError(UMSNSException e, SHARE_TO platform) {
				        	Toast.makeText(context, "对不起，绑定失败，请检查网络设置", Toast.LENGTH_LONG).show();
				        	//
				        }
					};
					
					Log.v("my","fore");
					if(arg2==0)
					{
						//Toast.makeText(context, "asdf", Toast.LENGTH_LONG).show();
						UMSnsService.oauthRenr(context, listener);
						Log.v("my","renren");
					}
					if(arg2==1)
					{
						UMSnsService.oauthTenc(context, listener);
					}
					if(arg2==2)
					{
						UMSnsService.oauthSina(context, listener);
					}
					return ;
				}
			}
		});
		pop=new PopupWindow(popView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setOnDismissListener(new OnDismissListener() {
			
			public void onDismiss() {
				// TODO Auto-generated method stub
				viewHolder.img.setBackgroundResource(R.drawable.account);
			}
		});
		pop.showAsDropDown(arg1);
	}
	public void showHistory(View arg1, int arg2)
	{
		if(pop!=null && pop.isShowing())
		{
			pop.dismiss();
			return;
		}
		final ViewHolder viewHolder=(ViewHolder)arg1.getTag();
		viewHolder.img.setBackgroundResource(R.drawable.history_pressed);
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
			if(success.contains(item))
			{
				icons.add(R.drawable.ok);
			}
			else
			{
				icons.add(R.drawable.fail);
			}
		}
		int[] iconIDs=new int[icons.size()];
		String[] names=new String[icons.size()];
		for(int i=0;i<icons.size();i++)
		{
			iconIDs[i]=icons.get(i);
			names[i]=items.get(i);
		}
		LayoutInflater inflater=(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View popView=inflater.inflate(R.layout.pop_layout,null);
		ListView popList=(ListView)popView.findViewById(R.id.popList);
		MyAdapter adapter=new MyAdapter(context, iconIDs, names, false,R.layout.spinner_item);
		popList.setAdapter(adapter);
		popList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				pop.dismiss();
				
			}
		});
		pop=new PopupWindow(popView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setOnDismissListener(new OnDismissListener() {
			
			public void onDismiss() {
				// TODO Auto-generated method stub
				viewHolder.img.setBackgroundResource(R.drawable.history);
			}
		});
		pop.showAsDropDown(arg1);
	}
	
	public void showAboutUs(View arg1, int arg2)
	{
		if(pop!=null && pop.isShowing())
		{
			pop.dismiss();
			
			return;
		}
		LayoutInflater inflater=(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View popView=inflater.inflate(R.layout.aboutus,null);
		popView.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				
			}
		});
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();//屏幕宽度
		int height = wm.getDefaultDisplay().getHeight();
		pop=new PopupWindow(popView,width*9/10, LayoutParams.WRAP_CONTENT);
		pop.setAnimationStyle(R.style.Animation);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setOnDismissListener(new OnDismissListener() {
			
			public void onDismiss() {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "asdfads", Toast.LENGTH_LONG).show();
				WindowManager.LayoutParams lp = (WindowManager.LayoutParams)((Activity) context).getWindow().getAttributes();
				lp.alpha = 1f; //0.0-1.0
		      	((Activity) context).getWindow().setAttributes(lp);
			}
		});
		pop.showAtLocation(view, Gravity.CENTER, 0, 0);
		//WindowManager.LayoutParams lp = (WindowManager.LayoutParams)((Activity) context).getWindow().getAttributes();
		WindowManager.LayoutParams lp = (WindowManager.LayoutParams)((Activity) context).getWindow().getAttributes();
		lp.alpha = 0.3f; //0.0-1.0
      	((Activity) context).getWindow().setAttributes(lp);
      	
	}
	public void showHelp(View arg1, int arg2)
	{
		Intent intent=new Intent();
		intent.setClass(context, HelpPager.class);
		context.startActivity(intent);
	}
	
}
