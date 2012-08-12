/*
 * 用于定义自己的Adapter，既为SNS显示准备，也为黑名单做准备
 */
package com.example.skylark;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter
{
	private Context context;
	private int iconIDs[];
	private ArrayList<Drawable> icons=new ArrayList<Drawable>();
	//private Drawable[] icons;
	private String names[];
	private boolean haveACheckBox;
	private HashMap<Integer, Boolean> isSelected=new HashMap<Integer, Boolean>();
	private CheckBox check;
	/*
	 * 用于使用Resources进行构造的构造函数。
	 */
	public MyAdapter(Context context,int iconIDs[],String names[],boolean haveACheckBox)
	{
		Drawable d;
		this.context=context;
		this.iconIDs=iconIDs;
		for(int i=0;i<iconIDs.length;i++)
		{
			//icons[i]=context.getResources().getDrawable(iconIDs[i]);
			//icons[i]=context.getResources().getDrawable(R.drawable.friends);
			//Toast.makeText(context, ""+R.drawable.renren, Toast.LENGTH_LONG).show();
			
			int s=iconIDs[i];
			
			if(s!=0)
			{
				d=context.getResources().getDrawable(s);
				icons.add(d);
			}
			else icons.add(context.getResources().getDrawable(R.drawable.friends));
			//Toast.makeText(context, icons.size()+"", Toast.LENGTH_LONG).show();
		}
		this.names=names;
		this.haveACheckBox=haveACheckBox;
		iniData();
	}
	/*
	 * 用于使用drawable数组进行构造的构造函数
	 */
	public MyAdapter(Context context,ArrayList<Drawable> icons,String names[],boolean haveACheckBox)
	{
		Log.v("my","f");
		this.context=context;
		this.icons=icons;
		this.names=names;
		this.haveACheckBox=haveACheckBox;
		iniData();
	}
	
	/*
	 * 初始化多选数组。
	 */
	public void iniData()
	{
		for(Integer i=0;i<icons.size();i++)
		{
			isSelected.put(i, false);
		}
	}
	
	/*
	 * 以下四个方法是BaseAdapter必须实现的。
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount()
	{
		return icons.size();
	}
	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position)
	{
		//return iconIDs[position];
		return icons.get(position);
	}
	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position)
	{
		return position;
	}
	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position,View convertView,ViewGroup parent)
	{
		LinearLayout ll=new LinearLayout(context);
		vHolder vholder=new vHolder();
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setGravity(Gravity.CENTER_VERTICAL);
		
		ImageView icon=new ImageView(context);
		//icon.setImageResource(iconIDs[position]);
		icon.setImageDrawable(icons.get(position));
		icon.setLayoutParams(new ViewGroup.LayoutParams(60,60));
		ll.addView(icon);
		vholder.img=icon;
		
		TextView name=new TextView(context);
		name.setText(names[position]);
		name.setTextSize(20);
		name.setTextColor(Color.BLUE);
		ll.addView(name);
		vholder.txt=name;
		
		if(haveACheckBox)
		{
			CheckBox check=new CheckBox(context);
			check.setChecked(isSelected.get(position));
			check.setGravity(Gravity.CENTER_HORIZONTAL);
			//check.setClickable(false);
			check.setFocusable(false);
			this.check=check;
			//check.setPadding(0, 0, 100, 0);
			ll.addView(check);
			vholder.cb=check;
		}
		//convertView.setTag(vholder)
		return ll;
	}
	public HashMap<Integer,Boolean> getIsSelected()
	{
		return isSelected;
	}
	public void setIsSelected(HashMap<Integer,Boolean> isSelected)
	{
		this.isSelected=isSelected;
	}
	public void setIsSelected(int position)
	{
		isSelected.put(position,isSelected.get(position) ^ true);
		check.setChecked(true);
		Toast.makeText(context, check.isChecked()+"", Toast.LENGTH_LONG).show();
	}
	public void add(int iconID,String name)
	{
		iconIDs[icons.size()]=iconID;
		names[icons.size()]=name;
		icons.add(context.getResources().getDrawable(iconID));
	}
	public void add(Drawable icon,String name)
	{
		icons.add(icon);
		names[icons.size()]=name;
	}
	public String getName(int position)
	{
		return names[position];
	}
	
	public final class vHolder
	{
		public ImageView img;
		public TextView txt;
		public CheckBox cb;
	}
}
