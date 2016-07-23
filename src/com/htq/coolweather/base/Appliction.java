package com.htq.coolweather.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextDirectionHeuristic;

import com.htq.coolweather.utils.SharePrefrenceUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class Appliction extends Application{
	
	@Override
	public void onCreate() {
		
		initImageLoader(this);
	}
	
	private void initImageLoader(Context ctx) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				ctx).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheSize(32 * 1024 * 1024)
				.memoryCacheSize(4 * 1024 * 1024).enableLogging().build();
		ImageLoader.getInstance().init(config);
		
	}
	
	
	public static boolean isNetWorkConnect(Context context)
	{
		ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
		if(networkInfo==null||!networkInfo.isAvailable())
	    	return false;
		else {
			return true;
		}
	
	}
}
