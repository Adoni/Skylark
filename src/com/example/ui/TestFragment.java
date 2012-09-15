package com.example.ui;

import com.example.skylark.R;
import com.example.skylark.R.layout;

import junit.framework.TestResult;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public final class TestFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private int layout;
    private Context context;
    private PopupWindow pop;
    
    public void setLayout(int layout,Context context, PopupWindow pop)
    {
    	this.layout=layout;
    	this.context=context;
    	this.pop=pop;
    }
    
    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       
    	//LinearLayout mylayout=null;
    	//mylayout=(LinearLayout)inflater.inflate(layout,container,false);
        //return mylayout;
		View ans=inflater.inflate(layout, container,false);
        //return inflater.inflate(layout,container,false);
		if(layout==R.layout.plan)
		{
			PlanInitialize plan=new PlanInitialize(ans,this,context,pop);
			plan.iniPlan();
		}
		if(layout==R.layout.blacklist)
		{
			BLInitialize bl=new BLInitialize(ans,this,context);
			bl.initial();
		}
		if(layout==R.layout.setting)
		{
			SettingInitialize si=new SettingInitialize(ans, this, context,pop);
			si.iniSetting();
		}
        return ans;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
