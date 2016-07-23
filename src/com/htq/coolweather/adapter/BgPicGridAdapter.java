package com.htq.coolweather.adapter;

import java.util.List;

import com.htq.coolweather.R;
import com.htq.coolweather.entity.BgPicEntity;
import com.htq.coolweather.utils.SharePrefrenceUtil;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
/**
 * 
 * @author htq 爱丽颖的颖火虫
 * 博客地址：blog.csdn.net/htq__
 */

public class BgPicGridAdapter extends BaseAdapter {
	

	private List<BgPicEntity> bgList;
	private Resources resources;
	private Activity mActivity;
	private String mDefaultBgPath;
	private SharePrefrenceUtil sharePrefrenceUtil;
	public BgPicGridAdapter(Activity mActivity,List<BgPicEntity> list)
	{
		this.bgList = list;
		this.mActivity=mActivity;
		this.resources = mActivity.getResources();
		sharePrefrenceUtil=new SharePrefrenceUtil(mActivity);
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bgList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mActivity).inflate(
					R.layout.bg_pic_grid_item, null);
			viewHolder.backgroundIv = (ImageView) convertView
					.findViewById(R.id.gridview_item_iv);
			viewHolder.checkedIv = (ImageView) convertView
					.findViewById(R.id.gridview_item_checked_iv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.backgroundIv.setBackgroundDrawable(new BitmapDrawable(
				resources, ((BgPicEntity)getItem(position)).bitmap));
		
		mDefaultBgPath=sharePrefrenceUtil.getPath();
		if (((BgPicEntity)getItem(position)).path.equals(mDefaultBgPath)) {
			viewHolder.checkedIv.setVisibility(View.VISIBLE);
		} else {
			viewHolder.checkedIv.setVisibility(View.GONE);
		}

		return convertView;
		
		
	}
	private class ViewHolder {
		ImageView checkedIv, backgroundIv;
	}


}
