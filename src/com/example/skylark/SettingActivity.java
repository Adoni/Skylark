/*
 * 用于显示设置界面，包括：
 * 设置音量大小，以及是否有提示音
 * 设置是否有“宠物警告”
 * 设置总体评价发布周期
 * 设置勾选是否自动登录当前帐户
 * 设置默认登陆的社交网络及账号，密码
 * 关于：介绍主要开发者及版本信息
 */
package com.example.skylark;

import android.app.Activity;
import android.os.Bundle;

public class SettingActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
	}
}
