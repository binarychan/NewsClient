package com.chan.news.fragments;

import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chan.news.R;
import com.chan.news.adapters.NewsAdapter;
import com.chan.news.model.News;
import com.chan.news.service.NewsService;

public class CommonFragment extends Fragment {

	private XListView mListView;
	
	public ImageView mImageView;
	
	public static final String ARG_SECTION_NUMBER = "selected_tab_num";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.common_fragment_layout, null);
		mImageView = (ImageView) view.findViewById(R.id.imageView);
		
		Bundle args = getArguments();
		if(args != null) {
			int tabNum = args.getInt(ARG_SECTION_NUMBER);
			switch(tabNum) {
			case 1:
				View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.news_headline_page, null);
				mListView = (XListView)view1.findViewById(R.id.xListView);
				mListView.setPullLoadEnable(true);
				mListView.setXListViewListener(new IXListViewListener() {

					@Override
					public void onRefresh() {
						
						
					}

					@Override
					public void onLoadMore() {
						
						
					}
					
				});
				
				List<News> newses = new NewsService().getNewses();
				
				mListView.setAdapter(new NewsAdapter(getActivity(), newses));
				return view1;
				
			case 2:
				break;
			case 3:
				break;
			}
		}
		
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
