package com.chan.news.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chan.news.R;
import com.chan.news.WeatherActivity;

public class SettingFragment extends Fragment implements OnClickListener {

	private Context context;
	private LinearLayout layout;
	
	public SettingFragment(){
		
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.setting_layout, null);
		layout = (LinearLayout) view.findViewById(R.id.weather_item);
		layout.setOnClickListener(this);
		return view;
	}
	
	
	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.weather_item:
			Intent intent = new Intent(context, WeatherActivity.class);
			context.startActivity(intent);
			break;
		}
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViews();
	}
	
	private void setupViews(){
		context = getActivity();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
