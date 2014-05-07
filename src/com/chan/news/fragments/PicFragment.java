package com.chan.news.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chan.news.R;

public class PicFragment extends CommonFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setupViews();
	}

	
	private void setupViews(){
		mImageView.setBackgroundResource(R.drawable.icon_nav_item_pic);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
