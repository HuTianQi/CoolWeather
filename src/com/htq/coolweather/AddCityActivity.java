package com.htq.coolweather;

import com.htq.coolweather.R;
import com.htq.coolweather.adapter.GridAddCityAdapter;
import com.htq.coolweather.entity.SQLiteCityManager;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author htq 爱丽颖的颖火虫
 * blog:blog.csdn.net/htq__
 */
public class AddCityActivity extends Activity {

	private GridView addCityGrid;
	private static TextView cityTv;
	private boolean ishas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcity_activity);
		initView();
		
	}

	private void initView()
	{
addCityGrid = (GridView) findViewById(R.id.addcity_gridview);
		
		GridAddCityAdapter ad = new GridAddCityAdapter(this);
		
		addCityGrid.setAdapter(ad);
		
		addCityGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				cityTv = (TextView) view.findViewById(R.id.citytext);
				cityTv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.city_checkbox_selected, 0);
				querydata(cityTv.getText().toString());
				// 如果数据库中没有该城市，则添加到数据库。反之则提示。
				if(!ishas){
					insertdata();
					finish();
				}else{
					Toast.makeText(AddCityActivity.this, "不可重复添加",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
	private SQLiteCityManager sqlite = new SQLiteCityManager(
			AddCityActivity.this, "weatherdb", null, 1);

	
	private void insertdata() {
		
		SQLiteDatabase db = sqlite.getReadableDatabase();
		
		ContentValues cv = new ContentValues();
		
		cv.put("cityname", cityTv.getText().toString());
		cv.put("imageurl", "");
		cv.put("weather", "点击更新");
		cv.put("temp", "0℃");
		//
		db.insert("coolWeather", "cityname", cv);
	}

	public void querydata(String str) {
		// 读写数据库
		SQLiteDatabase db = sqlite.getReadableDatabase();
		Cursor cursor = db.query("coolWeather", null, null, null, null, null,
				null);
		while (cursor.moveToNext()) {
			String cityname = cursor.getString(cursor
					.getColumnIndex("cityname"));
			cityname = cityname.substring(0, 2);
			str = str.substring(0, 2);
			// 与当前按下的城市名做比较
			if (ishas = cityname.equals(str)) {
				return;
			}
		}
	}
}
