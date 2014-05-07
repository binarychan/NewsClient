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
		// 初始化界面
		setupViews();
		// 加载天气信息
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
		// 获取天气实况
		String weatherState = detail.getProperty(4).toString();
		String date = detail.getProperty(7).toString();
		String time = date.split(" ")[0]; // 类似3月30日
		String state = date.split(" ")[1]; // 类似：大到暴雨
		String temperature = detail.getProperty(8).toString(); // 20°/24°
		
		cityTextView.setText(cityName);
		weatherStateTextView.setText(time + "\n" + state);
		temperatureTextView.setText(temperature);
		
		loadingLayout.setVisibility(View.GONE);
	}
	
	private void initWeatherData2() {

		// 在工作线程中创建Handler，Handler为消息处理者，这将意味者消息将在这个工作线程上被处理
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
				weatherHandler.sendMessage(msg); // handler发送消息
			}
			
		});
		workerThread.start();
	}
	
	private void initWeatherData() {
		// 定位城市名
		String cityName = new LocationUtil(this).getCurrentCityName();
		// 根据城市名获得天气情况
		SoapObject detail = WebServiceUtil.getWeatherByCity(cityName);
		// 获取天气实况
		String weatherState = detail.getProperty(4).toString();
		
		String date = detail.getProperty(7).toString();
		
		String weatherToday = "今天：" + date.split(" ")[0];
		weatherToday = weatherToday + "\n天气：" + date.split(" ")[1];
		weatherToday = weatherToday + "\n气温："
			+ detail.getProperty(8).toString();
		
		cityTextView.setText(cityName + "天气");
		
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
		// 设置返回上一级
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		
		// 设置响应图标打开菜单的监听器
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
			new ToastUtil(this).show("尚待完成");
			break;
		case R.id.share_image:
			new ToastUtil(this).show("尚待完成");
			break;
		}
		
	}
	
}
