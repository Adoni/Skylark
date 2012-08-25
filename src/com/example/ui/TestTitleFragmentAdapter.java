package com.example.ui;

import android.content.Context;
import android.support.v4.app.FragmentManager;

class TestTitleFragmentAdapter extends TestFragmentAdapter {
    public TestTitleFragmentAdapter(FragmentManager fm,Context context) {
        super(fm,context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TestFragmentAdapter.CONTENT[position % CONTENT.length];
    }
}