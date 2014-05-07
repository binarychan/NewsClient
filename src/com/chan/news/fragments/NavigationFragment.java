package com.chan.news.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.chan.news.FragmentController;
import com.chan.news.FragmentModel;
import com.chan.news.MainActivity;
import com.chan.news.R;

/**
 * NavigationFragment说明信息：填充到左边滑动菜单的Fragment
 * 导航菜单：新闻、图片、视频、跟帖
 */
public class NavigationFragment extends Fragment implements OnCheckedChangeListener {
	
	private View mView; // 导航Fragment使用的视图资源
	private RadioGroup  m_radioGroup; // 导航菜单中用一个RadioGroup包含菜单内容
	private FragmentController mFragmentController;

	public NavigationFragment(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFragmentController = FragmentController.getInstance();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViews();
	}
	
	private void setupViews(){
		// 获得RadioGroup的引用
		m_radioGroup = (RadioGroup) mView.findViewById(R.id.nav_radiogroup);
		((RadioButton) m_radioGroup.getChildAt(0)).toggle();
		// 响应RadioGroup中每个RadioButton的点击事件
		m_radioGroup.setOnCheckedChangeListener(this);
	}

	/**
	 * 返回导航菜单视图，该视图将被导航Fragment使用
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.navigation_menu_layout, null);
		return mView;
	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int id) {
		switch(id) {
		case R.id.nav_item_news:
			goNewsFragment();
			break;
		case R.id.nav_item_pic:
			goPicFragment();
			break;
		case R.id.nav_item_video:
			goVideoFragment();
			break;
		case R.id.nav_item_follow:
			goFollowFragment();
			break;
		}
	}
	
	private void goNewsFragment(){
		if (getActivity() == null)
			return;

		FragmentModel fragmentModel = mFragmentController.getNewsFragmentModel();
		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragmentModel);
		}
	}
	
	private void goPicFragment(){
		if (getActivity() == null)
			return;
		
		FragmentModel fragmentModel = mFragmentController.getPicFragmentModel();
		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragmentModel);
		}
	}
	
	private void goVideoFragment(){
		if (getActivity() == null)
			return;

		FragmentModel fragmentModel = mFragmentController.getVideoFragmentModel();
		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragmentModel);
		}
	}
	
	private void goFollowFragment(){
		if (getActivity() == null)
			return;

		FragmentModel fragmentModel = mFragmentController.getFollowFragmentModel();
		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragmentModel);
		}
	}
	
}
