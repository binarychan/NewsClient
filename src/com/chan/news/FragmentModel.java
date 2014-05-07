package com.chan.news;

import android.support.v4.app.Fragment;

/**
 * FragmentModel:Fragment模型类，一个Fragment模型包含一个标题和一个Fragment
 */
public class FragmentModel {

	public String mTitle = "";
	public Fragment mFragment;
	
	public FragmentModel(String title, Fragment fg){
		mTitle = title;
		mFragment = fg;
	}
}
