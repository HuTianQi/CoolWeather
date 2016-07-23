package com.htq.coolweather;

import java.util.Calendar;
import java.util.List;
import com.htq.coolweather.R;
import com.htq.coolweather.adapter.GridTodayCAdapter;
import com.htq.coolweather.entity.LivingIndexEntity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author htq 爱丽颖的颖火虫
 * 博客地址:blog.csdn.net/htq__
 *
 */
public class LifeIndexFragment extends Fragment {

	public static final String TAG = "TodayCan";
	public TextView descTv;
	public List<LivingIndexEntity> listsib;
    private TextView dateTv;
	private View baseView;
	private GridView todayInfoGrid;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity.TAG_H = TAG;
		baseView = inflater.inflate(R.layout.gridview_todaycan, null);

		initView();
		initData();
		return baseView;
	}
	
	private void initView()
	{
		todayInfoGrid = (GridView) baseView
				.findViewById(R.id.gridview);
		dateTv=(TextView) baseView.findViewById(R.id.date_tv);
		
		descTv = (TextView)baseView.findViewById(R.id.todaycan_dec);

		
		
	}
	private  void initData()
	{
		initDate();
		
		
		if(MainActivity.response.getResults()==null)
		{
			Toast.makeText(getActivity(), "获取指数信息失败，请检查网络连接",0).show();
		}else
		{
			setData();
		}

	}
	private void setData()
	{
		listsib = MainActivity.response.getResults().get(0).getIndex();
	//	Log.i("weatherIndex", listsib.toString()+"==>>listsib.toString()");
		if (MainActivity.response.getResults().get(0).getIndex()
				.toString() == "[]") {
		}else {
			
			todayInfoGrid.setAdapter(new GridTodayCAdapter(getActivity(),
					listsib));
	
		}
		todayInfoGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
					descTv.setText(listsib.get(position).getDes());
					
			}
		});
		
	}
	private void initDate()
	{

		Calendar calendar = Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int mon=calendar.get(Calendar.MONTH )+1;
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		dateTv.setText(year+"年"+mon+"月"+day+"日");
	}
	
}
