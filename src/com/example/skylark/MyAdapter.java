/*
 * 用于定义自己的Adapter，既为SNS显示准备，也为黑名单做准备
 */
package com.example.skylark;

import java.util.ArrayList;

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
	private Drawable d;
	
	public MyAdapter(Context context,int iconIDs[],String names[],boolean haveACheckBox)
	{
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
	}
	public MyAdapter(Context context,ArrayList<Drawable> icons,String names[],boolean haveACheckBox)
	{
		Log.v("my","f");
		this.context=context;
		this.icons=icons;
		this.names=names;
		this.haveACheckBox=haveACheckBox;
	}
	public int getCount()
	{
		return icons.size();
	}
	public Object getItem(int position)
	{
		//return iconIDs[position];
		return icons.get(position);
	}
	public long getItemId(int position)
	{
		return position;
	}
	
	public View getView(int position,View convertView,ViewGroup parent)
	{
		LinearLayout ll=new LinearLayout(context);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setGravity(Gravity.CENTER_VERTICAL);
		
		ImageView icon=new ImageView(context);
		//icon.setImageResource(iconIDs[position]);
		icon.setImageDrawable(icons.get(position));
		icon.setLayoutParams(new ViewGroup.LayoutParams(60,60));
		ll.addView(icon);
		
		TextView name=new TextView(context);
		name.setText(names[position]);
		name.setTextSize(20);
		name.setTextColor(Color.BLUE);
		ll.addView(name);
		
		if(haveACheckBox)
		{
			CheckBox check=new CheckBox(context);
			check.setChecked(false);
			check.setGravity(Gravity.CENTER_HORIZONTAL);
			//check.setPadding(0, 0, 100, 0);
			ll.addView(check);
		}
		
		return ll;
	}
	public void add(int iconID,String name)
	{
		iconIDs[iconIDs.length]=iconID;
		names[names.length]=name;
	}
}
