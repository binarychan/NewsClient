package com.chan.news.fragments;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.chan.news.R;
import com.chan.news.adapters.NewsAdapter;
import com.chan.news.model.News;
import com.chan.news.service.NewsService;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class NewsFragment extends Fragment implements OnItemSelectedListener {
	
	private View mView;
	
	private static XListView mListView;
	
	private ViewPager newsViewPager;
//	private PagerTitleStrip newsPagerTitle;
	private List<View> views;
	private List<String> titles;
	
	private ActionBar actionBar;
	
	private static SlidingFragmentActivity mActivity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = getActivity().getLayoutInflater().inflate(R.layout.news_fragment_layout, null);
		// 返回包含一个ViewPager的视图
		newsViewPager = (ViewPager)mView.findViewById(R.id.news_pager);
//		newsPagerTitle = (PagerTitleStrip)mView.findViewById(R.id.news_pager_title);
		
		return mView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = (SlidingFragmentActivity) getActivity();
		// 在活动创建完毕的时候，初始化视图
		setupViews();
	}

	
	private void setupTabNavView() {
		 actionBar = mActivity.getSupportActionBar();
		 FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(mActivity.getSupportFragmentManager()){

			@Override
			public Fragment getItem(int position) {
				Fragment fragment = new CommonFragment();
				Bundle args = new Bundle();
				args.putInt(CommonFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			}

			@Override
			public int getCount() {
				
				return 3;
			}

			@Override
			public CharSequence getPageTitle(int position) {
				switch (position) {
					case 0:
						return "头条";
					case 1:
						return "科技";
					case 2:
						return "体育";
				}
				return null;
			}
			 
		 };
		 actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		 actionBar.setDisplayHomeAsUpEnabled(true);
		 
		// 遍历pagerAdapter对象所包含的全部Fragment。
		// 每个Fragment对应创建一个Tab标签
		for (int i = 0; i < fragmentPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
				.setText(fragmentPagerAdapter.getPageTitle(i))
				.setTabListener(new TabListener() {

					@Override
					public void onTabSelected(Tab tab, FragmentTransaction ft) {
						newsViewPager.setCurrentItem(tab.getPosition());
					}

					@Override
					public void onTabUnselected(Tab tab, FragmentTransaction ft) {
						
					}

					@Override
					public void onTabReselected(Tab tab, FragmentTransaction ft) {
						
					}
					
				}));
		}
		newsViewPager.setAdapter(fragmentPagerAdapter);
		newsViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	public static class GetNewsesHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			List<News> newses = (List<News>) msg.obj;
			NewsAdapter adapter = new NewsAdapter(mActivity, newses); 
			adapter.notifyDataSetChanged();
			mListView.setAdapter(adapter);
		}

	}
	
	/**
	 * 在和该Fragment关联的活动创建好后，为ViewPager设置适配器，
	 * 并加载每一个Pager的ListView，即从网络上获取ListView显示的数据，
	 * 并将数据传给ListView的Adapter完成数据填充显示
	 */
	private void setupViews(){

		View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.news_headline_page, null);
		mListView = (XListView)view1.findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {

			/**
			 * 当用户下拉刷新时响应的回调
			 */
			@Override
			public void onRefresh() {
				
			}

			/**
			 * 当上提加载更多时响应的回调
			 */
			@Override
			public void onLoadMore() {
				
			}
			
		});
		
		mListView.setOnItemSelectedListener(this);
		
		new NewsService().getJsonStringNewses(new GetNewsesHandler());
//		List<News> newses = new NewsService().getNewses();
//		mListView.setAdapter(new NewsAdapter(getActivity(), newses));
		
		TextView view2 = new TextView(getActivity());
		view2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		view2.setText("科技");
		
		TextView view3 = new TextView(getActivity());
		view3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		view3.setText("体育");
		
		TextView view4 = new TextView(getActivity());
		view4.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		view4.setText("经济");
		
		views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		
		titles = new ArrayList<String>();
		titles.add("头条");
		titles.add("科技");
		titles.add("体育");
		titles.add("经济");
		
		newsViewPager.setAdapter(new MyPagerAdapter());
		
		newsViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		newsViewPager.setCurrentItem(0); // 设置ViewPager首先显示第一个Pager
		mActivity.getSlidingMenu()
				 .setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}
	
	private final class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager)container).removeView(views.get(position));
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles.get(position);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager)container).addView(views.get(position));
			return views.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
}
