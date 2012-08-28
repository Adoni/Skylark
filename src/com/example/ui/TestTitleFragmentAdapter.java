package com.example.ui;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.widget.PopupWindow;

class TestTitleFragmentAdapter extends TestFragmentAdapter {
    public TestTitleFragmentAdapter(FragmentManager fm,Context context,PopupWindow pop) {
        super(fm,context,pop);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TestFragmentAdapter.CONTENT[position % CONTENT.length];
    }
}