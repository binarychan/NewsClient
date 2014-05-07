package com.chan.news;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.chan.news.fragments.FollowFragment;
import com.chan.news.fragments.NewsFragment;
import com.chan.news.fragments.PicFragment;
import com.chan.news.fragments.VideoFragment;

/**
 * Fragment������������ʵ��Ϊһ��������
 * ��Ϊjava���ƹ涨���ڲ���FragmentControllerHolderֻ����getInstance()������һ�ε��õ�ʱ��
 * �Żᱻ���أ�ʵ����lazy������������ع������̰߳�ȫ�ģ�ʵ���̰߳�ȫ����
 * �ڲ�����ص�ʱ��ʵ����һ��instance��
 */
public class FragmentController {

	private static Context mContext;
	
	private Map<String, FragmentModel> mFragmentModelMaps = 
			new HashMap<String, FragmentModel>();

	private FragmentController(Context context) {
		mContext = context;
	}
	
	// ʹ���༶�ڲ�����ʵ�ֵ���ģʽ
	private static class FragmentControllerHolder {
		private static FragmentController instance = new FragmentController(mContext);
	}
	
	public static FragmentController getInstance() {
		return FragmentControllerHolder.instance;
	}
	
	public FragmentModel getFragmentModel(String name){
		return mFragmentModelMaps.get(name);
	}

	public void putFragmentModel(String name,FragmentModel fragment){
		mFragmentModelMaps.put(name, fragment);
	}
	
	/**
	 * ʹ�ø÷���ʵ�ֻ�ö�Ӧ��Fragmentģ��
	 */
	public FragmentModel getNewsFragmentModel() {
		FragmentModel fragmentModel = mFragmentModelMaps.get(FragmentBuilder.NEWS_FRAGMENT);
		if (fragmentModel == null){
			fragmentModel = FragmentBuilder.getNewsFragmentModel();
			mFragmentModelMaps.put(FragmentBuilder.NEWS_FRAGMENT, fragmentModel);
		}
		return fragmentModel;
	}
	
	public FragmentModel getPicFragmentModel() {
		FragmentModel fragmentModel = mFragmentModelMaps.get(FragmentBuilder.PIC_FRAGMENT);
		if (fragmentModel == null){
			fragmentModel = FragmentBuilder.getPicFragmentModel();
			mFragmentModelMaps.put(FragmentBuilder.PIC_FRAGMENT, fragmentModel);
		}
		return fragmentModel;
	}
	
	public FragmentModel getVideoFragmentModel() {
		FragmentModel fragmentModel = mFragmentModelMaps.get(FragmentBuilder.VIDEO_FRAGMENT);
		if (fragmentModel == null){
			fragmentModel = FragmentBuilder.getVideoFragmentModel();
			mFragmentModelMaps.put(FragmentBuilder.VIDEO_FRAGMENT, fragmentModel);
		}
		return fragmentModel;
	}
	
	public FragmentModel getFollowFragmentModel() {
		FragmentModel fragmentModel = mFragmentModelMaps.get(FragmentBuilder.FOLLOW_FRAGMENT);
		if (fragmentModel == null){
			fragmentModel = FragmentBuilder.getFollowFragmentModel();
			mFragmentModelMaps.put(FragmentBuilder.FOLLOW_FRAGMENT, fragmentModel);
		}
		return fragmentModel;
	}
	
	public static class FragmentBuilder{
		public static final String NEWS_FRAGMENT = "NewsFragment";
		public static final String PIC_FRAGMENT = "PicFragment";
		public static final String VIDEO_FRAGMENT = "VideoFragment";
		public static final String FOLLOW_FRAGMENT = "FollowFragment";
		
		
		public static FragmentModel  getNewsFragmentModel(){
			NewsFragment fragment = new NewsFragment();
			FragmentModel fragmentModel = new FragmentModel("����", fragment);
			return fragmentModel;
		}
		
		public static FragmentModel getPicFragmentModel(){
			PicFragment fragment = new PicFragment();
			FragmentModel fragmentModel = new FragmentModel("ͼƬ", fragment);
			return fragmentModel;
		}
		
		public static FragmentModel getVideoFragmentModel(){
			VideoFragment fragment = new VideoFragment();
			FragmentModel fragmentModel = new FragmentModel("��Ƶ", fragment);
			return fragmentModel;
		}
		
		public static FragmentModel getFollowFragmentModel(){
			FollowFragment fragment = new FollowFragment();
			FragmentModel fragmentModel = new FragmentModel("����", fragment);
			return fragmentModel;
		}
	}
	
}
