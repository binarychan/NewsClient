package com.chan.news.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chan.news.R;

public class ToastUtil {
	
	private Context context;
	private Toast toast;
	
	public ToastUtil(Context context) {
		this.context = context;
	}
	
	public void show(String msg) {
		toast = new Toast(context);
		View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
		TextView tv = (TextView) view.findViewById(R.id.toastText);
		tv.setText(msg);
		toast.setView(view);
		toast.show();
	}

}
