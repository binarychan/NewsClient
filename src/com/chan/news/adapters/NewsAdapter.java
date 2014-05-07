package com.chan.news.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chan.news.R;
import com.chan.news.cache.ImageLoader;
import com.chan.news.model.News;

public class NewsAdapter extends BaseAdapter {
	
	private Context context;
	private List<News> newses;
	
	private ImageLoader imageLoader;
	
	private static final String SERVER_URL 
		= "http://192.168.1.100:8081/News";
	
	public NewsAdapter(Context context, List<News> newses) {
		this.context = context;
		this.newses = newses;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		
		return newses.size();
	}

	@Override
	public Object getItem(int position) {
		
		return newses.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TextView titleView = null;
		TextView summaryView = null;
		TextView followNumView = null;
		ImageView imageView = null;
		
		if(convertView == null) {// 屏幕上第一页显示的条目，会被第二页的重用的View
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
			titleView = (TextView) convertView.findViewById(R.id.news_title);
			summaryView = (TextView) convertView.findViewById(R.id.news_summary);
			followNumView = (TextView) convertView.findViewById(R.id.news_follow_num);
			imageView = (ImageView) convertView.findViewById(R.id.news_image);
			
			ViewCache cache = new ViewCache();
			cache.titleView = titleView;
			cache.summaryView = summaryView;
			cache.followNumView = followNumView;
			cache.imageView = imageView;
			convertView.setTag(cache);
		} else {
			ViewCache cache = (ViewCache) convertView.getTag();
			titleView = cache.titleView;
			summaryView = cache.summaryView;
			followNumView = cache.followNumView;
			imageView = cache.imageView;
		}
		
		News news = newses.get(position);
		titleView.setText(news.getTitle());
		summaryView.setText(news.getSummary());
		followNumView.setText(String.valueOf(news.getFollowNum()) + "跟帖");
		imageLoader.DisplayImage(SERVER_URL + news.getPicUrl(), imageView);
		
		return convertView;
	}

	private final class ViewCache {
		public TextView titleView;
		public TextView summaryView;
		public TextView followNumView;
		public ImageView imageView;
	}
}
