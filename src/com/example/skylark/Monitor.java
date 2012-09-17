package com.example.skylark;

public class Monitor {
	public Monitor()
	{
		
	}
	
	
	/*
	 * 用于在用户触犯规则时，杀死进程，当前先实现提醒和阻碍
	 * 若成功则返回真，否则返回假
	 */
	boolean killProcess(String processName)
	{
		return true;
	}
	
	/*
	 * 用于在SNS上发布状态，使用的是泽辰提供的API
	 * 若成功则返回真，否则返回假
	 */
	boolean publishAtSNS()
	{
		return true;
	}
}
