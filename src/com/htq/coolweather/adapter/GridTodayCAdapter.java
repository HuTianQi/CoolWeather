package com.htq.coolweather.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.htq.coolweather.R;
import com.htq.coolweather.entity.LivingIndexEntity;

public class GridTodayCAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<LivingIndexEntity> sportIndex;
	private int[] resours = {  
		    R.drawable.ic_todaycan_dress,
			R.drawable.ic_todaycan_carwash, R.drawable.ic_todaycan_tour,
			R.drawable.ic_todaycan_coldl, R.drawable.ic_todaycan_sport,
			R.drawable.ic_todaycan_ultravioletrays };

	public GridTodayCAdapter(Context context, List<LivingIndexEntity> sportIndex) {
		this.mInflater = LayoutInflater.from(context);
		this.sportIndex = sportIndex;
	}

	@Override
	public int getCount() {
		return sportIndex == null ? 0 : sportIndex.size();
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
			// 此处需要加上第二个参数parent，否则item中的设置无效。如item高度设置。
			convertView = mInflater.inflate(R.layout.item_gridview_todaycan,
					parent, false);
		}
		TextView dothing = (TextView) convertView.findViewById(R.id.dothing);
		TextView index = (TextView) convertView.findViewById(R.id.index);
		ImageView image_index = (ImageView) convertView
				.findViewById(R.id.image_index);
		ImageView image_click = (ImageView) convertView
				.findViewById(R.id.image_click);

		// 设置数据
		if (position == 0){
			dothing.setText("穿衣指数");
		}else if (position == 1) {
			dothing.setText("洗车指数");
		}else if (position == 2){
			dothing.setText("旅游指数");
		}else if (position == 3) {
			dothing.setText("感冒指数");
		}else if (position == 4) {
			dothing.setText("运动指数");
		}else if (position == 5) {
			dothing.setText("紫外线指数");
		}
		else {
			dothing.setText(sportIndex.get(position).getTipt());
		}

		index.setText("点击查看详情");

		Log.i("TAG", sportIndex.size()+"sportIndex.size()");
		image_index.setBackgroundResource(resours[position]);
		image_click.setBackgroundResource(R.drawable.ic_todaycan_clickbt);
		return convertView;
	}
}
