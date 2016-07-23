package com.htq.coolweather;

import java.util.ArrayList;
import java.util.List;

import com.htq.coolweather.R;
import com.htq.coolweather.adapter.ListWeatherAdapter;
import com.htq.coolweather.entity.CityManagerEntity;
import com.htq.coolweather.entity.SQLiteCityManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class HomePageFragment extends Fragment {

	public static final String TAG = "HomeContent";
	public static List<CityManagerEntity> mcmb = new ArrayList<CityManagerEntity>();
	public static TextView currentcity;// 当前城市
	private TextView pm25;// PM值
	private TextView temp;// 温度
	private TextView pollution;// 污染程度
	private ListView weatherInfolist;//
	private EditText inputcity;
	private Button searchWeatherBtn;
	private View homeContent;
	public MainActivity mainActivity;
	public FragmentAndActivity mActivity;
	static ProgressDialog pDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MainActivity.TAG_H = TAG;
		if(!mainActivity.netErrorFlag)
		{
			homeContent = inflater.inflate(R.layout.include_content_activity,
				null);
		   initview();
		   if(MainActivity.response.getResults()!=null)
			{
		       setpagedata();
			}
		}
		else{
			homeContent = inflater.inflate(R.layout.main_activity_net_error,
					null);
		}
		return homeContent;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mainActivity = (MainActivity) getActivity();
		mActivity = (FragmentAndActivity) activity;

	}

	private void initview() {
		currentcity = (TextView) homeContent.findViewById(R.id.currentcity);
		pm25 = (TextView) homeContent.findViewById(R.id.pm25);
		temp = (TextView) homeContent.findViewById(R.id.temp);
		searchWeatherBtn= (Button) homeContent.findViewById(R.id.btn_search);
		
		pollution = (TextView) homeContent.findViewById(R.id.pollution_level);
		inputcity = (EditText) homeContent.findViewById(R.id.inputcity);
		weatherInfolist = (ListView) homeContent
				.findViewById(R.id.weather_infor_list);
		
	}
/**
 * @author htq
 * 根据天气情况自动更换app的皮肤，如晴天则显示晴天的背景，多云则显示多云的背景...
 * 如果当前天气为一种转另外一种天气，则显示前面的那种天气背景，如多云转晴则显示多云的背景
 * 注自动设置背景不会在配置文件中记录设置的是哪张图片背景，仅仅当用户在更换皮肤模块手动更改才
 * 在配置文件中记录
 * bolg:blog.csdn.net/htq__
 */
	private void autoSetBgPic()
	{
		String path=null;
		
		String weather=MainActivity.response.getResults().get(0)
				
		.getWeather_data().get(0).getWeather();
		if(weather.contains("多云")&&!weather.contains("转多云"))
		{
			path="cloudy.jpg";
			
		}else if(weather.contains("晴")&&!weather.contains("转晴"))
		{
			path="fine.jpg";
		}else if(weather.contains("雨")){
			path="rain.jpg";
			
		}
		if(path!=null)
		{
		    Intent intent=new Intent("change_background");
	     	intent.putExtra("path", path);
	     	intent.putExtra("auto", true);
		    getActivity().sendBroadcast(intent);
		}
	}
	public void setpagedata() {
		if(pDialog != null){
			pDialog.dismiss();
		}
		
		  autoSetBgPic();
//		 Log.i("weather","autoSet is called");
		weatherInfolist.setAdapter(new ListWeatherAdapter(getActivity(),
				MainActivity.response.getResults().get(0)
						.getWeather_data()));
		searchWeatherBtn.setOnClickListener(searchWeatherOnClickListener);
		currentcity.setText(MainActivity.response.getResults().get(0)
				.getCurrentCity());

		if ("".equals(MainActivity.response.getResults().get(0).getPm25())) {
			pm25.setText("PM2.5：");
			pollution.setText(R.string.no_data);
			pollution.setBackgroundColor(Color.TRANSPARENT);
		} else {
			pm25.setText("PM2.5："
					+ MainActivity.response.getResults().get(0).getPm25());
			int pm = Integer.parseInt(MainActivity.response.getResults()
					.get(0).getPm25());
			Log.i("TAG", pm + " <-- pm");
			if (pm < 75) {
				pollution.setText(R.string.pollution_no);
				pollution.setBackgroundResource(R.drawable.ic_dl_b);
			} else if (pm > 75 && pm < 100) {
				pollution.setText(R.string.pollution_little);
				pollution.setBackgroundResource(R.drawable.ic_dl_c);
			} else if (pm > 100 && pm < 150) {
				pollution.setText(R.string.pollution_mild);
				pollution.setBackgroundResource(R.drawable.ic_dl_d);
			} else if (pm > 150 && pm < 200) {
				pollution.setText(R.string.polltion_moderate);
				pollution.setBackgroundResource(R.drawable.ic_dl_e);
			} else if (pm > 200) {
				pollution.setText(R.string.polltion_severe);
				pollution.setBackgroundResource(R.drawable.ic_dl_f);
			}
		}
		

		String todaydata = MainActivity.response.getResults().get(0)
				.getWeather_data().get(0).getDate();
		String temperature = MainActivity.response.getResults().get(0)
				.getWeather_data().get(0).getTemperature();
		String subs = null;
		if (todaydata.length() > 14) {
			subs = todaydata.substring(14, todaydata.length() - 1);
			temp.setText(subs);
		} else if (temperature.length() > 5) {
			String[] str = temperature.split("~ ", 2);
			subs = str[1];
			temp.setText(subs);
		} else {
			temp.setText(temperature);
		}

		// 创建SQLite对象并不会创建数据库
		SQLiteCityManager sqlite = new SQLiteCityManager(getActivity(),
				"weatherdb", null, 1);
		// 读写数据库
		SQLiteDatabase db = sqlite.getWritableDatabase();
		// ContentValues键值对，类似HashMap
		ContentValues cv = new ContentValues();
		// key为字段名，value为所存数据
		cv.put("cityname", MainActivity.response.getResults().get(0)
				.getCurrentCity());
		cv.put("imageurl", MainActivity.response.getResults().get(0)
				.getWeather_data().get(0).getDayPictureUrl());
		cv.put("weather", MainActivity.response.getResults().get(0)
				.getWeather_data().get(0).getWeather());
		cv.put("temp", subs);
		Cursor cursor = db.query("coolWeather", null, null, null, null, null,
				null);
		int i = 0;
		while (cursor.moveToNext()) {
			i++;
			Log.i("TAG", i + "==>>i");
			String cityname = cursor.getString(cursor
					.getColumnIndex("cityname"));
			String weathertext = cursor.getString(cursor
					.getColumnIndex("weather"));
			cityname = cityname.substring(0, 2);
			String citytext = currentcity.getText().toString().substring(0, 2);
			if (citytext.equals(cityname)) {
				if ("点击更新".equals(weathertext)) {
					db.update("coolWeather", cv, "weather = ?",
							new String[] { "点击更新" });
					db.close();
				}
				return;
			}
		}
		// 插入，第二个参数:不能为null的字段
		db.insert("coolWeather", "cityname", cv);
		db.close();
	}

	private View.OnClickListener searchWeatherOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			pDialog = new ProgressDialog(getActivity());
			pDialog.setCancelable(true);// 点击可以取消Dialog的展现
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setMessage("正在查询，请稍后...");
			pDialog.show();
			mActivity.senddata(inputcity);
			mActivity.sendcitytext(inputcity.getText().toString());
		}
	};
}

