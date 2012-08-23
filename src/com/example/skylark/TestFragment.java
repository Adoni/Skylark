package com.example.skylark;

import com.example.ui.BLInitialize;
import com.example.ui.PlanInitialize;

import junit.framework.TestResult;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public final class TestFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private int layout;
    
    public void setLayout(int layout)
    {
    	this.layout=layout;
    }
    public TestFragment newLayout(int layout)
    {
    	TestFragment myfragment=new TestFragment();
    	this.layout=layout;
    	return myfragment;
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
			PlanInitialize plan=new PlanInitialize(ans,this);
			plan.iniPlan();
		}
		if(layout==R.layout.blacklist)
		{
			BLInitialize bl=new BLInitialize(ans,this);
			bl.initial();
		}
        return ans;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
