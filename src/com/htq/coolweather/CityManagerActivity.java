package com.htq.coolweather;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.VersionInfo;

import com.htq.coolweather.R;
import com.htq.coolweather.adapter.GridCityMAdapter;
import com.htq.coolweather.entity.CityManagerEntity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class CityManagerActivity extends Activity {

	private GridView mGrid;
	private List<CityManagerEntity> mCityManagerEntity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview_activity);
		initView();
	}
	private void initView()
	{
		mCityManagerEntity = new ArrayList<CityManagerEntity>();
		mGrid = (GridView) findViewById(R.id.gridview);
		mGrid.setNumColumns(3);
		mGrid.setBackgroundResource(R.drawable.bg_homepager_blur);
		mGrid.setAdapter(new GridCityMAdapter(this, mCityManagerEntity));
		
	}
}
