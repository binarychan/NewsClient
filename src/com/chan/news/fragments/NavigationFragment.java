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
 * NavigationFragment˵����Ϣ����䵽��߻����˵���Fragment
 * �����˵������š�ͼƬ����Ƶ������
 */
public class NavigationFragment extends Fragment implements OnCheckedChangeListener {
	
	private View mView; // ����Fragmentʹ�õ���ͼ��Դ
	private RadioGroup  m_radioGroup; // �����˵�����һ��RadioGroup�����˵�����
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
		// ���RadioGroup������
		m_radioGroup = (RadioGroup) mView.findViewById(R.id.nav_radiogroup);
		((RadioButton) m_radioGroup.getChildAt(0)).toggle();
		// ��ӦRadioGroup��ÿ��RadioButton�ĵ���¼�
		m_radioGroup.setOnCheckedChangeListener(this);
	}

	/**
	 * ���ص����˵���ͼ������ͼ��������Fragmentʹ��
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
