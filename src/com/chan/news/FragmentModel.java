package com.chan.news;

import android.support.v4.app.Fragment;

/**
 * FragmentModel:Fragmentģ���࣬һ��Fragmentģ�Ͱ���һ�������һ��Fragment
 */
public class FragmentModel {

	public String mTitle = "";
	public Fragment mFragment;
	
	public FragmentModel(String title, Fragment fg){
		mTitle = title;
		mFragment = fg;
	}
}
