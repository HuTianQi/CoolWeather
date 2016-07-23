package com.htq.coolweather;


import com.htq.coolweather.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutMeFragment extends Fragment {

	private View baseView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		baseView=inflater.inflate(R.layout.about_me_fragment, null);
		initView();
		return baseView;
	}

	private void initView()
	{
       TextView aboutMeTv=(TextView) baseView.findViewById(R.id.about_me_tv);
       Linkify.addLinks(aboutMeTv, Linkify.ALL);
	}
	
}
