package com.htq.coolweather;

import com.htq.coolweather.R;
import com.htq.coolweather.adapter.GridCityMAdapter;
import com.htq.coolweather.entity.CityManagerEntity;
import com.htq.coolweather.entity.SQLiteCityManager;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CityManagerFragment extends Fragment {

	public static final String TAG = "CityManager";
	private GridView mGridview;
	private String cityname;
	private String imageurl;
	private String weather;
	private String temp;
	public CityManagerEntity cmb;
	public GridCityMAdapter cmAdapter;
	private FragmentAndActivity mActivity;
	public Intent intent;
	private SQLiteCityManager sqlite;
	private SQLiteDatabase db;
	private View baseView;

	@Override
	public void onResume() {
		getdatabase();
		for (int i = 0; i < HomePageFragment.mcmb.size(); i++) {
			if (HomePageFragment.mcmb.get(i).getCity()
					.equals(MainActivity.cmb2.getCity())) {
				HomePageFragment.mcmb.remove(MainActivity.cmb2);
			}
		}
		// 标记，为每次打开城市管理页都会加载一个item问题的解决方案
		MainActivity.cmb2.setCity("添加");
		HomePageFragment.mcmb.add(HomePageFragment.mcmb.size(),
				MainActivity.cmb2);
		cmAdapter.setCitymanager(HomePageFragment.mcmb);
		for(int i = 0; i < HomePageFragment.mcmb.size(); i++ ){
			Log.i("TAG", HomePageFragment.mcmb.get(i).getCity());
		}
		Log.i("TAG", HomePageFragment.mcmb.size()+"<<<<==>>>>HomePageFragment.mcmb");
		cmAdapter.notifyDataSetChanged();

		super.onResume();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (FragmentAndActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity.TAG_H = TAG;
		baseView=inflater.inflate(R.layout.gridview_citymanager,
				null);
		 init();
		return baseView;
	}

	private void init()
	{
		
		mGridview = (GridView) baseView.findViewById(R.id.gridview);
		intent = new Intent(getActivity(), AddCityActivity.class);
		mGridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == HomePageFragment.mcmb.size() - 1) {
					startActivity(intent);
				} else {
					//showDialog
					mActivity.showDialog();
					
					MainActivity ff = (MainActivity) getActivity();
					ff.switchFragment(MainActivity.homecontent,
							HomePageFragment.TAG);
					// 得到城市，发起网络请求。
					mActivity.sendcitytext(HomePageFragment.mcmb.get(
							position).getCity());
				}
			}
		});
		cmAdapter = new GridCityMAdapter(getActivity(),
				HomePageFragment.mcmb);
		mGridview.setAdapter(cmAdapter);
	}
	
	public void getdatabase() {
		
		sqlite = new SQLiteCityManager(getActivity(),
				"weatherdb", null, 1);
		
		db = sqlite.getWritableDatabase();
		
		Cursor cursor = db.query("coolWeather", null, null, null, null, null,
				null);
		HomePageFragment.mcmb.clear();
		
		while (cursor.moveToNext()) {
			int _id = cursor.getInt(cursor.getColumnIndex("_id"));
			cityname = cursor.getString(cursor.getColumnIndex("cityname"));
			imageurl = cursor.getString(cursor.getColumnIndex("imageurl"));
			weather = cursor.getString(cursor.getColumnIndex("weather"));
			temp = cursor.getString(cursor.getColumnIndex("temp"));
			Log.i("TAG", _id + "  @@@@@@@@_id-" + " cityname-" + cityname + " imageurl-"
					+ imageurl + " weather-" + weather + " temp-" + temp);
			setCityManagerEntity();
		}
	}

	
	public void setCityManagerEntity() {
		cmb = new CityManagerEntity();
		cmb.setCity(cityname);
		cmb.setWeather(weather);
		cmb.setTemp(temp);
		cmb.setWeatherimage(imageurl);
		for (int i = 0; i < HomePageFragment.mcmb.size(); i++) {
			Log.i("TAG", HomePageFragment.mcmb.size()+"==>HomePageFragment.mcmb.size()");
			Log.i("TAG", HomePageFragment.mcmb.get(i).getCity()+"==>" +
						"HomePageFragment.mcmb.get(i).getCity()");
			if (HomePageFragment.mcmb.get(i).getCity().equals(cmb.getCity())) {
				HomePageFragment.mcmb.set(i, cmb);
				return;
			}
		}
		HomePageFragment.mcmb.add(cmb);
	}
	
	/**
	 * 删除数据库
	 */
	public void deletedata(){
		sqlite = new SQLiteCityManager(getActivity(),
				"weatherdb", null, 1);
		db = sqlite.getWritableDatabase();
		db.delete("coolWeather", "_id = "+ 2, null);
	}
}
