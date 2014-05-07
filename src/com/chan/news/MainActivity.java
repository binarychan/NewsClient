package com.chan.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.chan.news.fragments.NavigationFragment;
import com.chan.news.fragments.SettingFragment;
import com.chan.news.util.ToastUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements OnClickListener {

	private String mTitle;
	private Fragment mContent;
	
	private ImageView mLeftIcon;
	private ImageView mRightIcon;
	private TextView mTitleTextView;
	
	private FragmentController mFragmentController;
	private long exitTime = 0;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获得FragmentController
		mFragmentController = FragmentController.getInstance();
		
		// 初始化MainActivity视图
		setupViews();
		
		initData();
	}

	private void setupViews(){
		// setContenView 为 一个FrameLayout
		setContentView(R.layout.main_slidemenu_layout);
		// 初始化ActionBar操作栏
		initActionBar();
		// 初始化滑动菜单
		initSlideMenu();
	
	}
	
	private void initSlideMenu(){

		// 获得滑动菜单，并设置菜单模式为左右
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		// left_menu_frame同样为一个FrameLayout
		setBehindContentView(R.layout.left_menu_frame);
		sm.setSlidingEnabled(true);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		// 设置ActionBar返回上一级，而不是最上层home
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// 获得FragmentManager，将左边菜单设置为导航Fragment
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.left_menu_frame, new NavigationFragment())
		.commit();
		
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setBehindScrollScale(0);
		sm.setFadeDegree(0.25f);
		// 设置右边菜单，同样为一个FrameLayout
		sm.setSecondaryMenu(R.layout.right_menu_frame);
		sm.setSecondaryShadowDrawable(R.drawable.shadow);
		// 获得FragmentManager，将右边菜单设置为设置Fragment
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.right_menu_frame, new SettingFragment())
		.commit();
		
		FragmentModel fragmentModel = mFragmentController.getNewsFragmentModel();
		switchContent(fragmentModel);
	}
	
	/**
	 * 初始化新闻客户端首页的ActionBar操作栏
	 */
	private void initActionBar(){
		ActionBar actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.actionbar_layout);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		// 设置响应图标打开菜单的监听器
		mLeftIcon = (ImageView) findViewById(R.id.left_menu_icon);
		mRightIcon = (ImageView) findViewById(R.id.right_menu_icon);
		mLeftIcon.setOnClickListener(this);
		mRightIcon.setOnClickListener(this);
		
		mTitleTextView = (TextView) findViewById(R.id.actionbar_title);
	}
	
	private void initData(){
		
	}
	
	public void switchContent(FragmentModel fragment) {
		mTitle = fragment.mTitle;
		mContent = fragment.mFragment;

		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 0);
		mTitleTextView.setText(mTitle);
	}
	
	/**
	 * 响应操作栏左上角和右上角打开菜单的两个按钮
	 */
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.left_menu_icon:
			toggle(); // 打开左边菜单
			break;
		case R.id.right_menu_icon:
			showSecondaryMenu(); // 打开右边菜单
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//判断用户是否按下返回键
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() 
	    		== KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis() - exitTime) > 2000){
	        	//当用户第一次按下返回键时，提示再按一次退出程序
//	            Toast.makeText(getApplicationContext(), "再按一次退出",
//	            		Toast.LENGTH_SHORT).show();  
	            new ToastUtil(this).show("再按一次退出应用");
	            exitTime = System.currentTimeMillis();   
	        } else {//第二次按下返回键，结束当前Activity，退出程序
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(keyCode == KeyEvent.KEYCODE_BACK) {    
//			exitBy2Click();      //调用双击退出函数  
//	    }  
//	    return true; 
//	}
//	
//	private static boolean isExit = false;
	
//	private void exitBy2Click() {  
//	    Timer tExit = null;  
//	    if (isExit == false) {  
//	        isExit = true; // 准备退出  
//	        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();  
//	        tExit = new Timer();  
//	        tExit.schedule(new TimerTask() {  
//	            @Override  
//	            public void run() {  
//	                isExit = false; // 取消退出  
//	            }  
//	        }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  
//	  
//	    } else {  
//	        finish();  
//	        System.exit(0);  
//	    }  
//	}
	
	
}
