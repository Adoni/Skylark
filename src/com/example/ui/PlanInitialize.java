package com.example.ui;
/*
 * 主功能界面，大部分的用户体验在此。
 * 其界面包括规划时长，规划黑名单，是否发布到SNS等，其中两个菜单还可以连接到其他界面。
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.util.EncodingUtils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.skylark.*;
import com.umeng.api.exp.UMSNSException;
import com.umeng.api.sns.UMSnsService;
import com.umeng.api.sns.UMSnsService.SHARE_TO;

public class PlanInitialize{
	TimePicker tp;
//	Spinner sns_sp,bl_sp;
	Button sns_sp,bl_sp;
	Button startButton;
	String snsName="";
	String blName="";
	TestFragment fragment;
	Context context;
	View view;
	private PopupWindow pop;
	public PlanInitialize(View view, TestFragment fragment,Context context, PopupWindow pop)
	{
		this.view=view;
		this.fragment=fragment;
		//this.context=MyApplication.getInstance();
		this.context=context;
		this.pop=pop;
	}
	public void iniPlan() {
		// TODO Auto-generated method stub
		/*
		sns_sp=(Spinner)view.findViewById(R.id.sns_sp);
		bl_sp=(Spinner)view.findViewById(R.id.bl_sp);
		*/
		sns_sp=(Button)view.findViewById(R.id.sns_sp);
		bl_sp=(Button)view.findViewById(R.id.bl_sp);
		iniTime();
		iniSNS();
		iniBL();
		startButton=(Button)view.findViewById(R.id.start);
		startButton.setClickable(false);
		startButton.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startMonitor();
				
			}
		});
		((Button)view.findViewById(R.id.silenceMode)).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startSilenceMode();
			}
		});
	}
	
	private void startMonitor()
	{
		if(context.getSharedPreferences("Setting", 0).getBoolean("blChanged", false))
		{
			Toast.makeText(context, "黑名单列表已改变，请重新选择黑名单！", Toast.LENGTH_SHORT).show();
			iniBL();
			return;
		}
		if(blName.equals(""))
		{
			Toast.makeText(context, "请选择黑名单", Toast.LENGTH_SHORT).show();
			return;
		}
		//startButton.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.friends));
		Intent intent=new Intent("com.example.skylark.monitorservice");
		intent.putExtra("blName", blName);
		intent.putExtra("snsName", snsName);
		intent.putExtra("hour", tp.getCurrentHour());
		intent.putExtra("min", tp.getCurrentMinute());
		
		saveStatus();
		HashMap<String, SHARE_TO> Name=new HashMap<String, UMSnsService.SHARE_TO>();
		Name.put("renren", SHARE_TO.RENR);
		Name.put("tencent", SHARE_TO.TENC);
		Name.put("sina",SHARE_TO.SINA);
		if(!snsName.equals("") && !UMSnsService.isAuthorized(MyApplication.getInstance(), Name.get(snsName)))
		{
			UMSnsService.OauthCallbackListener listener = new UMSnsService.OauthCallbackListener(){
		        public void onComplete(Bundle value, SHARE_TO platform) {
		        	Toast.makeText(context, "绑定成功", Toast.LENGTH_LONG).show();
		        	Intent intent=new Intent("com.example.skylark.monitorservice");
		    		intent.putExtra("blName", blName);
		    		intent.putExtra("snsName", snsName);
		    		intent.putExtra("hour", tp.getCurrentHour());
		    		intent.putExtra("min", tp.getCurrentMinute());
		    		Toast toast=Toast.makeText(context, "开始", Toast.LENGTH_SHORT);
					LinearLayout toastView = (LinearLayout) toast.getView();
					ImageView imageCodeProject = new ImageView(context.getApplicationContext());
					//imageCodeProject.setImageResource(R.drawable.icon);
					//toastView.addView(imageCodeProject, 0);
					toast.show();
					context.startService(intent);
					Countdown.Countdown(context);
					((Activity)context).finish();
		        }
		        public void onError(UMSNSException e, SHARE_TO platform) {
		        	Toast.makeText(context, "对不起，绑定失败，请检查网络设置", Toast.LENGTH_LONG).show();
		        }
			};
			
			//Log.v("my","fore");
			if(snsName.equals("renren"))
			{
				//Toast.makeText(context, "asdf", Toast.LENGTH_LONG).show();
				UMSnsService.oauthRenr(context, listener);
				Log.v("my","renren");
			}
			if(snsName.equals("tencent"))
			{
				UMSnsService.oauthTenc(context, listener);
			}
			if(snsName.equals("sina"))
			{
				UMSnsService.oauthSina(context, listener);
			}
			Countdown.Countdown(context);
			return ;
		}
		else
		{
			Toast toast=Toast.makeText(context, "开始", Toast.LENGTH_SHORT);
			LinearLayout toastView = (LinearLayout) toast.getView();
			ImageView imageCodeProject = new ImageView(context.getApplicationContext());
			//imageCodeProject.setImageResource(R.drawable.icon);
			//toastView.addView(imageCodeProject, 0);
			toast.show();
			context.startService(intent);
			Countdown.Countdown(context);
			((Activity)context).finish();
		}
		//android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	private void startSilenceMode()
	{
		new AlertDialog.Builder(context)
		.setTitle("静默模式")
		.setMessage("确定要开始静默模式吗？静默模式下你将无法使用手机，否则此次尝试就会失败")
		.setPositiveButton("是", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				DevicePolicyManager dpm=(DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
				ComponentName componentName = new ComponentName(context, AdminReceiver.class);
				if(!dpm.isAdminActive(componentName))
				{
					Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
			        ((Activity) context).startActivityForResult(intent, 0);
					
				}
				dpm.lockNow();
				//dpm.removeActiveAdmin(componentName);
				Intent intent=new Intent("com.example.skylark.silencemode");
				intent.putExtra("hour", tp.getCurrentHour());
				intent.putExtra("min", tp.getCurrentMinute());
				blName="";
				saveStatus();
				context.startService(intent);
				Countdown.Countdown(context);
				((Activity)context).finish();
			}
		})
		.setNegativeButton("否",null)
		.show();
		
		
	}
	private void saveStatus()
	{
		Time t=new Time();
		t.setToNow();
		SharedPreferences setting=context.getSharedPreferences("Setting", 0);
		SharedPreferences.Editor editor=setting.edit();
		editor.putInt("Hour", tp.getCurrentHour());
		editor.putInt("Min",tp.getCurrentMinute());
		editor.putInt("sHour", t.hour);
		editor.putInt("sMin",t.minute);
		editor.putInt("sSec", t.second);
		editor.putBoolean("serviceIsActived", true);
		
		editor.putString("blName", blName);
		editor.commit();
	}
	private void iniTime()
	{
		tp=(TimePicker)view.findViewById(R.id.tp);
		tp.setIs24HourView(true);
		SharedPreferences setting=context.getSharedPreferences("Setting", 0);
		tp.setCurrentHour(setting.getInt("Hour", tp.getCurrentHour()));
		tp.setCurrentMinute(setting.getInt("Min", tp.getCurrentMinute()));
	}
	/*
	 * 用以初始化SNS Spinner
	 */
	@SuppressLint("NewApi")
	private void iniSNS()
	{
		if(pop!=null && pop.isShowing())
		{
			pop.dismiss();
			
			return;
		}
		String[] snsNames={"","renren","tencent","sina"};
		int position=context.getSharedPreferences("Setting", 0).getInt("SNS", 0);
		snsName=snsNames[position];
	    
		sns_sp.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(pop!=null && pop.isShowing())
				{
					pop.dismiss();
					return;
				}
				LayoutInflater inflater=(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
				View snsPopView=inflater.inflate(R.layout.sns_pop,null);
				ListView snsList=(ListView)snsPopView.findViewById(R.id.sns_list);
				MyAdapter adapter=new MyAdapter(MyApplication.getInstance(), 
				new int[]{0,R.drawable.renren,R.drawable.tencent,R.drawable.sina},
				new String[]{"不发布","人人网","腾讯微博","新浪微博"},false,R.layout.popup_item);
				snsList.setAdapter(adapter);
				snsList.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String[] snsNames={"","renren","tencent","sina"};
						snsName=snsNames[arg2];
						SharedPreferences setting=context.getSharedPreferences("Setting", 0);
						SharedPreferences.Editor editor=setting.edit();
						editor.putInt("SNS", arg2);
						editor.putString("snsName", snsName);
						editor.commit();
						pop.dismiss();
						WindowManager.LayoutParams lp = (WindowManager.LayoutParams)((Activity) context).getWindow().getAttributes();
						lp.alpha = 1f; //0.0-1.0
				      	((Activity) context).getWindow().setAttributes(lp);
						int[] snsBgs=new int[]{R.drawable.sns_none,R.drawable.sns_renren,R.drawable.sns_tencent,R.drawable.sns_sina};
						sns_sp.setBackgroundResource(snsBgs[setting.getInt("SNS", 0)]);
					}
				});
				WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
				int width = wm.getDefaultDisplay().getWidth();//屏幕宽度
				int height = wm.getDefaultDisplay().getHeight();
				
				pop=new PopupWindow(snsPopView,width*6/7, LayoutParams.WRAP_CONTENT);
				pop.setAnimationStyle(R.style.Animation);
				pop.setBackgroundDrawable(new BitmapDrawable());
				pop.setFocusable(true);
				pop.setOnDismissListener(new OnDismissListener() {
					
					public void onDismiss() {
						// TODO Auto-generated method stub
						//Toast.makeText(context, "asdfads", Toast.LENGTH_LONG).show();
						WindowManager.LayoutParams lp = (WindowManager.LayoutParams)((Activity) context).getWindow().getAttributes();
						lp.alpha = 1f; //0.0-1.0
				      	((Activity) context).getWindow().setAttributes(lp);
					}
				});
			//	pop.setOutsideTouchable(true);
				//pop.showAsDropDown(v);
				pop.showAtLocation(view, Gravity.CENTER, 0, 0);
				WindowManager.LayoutParams lp = (WindowManager.LayoutParams)((Activity) context).getWindow().getAttributes();
				lp.alpha = 0.3f; //0.0-1.0
		      	((Activity) context).getWindow().setAttributes(lp);
				/*
				AlertDialog.Builder builder = new Builder(context);
				builder.setView(snsPopView);
				AlertDialog dialog;
				
				Dialog dialog=new Dialog(context,R.style.dialog);
//				dialog=builder.create();
				dialog.setContentView(snsPopView);
				dialog.setFeatureDrawableResource(0, R.drawable.pop_bg);
				WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
				lp.alpha = 0.8f; //0.0-1.0
				dialog.getWindow().setAttributes(lp);
				dialog.show();
				*/
			}
		});
		int[] snsBgs=new int[]{R.drawable.sns_none,R.drawable.sns_renren,R.drawable.sns_tencent,R.drawable.sns_sina};
		SharedPreferences setting=context.getSharedPreferences("Setting", 0);
		sns_sp.setBackgroundResource(snsBgs[setting.getInt("SNS", 0)]);
        
	}
	
	/*
	 * 用以初始化BL Spinner
	 */
	
	private void iniBL()
	{
		final ArrayList<String> names=new ArrayList<String>();
		//names.add("");
		final ArrayList<Drawable> icons=new ArrayList<Drawable>();
		String blNames = "";
		FileInputStream fin;
		try {
			fin = MyApplication.getInstance().openFileInput(MyApplication.getInstance().getResources().getString(R.string.blListNames));
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
				
		while(blNames.length()>0)
		{
			String name=blNames.substring(0,blNames.indexOf(" "));
			names.add(name);
			blNames=blNames.substring(blNames.indexOf(" ")+1);
			icons.add(context.getResources().getDrawable(R.drawable.stop));
		}
		names.add("自定义");
		icons.add(context.getResources().getDrawable(R.drawable.selfdefine));		
		SharedPreferences setting=context.getSharedPreferences("Setting", 0);
		Editor editor=setting.edit();
		editor.putBoolean("blChanged", false);
		editor.commit();
		bl_sp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(context.getSharedPreferences("Setting", 0).getBoolean("blChanged", false))
				{
					names.clear();
					icons.clear();
					String blNames = "";
					FileInputStream fin;
					try {
						fin = MyApplication.getInstance().openFileInput(MyApplication.getInstance().getResources().getString(R.string.blListNames));
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
							
					while(blNames.length()>0)
					{
						String name=blNames.substring(0,blNames.indexOf(" "));
						names.add(name);
						blNames=blNames.substring(blNames.indexOf(" ")+1);
						icons.add(context.getResources().getDrawable(R.drawable.stop));
					}
					names.add("自定义");
					icons.add(context.getResources().getDrawable(R.drawable.selfdefine));
					SharedPreferences setting=context.getSharedPreferences("Setting", 0);
					Editor editor=setting.edit();
					editor.putBoolean("blChanged", false);
					editor.commit();
			      	bl_sp.setText(names.get(setting.getInt("BL", 0)));
					blName=names.get(setting.getInt("BL", 0));
				}
					
				MyAdapter adapter=new MyAdapter(context, icons, names,false,R.layout.popup_item);
				LayoutInflater inflater=(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
				View blPopView=inflater.inflate(R.layout.sns_pop,null);
				ListView blList=(ListView)blPopView.findViewById(R.id.sns_list);
				blList.setAdapter(adapter);
				blList.setOnItemClickListener(new OnItemClickListener() {
					
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						SharedPreferences setting=context.getSharedPreferences("Setting", 0);
						SharedPreferences.Editor editor=setting.edit();
						editor.putInt("BL", arg2);
						editor.commit();
						if(arg2==arg0.getCount()-1)
						{
							Intent intent=new Intent();
							intent.setClass(MyApplication.getInstance(), DefineBlackList.class);
							fragment.startActivity(intent);
						}
						else 
						{
							blName=names.get(arg2);
							
						}
						pop.dismiss();
						WindowManager.LayoutParams lp = (WindowManager.LayoutParams)((Activity) context).getWindow().getAttributes();
						lp.alpha = 1f; //0.0-1.0
				      	((Activity) context).getWindow().setAttributes(lp);
						bl_sp.setText(blName);
					}
				});
				WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
				int width = wm.getDefaultDisplay().getWidth();//屏幕宽度
				int height = wm.getDefaultDisplay().getHeight();
				height=height*2/3;
//				Toast.makeText(context, blList.getHeight()+"", Toast.LENGTH_LONG).show();
				int nowHeight=0;
				nowHeight=adapter.getView(0, null, blList).getHeight();
				nowHeight=120;
				//Toast.makeText(context, nowHeight+"", Toast.LENGTH_LONG).show();
				if(height>nowHeight*names.size())
				{
					//height=blPopView.getHeight();
					height=nowHeight*names.size();
				}
				pop=new PopupWindow(blPopView,width*6/7, height);
				pop.setBackgroundDrawable(new BitmapDrawable());
				pop.setFocusable(true);
				pop.setOutsideTouchable(true);
				pop.setAnimationStyle(R.style.Animation);
				pop.showAtLocation(view, Gravity.CENTER, 0, 0);
				pop.setOnDismissListener(new OnDismissListener() {
					
					public void onDismiss() {
						// TODO Auto-generated method stub
						//Toast.makeText(context, "asdfads", Toast.LENGTH_LONG).show();
						WindowManager.LayoutParams lp = (WindowManager.LayoutParams)((Activity) context).getWindow().getAttributes();
						lp.alpha = 1f; //0.0-1.0
				      	((Activity) context).getWindow().setAttributes(lp);
					}
				});
				WindowManager.LayoutParams lp = (WindowManager.LayoutParams)((Activity) context).getWindow().getAttributes();
				lp.alpha = 0.3f; //0.0-1.0
		      	((Activity) context).getWindow().setAttributes(lp);
		      	
			}
		});
		
		//bl_sp.setSelection(2);
//		SharedPreferences setting=context.getSharedPreferences("Setting", 0);
		bl_sp.setText(names.get(setting.getInt("BL", 0)));
		blName=names.get(setting.getInt("BL", 0));
	}
	
}
