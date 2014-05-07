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
		
		SoapObject detail = WebServiceUtil.getWeatherByCity("广州");
		// 获取天气实况
		String weatherState = detail.getProperty(4).toString();
		
		String date = detail.getProperty(7).toString();
		
		String weatherToday = "今天：" + date.split(" ")[0];
		weatherToday = weatherToday + "\n天气：" + date.split(" ")[1];
		weatherToday = weatherToday + "\n气温："
			+ detail.getProperty(8).toString();
		
		Log.i(TAG, weatherState);
		Log.i(TAG, date);
		Log.i(TAG, weatherToday);
	}
	
	public void testJson() {
		String newsesJsonString = "{\"newses\":[{\"content\":\"新闻正文3\",\"followNum\":13,\"id\":5,\"title\":\"新闻标题3\"},{\"content\":\"新闻正文4\",\"followNum\":14,\"id\":6,\"title\":\"新闻标题4\"},{\"content\":\"新闻正文5\",\"followNum\":15,\"id\":7,\"title\":\"新闻5\"}]}";
		List<News> newses = JsonUtil.fromJsonToList(newsesJsonString);
		
		Iterator<News> it = newses.iterator();
		while(it.hasNext()) {
			Log.i(TAG,it.next().toString());
		}
	}

}
