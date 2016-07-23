package com.htq.coolweather.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.htq.coolweather.R;
import com.htq.coolweather.entity.SQLiteCityManager;

public class GridAddCityAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private static final String[] cityname = 
			{ "北京", "上海", "广州","南京", "成都", "武汉", "杭州", "西安", "济南", "长春", "东莞",
					"沈阳", "天津", "哈尔滨", "长沙", "呼和浩特", "石家庄", "重庆", "无锡", "包头",
					"大连", "深圳", "福州", "海口", "乌鲁木齐", "兰州", "银川", "太原", "郑州",
					"合肥", "南昌", "南宁", "贵阳", "昆明", "拉萨", "西宁", "台北", "香港", "澳门" };
	SparseBooleanArray sba = new SparseBooleanArray();
	private String nowcityname;

	public GridAddCityAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);

		SQLiteCityManager sqlite = new SQLiteCityManager(context, "weatherdb", null, 1);
		SQLiteDatabase db = sqlite.getReadableDatabase();
		Cursor cursor = db.query("coolWeather", null, null, null, null, null, null);
		while(cursor.moveToNext()){
			nowcityname = cursor.getString(cursor.getColumnIndex("cityname"));
			Log.i("TAG", nowcityname+"-->nowcityname");
			for(int i=0;i<cityname.length;i++){
				if(nowcityname.equals(cityname[i])){
					sba.put(i, true);
				}
			}
		}
	}

	@Override
	public int getCount() {
		return cityname == null ? 0 : cityname.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_gridview_addcity,
					parent, false);// 此处需要加上第二个参数parent，否则item中的设置无效。如item高度设置。
		}
		TextView citytext = (TextView) convertView.findViewById(R.id.citytext);
		citytext.setText(cityname[position]);

		// 查询数据库，数据库中有该城市则设置勾选
		if (sba.get(position)) {
			citytext.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.city_checkbox_selected, 0);
		}
		return convertView;
	}
}
