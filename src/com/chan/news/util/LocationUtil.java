package com.chan.news.util;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.chan.news.http.ThreadSafeHttpClient;

public class LocationUtil {

	private Context context;
	
	public LocationUtil(Context context) {
		this.context = context;
	}
	
	private Location getCurrentLocation() {
		LocationManager locMgr = (LocationManager) 
				context.getSystemService(Context.LOCATION_SERVICE);
		
		Location loc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if(loc == null) {
			loc = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		
		return loc;
	}
	
	public String requestForCityInfo() {
		Location loc = getCurrentLocation();
		double longtitude = loc.getLongitude();
		double latitude = loc.getLatitude();
		
		final String requestURL = 
				"http://maps.google.com/maps/api/geocode/json?latlng=" 
				+ latitude + "," + longtitude + "&language=zh-CN&sensor=true";
		
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						//ªÒ»°HttpClient
						HttpClient httpClient = ThreadSafeHttpClient.getInstance();
						HttpGet request = new HttpGet(requestURL);
					
						String result = httpClient.execute(request,
								new BasicResponseHandler());
						
						return result;
					}
					
				}
		);
		new Thread(task).start();
		try {
			return task.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCurrentCityName() {
		
		String result = requestForCityInfo();
		
		String cityName = JsonUtil.parseCityNameFromJson(result);
		
		return cityName;
	}
	
}
