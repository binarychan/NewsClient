package com.chan.news.test;

import java.util.Iterator;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.test.AndroidTestCase;
import android.util.Log;

import com.chan.news.model.News;
import com.chan.news.util.JsonUtil;
import com.chan.news.util.LocationUtil;
import com.chan.news.util.WebServiceUtil;

public class TestMethods extends AndroidTestCase {
	
	private static final String TAG = "TestMethods";
	
	public void testLocation() {
		new LocationUtil(this.getContext()).getCurrentCityName();
		
	}
	
	
	public void testWebService() {
		
		SoapObject detail = WebServiceUtil.getWeatherByCity("����");
		// ��ȡ����ʵ��
		String weatherState = detail.getProperty(4).toString();
		
		String date = detail.getProperty(7).toString();
		
		String weatherToday = "���죺" + date.split(" ")[0];
		weatherToday = weatherToday + "\n������" + date.split(" ")[1];
		weatherToday = weatherToday + "\n���£�"
			+ detail.getProperty(8).toString();
		
		Log.i(TAG, weatherState);
		Log.i(TAG, date);
		Log.i(TAG, weatherToday);
	}
	
	public void testJson() {
		String newsesJsonString = "{\"newses\":[{\"content\":\"��������3\",\"followNum\":13,\"id\":5,\"title\":\"���ű���3\"},{\"content\":\"��������4\",\"followNum\":14,\"id\":6,\"title\":\"���ű���4\"},{\"content\":\"��������5\",\"followNum\":15,\"id\":7,\"title\":\"����5\"}]}";
		List<News> newses = JsonUtil.fromJsonToList(newsesJsonString);
		
		Iterator<News> it = newses.iterator();
		while(it.hasNext()) {
			Log.i(TAG,it.next().toString());
		}
	}

}
