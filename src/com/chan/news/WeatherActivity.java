package com.chan.news;

import org.ksoap2.serialization.SoapObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.chan.news.util.LocationUtil;
import com.chan.news.util.ToastUtil;
import com.chan.news.util.WebServiceUtil;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


public class WeatherActivity extends SlidingFragmentActivity implements OnClickListener{

	private ActionBar actionBar;
	private ImageView backImage;
	private ImageView selectCityImage;
	private ImageView shareImage;
	private TextView cityTextView;
	
	private ImageView weatherStateImage;
	private TextView temperatureTextView;
	private TextView weatherStateTextView;
	
	private WeatherHandler mHandler;
	private Thread workerThread;
	
	private View loadingLayout;

	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		// ��ʼ������
		setupViews();
		// ����������Ϣ
		initWeatherData2();
		
	}
	
	class WeatherHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			SoapObject detail = (SoapObject) msg.obj;
			String cityName = msg.getData().getString("cityName");
			updateViews(detail,cityName);
		}
		
	}
	
	private void updateViews(SoapObject detail, String cityName) {
		// ��ȡ����ʵ��
		String weatherState = detail.getProperty(4).toString();
		String date = detail.getProperty(7).toString();
		String time = date.split(" ")[0]; // ����3��30��
		String state = date.split(" ")[1]; // ���ƣ��󵽱���
		String temperature = detail.getProperty(8).toString(); // 20��/24��
		
		cityTextView.setText(cityName);
		weatherStateTextView.setText(time + "\n" + state);
		temperatureTextView.setText(temperature);
		
		loadingLayout.setVisibility(View.GONE);
	}
	
	private void initWeatherData2() {

		// �ڹ����߳��д���Handler��HandlerΪ��Ϣ�����ߣ��⽫��ζ����Ϣ������������߳��ϱ�����
//		mHandler = new WeatherHandler();
		final Handler weatherHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				SoapObject detail = (SoapObject) msg.obj;
				String cityName = msg.getData().getString("cityName");
				updateViews(detail,cityName);
			}
			
		};

		workerThread = new Thread(new Runnable(){

			@Override
			public void run() {
				
				String cityName = new LocationUtil(WeatherActivity.this).getCurrentCityName();
				SoapObject detail = WebServiceUtil.getWeatherByCity(cityName);
//				Message msg = mHandler.obtainMessage();
				Message msg = weatherHandler.obtainMessage();
				msg.obj = detail;
				Bundle data = new Bundle();
				data.putString("cityName", cityName);
				msg.setData(data);
				weatherHandler.sendMessage(msg); // handler������Ϣ
			}
			
		});
		workerThread.start();
	}
	
	private void initWeatherData() {
		// ��λ������
		String cityName = new LocationUtil(this).getCurrentCityName();
		// ���ݳ���������������
		SoapObject detail = WebServiceUtil.getWeatherByCity(cityName);
		// ��ȡ����ʵ��
		String weatherState = detail.getProperty(4).toString();
		
		String date = detail.getProperty(7).toString();
		
		String weatherToday = "���죺" + date.split(" ")[0];
		weatherToday = weatherToday + "\n������" + date.split(" ")[1];
		weatherToday = weatherToday + "\n���£�"
			+ detail.getProperty(8).toString();
		
		cityTextView.setText(cityName + "����");
		
		weatherStateTextView.setText(weatherState);
		temperatureTextView.setText(weatherToday);
		
	}

	private void setupViews() {
		setContentView(R.layout.weather_report_layout);
		
		initActionBar();
		
		weatherStateImage = (ImageView)findViewById(R.id.weather_report_pic);
		temperatureTextView = (TextView)findViewById(R.id.text_temperature);
		weatherStateTextView = (TextView)findViewById(R.id.text_weather_state);
		loadingLayout = findViewById(R.id.loading_layout);
		setBehindContentView(R.layout.main_slidemenu_layout);
	}
	
	private void initActionBar(){
		actionBar = getSupportActionBar();
		View view = LayoutInflater.from(this).inflate(R.layout.weather_action_bar_layout, null);
		actionBar.setCustomView(view);
//		actionBar.setCustomView(R.layout.weather_action_bar_layout);
		// ���÷�����һ��
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		
		// ������Ӧͼ��򿪲˵��ļ�����
		backImage = (ImageView) view.findViewById(R.id.back_to_prelevel_image);
		selectCityImage = (ImageView) view.findViewById(R.id.select_city_image);
		shareImage = (ImageView) view.findViewById(R.id.share_image);
		cityTextView = (TextView) view.findViewById(R.id.city_weather_text);
		
		
		backImage.setOnClickListener(this);
		selectCityImage.setOnClickListener(this);
		shareImage.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.back_to_prelevel_image:
			this.finish();
			break;
		case R.id.select_city_image:
			new ToastUtil(this).show("�д����");
			break;
		case R.id.share_image:
			new ToastUtil(this).show("�д����");
			break;
		}
		
	}
	
}
