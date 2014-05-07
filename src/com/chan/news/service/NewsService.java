package com.chan.news.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import android.os.Message;
import android.util.Log;

import com.chan.news.http.ThreadSafeHttpClient;
import com.chan.news.model.News;
import com.chan.news.util.JsonUtil;
import com.chan.news.fragments.NewsFragment.GetNewsesHandler;

public class NewsService {
	
	private static final String NEWS_URL = "http://192.168.1.100:8081/News/getNewsesPro"; 

	public List<News> getNewses() {
		
		List<News> newses = new ArrayList<News>();
		
		News news1 = new News(1,"每天轻松一刻","我们挺过天灾，但我们绝不能饶恕人祸"
				,918, "zheshi shili contgent conent");
		
		News news2 = new News(2,"每天轻松一刻","我们挺过天灾，但我们绝不能饶恕人祸"
				,918, "zheshi shili contgent conent");

		News news3 = new News(3,"每天轻松一刻","我们挺过天灾，但我们绝不能饶恕人祸每天轻松一刻我们挺过天灾，但我们绝不能饶恕人祸"
				,918, "zheshi shili contgent conent");
		
		News news4 = new News(4,"每天轻松一刻","我们挺过天灾，但我们绝不能饶恕人祸"
				,918, "zheshi shili contgent conent");
		
		News news5 = new News(5,"每天轻松一刻","我们挺过天灾，但我们绝不能饶恕人祸"
				,918, "zheshi shili contgent conent");
		
		newses.add(news1);
		newses.add(news2);
		newses.add(news3);
		newses.add(news4);
		newses.add(news5);
		
		
		return newses;
	}

	/**
	 * 使用GetNewsTask从服务器端获取返回的JSON格式的字符串
	 */
	public void getJsonStringNewses(final GetNewsesHandler newsHandler) {
		
		Thread workerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				
				try {
					
					HttpClient httpClient = ThreadSafeHttpClient.getInstance();
					HttpGet request = new HttpGet(NEWS_URL);
					String newsesJsonString = httpClient.execute(request, new BasicResponseHandler());
					Log.i("JSON", newsesJsonString);
					
					List<News> newses = JsonUtil.fromJsonToList(newsesJsonString);
					Log.i("JSON", newses.get(0).getTitle());
					Message msg = newsHandler.obtainMessage();
					msg.obj = newses;
					newsHandler.sendMessage(msg);
					
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		workerThread.start();
	}
	
}
