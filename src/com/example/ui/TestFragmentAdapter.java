package com.example.ui;

import com.example.skylark.R;
import com.example.skylark.R.layout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

class TestFragmentAdapter extends FragmentPagerAdapter {
    protected static final String[] CONTENT = new String[] { "黑名单", "开始规划", "设置",};
    private int[] layout=new int[]{R.layout.blacklist,R.layout.plan,R.layout.setting};
    private int mCount = CONTENT.length;
    private Context context;

    public TestFragmentAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }
    //主要的设定界面的方法
    @Override
    public Fragment getItem(int position) {
        //return TestFragment.newInstance("god ");
        TestFragment fragment=new TestFragment();
        //return null;
    	fragment.setLayout(layout[position],context);
    	return fragment;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}