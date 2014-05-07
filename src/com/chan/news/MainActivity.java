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
		// ���FragmentController
		mFragmentController = FragmentController.getInstance();
		
		// ��ʼ��MainActivity��ͼ
		setupViews();
		
		initData();
	}

	private void setupViews(){
		// setContenView Ϊ һ��FrameLayout
		setContentView(R.layout.main_slidemenu_layout);
		// ��ʼ��ActionBar������
		initActionBar();
		// ��ʼ�������˵�
		initSlideMenu();
	
	}
	
	private void initSlideMenu(){

		// ��û����˵��������ò˵�ģʽΪ����
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		// left_menu_frameͬ��Ϊһ��FrameLayout
		setBehindContentView(R.layout.left_menu_frame);
		sm.setSlidingEnabled(true);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		// ����ActionBar������һ�������������ϲ�home
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// ���FragmentManager������߲˵�����Ϊ����Fragment
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.left_menu_frame, new NavigationFragment())
		.commit();
		
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setBehindScrollScale(0);
		sm.setFadeDegree(0.25f);
		// �����ұ߲˵���ͬ��Ϊһ��FrameLayout
		sm.setSecondaryMenu(R.layout.right_menu_frame);
		sm.setSecondaryShadowDrawable(R.drawable.shadow);
		// ���FragmentManager�����ұ߲˵�����Ϊ����Fragment
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.right_menu_frame, new SettingFragment())
		.commit();
		
		FragmentModel fragmentModel = mFragmentController.getNewsFragmentModel();
		switchContent(fragmentModel);
	}
	
	/**
	 * ��ʼ�����ſͻ�����ҳ��ActionBar������
	 */
	private void initActionBar(){
		ActionBar actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.actionbar_layout);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		// ������Ӧͼ��򿪲˵��ļ�����
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
	 * ��Ӧ���������ϽǺ����ϽǴ򿪲˵���������ť
	 */
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.left_menu_icon:
			toggle(); // ����߲˵�
			break;
		case R.id.right_menu_icon:
			showSecondaryMenu(); // ���ұ߲˵�
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//�ж��û��Ƿ��·��ؼ�
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() 
	    		== KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis() - exitTime) > 2000){
	        	//���û���һ�ΰ��·��ؼ�ʱ����ʾ�ٰ�һ���˳�����
//	            Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�",
//	            		Toast.LENGTH_SHORT).show();  
	            new ToastUtil(this).show("�ٰ�һ���˳�Ӧ��");
	            exitTime = System.currentTimeMillis();   
	        } else {//�ڶ��ΰ��·��ؼ���������ǰActivity���˳�����
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
//			exitBy2Click();      //����˫���˳�����  
//	    }  
//	    return true; 
//	}
//	
//	private static boolean isExit = false;
	
//	private void exitBy2Click() {  
//	    Timer tExit = null;  
//	    if (isExit == false) {  
//	        isExit = true; // ׼���˳�  
//	        Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();  
//	        tExit = new Timer();  
//	        tExit.schedule(new TimerTask() {  
//	            @Override  
//	            public void run() {  
//	                isExit = false; // ȡ���˳�  
//	            }  
//	        }, 2000); // ���2������û�а��·��ؼ�����������ʱ��ȡ�����ղ�ִ�е�����  
//	  
//	    } else {  
//	        finish();  
//	        System.exit(0);  
//	    }  
//	}
	
	
}
