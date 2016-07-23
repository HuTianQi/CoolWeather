package com.htq.coolweather;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htq.coolweather.R;
import com.google.gson.GsonBuilder;
import com.htq.coolweather.base.Appliction;
import com.htq.coolweather.entity.CityManagerEntity;
import com.htq.coolweather.entity.MHttpEntity;
import com.htq.coolweather.entity.ResponseWrapper;
import com.htq.coolweather.entity.SendDataEntity;
import com.htq.coolweather.utils.SharePrefrenceUtil;
import com.htq.coolweather.utils.SystemUtils;

/**
 * 
 * @author htq 爱丽颖的颖火虫
 * 博客地址：blog.csdn.net/htq__
 *
 */
public class MainActivity extends FragmentActivity implements
		OnClickListener, FragmentAndActivity {

	private long nowtime;
	
	public static ResponseWrapper response = new ResponseWrapper();// 数据结构的对象
	public static ResponseWrapper response2;
	private DrawerLayout mainDrawerLayout;
	private View leftDrawer;
	private EditText inputcity;
	public boolean netErrorFlag=false;
	public static final int succeed = 1;
	public static final int fail = 2;
	public static final int nonet = 3;
	private static int tag = 0;
	public static String TAG_H = null;
	private ProgressDialog pDialog;
	public static HomePageFragment homecontent = new HomePageFragment();
	public CityManagerFragment citymanager = new CityManagerFragment();
	public static CityManagerEntity cmb2 = new CityManagerEntity();
    private ChangeBgReceiver mReceiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		getIntentData();
		registerBroadCast();
		initview();

	}

	
	private void getIntentData()
	{
		Intent intent = getIntent();
		String wetherdata = intent.getStringExtra("weather_data");// 得到启动页传递过来的数据
		GsonBuilder gson = new GsonBuilder();//
		response2 = gson.create().fromJson(wetherdata, ResponseWrapper.class);
		if(response2!=null)
		{
			if(response2.getError() == 0){
			response = response2;
			
		}
		}else
		{
			netErrorFlag=true;
		}
		
	}
	
	private void initview() {
		mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_main);
		initBgPic();
		leftDrawer = findViewById(R.id.left_drawer);
		mainDrawerLayout.setScrimColor(0x00000000);// 设置底部页面背景透明度
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		  ft.replace(R.id.fragmentlayout, homecontent, HomePageFragment.TAG);
		  ft.commit();
	
	}

	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.homep_menu:
			switchLeftLayout();
			break;
		case R.id.homep_refresh:
			if(Appliction.isNetWorkConnect(getApplicationContext()))
			{
				refresh();
				
			}else {
				Toast.makeText(getApplicationContext(), "网络未连接，请检查网络设置",0).show();
			}
			
			break;
		case R.id.btn_life:
			switchLeftLayout();
			switchFragment(new LifeIndexFragment(),null);
			break;
		case R.id.btn_city_manager:
			switchLeftLayout();
			switchFragment(citymanager, null);
			break;
		case R.id.btn_change_bag:
			switchLeftLayout();
			switchFragment(new ChangeBackgroundFragment(), null);
			break;
		case R.id.btn_about:
			switchLeftLayout();
			switchFragment(new AboutMeFragment(), null);
			break;
		case R.id.btn_share_app:
			SystemUtils.shareApp(this);
		    break;
		case R.id.exitapp:
			showExitDialog();
			break;
		}
	}
	
	private void refresh()
	{
		showDialog();
		switchFragment(homecontent, null);
		new Thread(new Runnable() {

			@Override
			public void run() {
				sendRequest(HomePageFragment.currentcity.getText()
						.toString());
			}
		}).start();
		
	}
	private void showExitDialog()
	{
		final Dialog dialog = new Dialog(this, 
				android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
		View exitappview = getLayoutInflater().inflate
				(R.layout.exitapp_dialog, null);
		TextView exitapp_text = (TextView) exitappview.findViewById(R.id.exitapp_text);
		Button leftbutton = (Button) exitappview.findViewById(R.id.leftbutton);
		Button rightbutton = (Button) exitappview.findViewById(R.id.rightbutton);
		exitapp_text.setText("退出程序");
		leftbutton.setText("确定");
		rightbutton.setText("取消");
		leftbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		rightbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setContentView(exitappview);
		dialog.show();
		
	}

	private void fromJson(String wetherdata) {
		GsonBuilder gson = new GsonBuilder();//
		response2 = gson.create().fromJson(wetherdata, ResponseWrapper.class);
		if (response2.getError() == 0) {
			response = response2;
			homecontent.setpagedata();
			if (tag == 4 && inputcity != null) {
				closeinput(inputcity);
			}
		} else if (response2.getError() == -3 || response2.getError() == -2) {
			showToast(getString(R.string.input_truename));
		} else {
			showToast(getString(R.string.getdata_fail));
		}
		if(HomePageFragment.pDialog != null){
			HomePageFragment.pDialog.dismiss();
		}
	}

	/**
	 * 点击多次bt，Toast只显示一次的解决方案
	 */
	public Toast toast = null;

	public void showToast(String text) {
		if (toast == null) {
			toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
		}
		toast.show();
	}

	/**
	 * 向服务器发送数据请求
	 */
	public void sendRequest(String cityname) {
		String getData = null;
		MHttpEntity mhe = null;
		try {
			SendDataEntity.setCity(cityname);// 获取用户输入的城市名
			mhe = MHttpEntity.sendHttpRequest(SendDataEntity.getData());
			if (mhe.getHentity() != null) {
				getData = EntityUtils.toString(mhe.getHentity());
				mhe.getMessage().obj = getData;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		handler.sendMessage(mhe.getMessage());// 使用Handler对网络状态做处理
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (pDialog != null)
				pDialog.dismiss();
			if (msg != null)
				switch (msg.arg1) {
				case succeed:// 与服务器连接成功
					if (msg.obj != null) {
						fromJson(msg.obj.toString());
					}
					break;
				case fail:// 与服务器连接失败
					showToast(getString(R.string.net_fail));
					break;
				}
		}
	};

	/**
	 * 关联menu键
	 */
	private void switchLeftLayout() {
		if (mainDrawerLayout.isDrawerOpen(leftDrawer)) {
			mainDrawerLayout.closeDrawer(leftDrawer);
		} else {
			mainDrawerLayout.openDrawer(leftDrawer);
		}
	}

	public void switchFragment(Fragment fragment, String str) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.fragmentlayout, fragment, null);
		//ft.replace(R.id.fragmentlayout, fragment, str);
		ft.commit();
	}

	/**
	 * 关闭输入法键盘
	 */
	public void closeinput(EditText editText) {
		editText.setText("");
		InputMethodManager imm = (InputMethodManager) 
				getSystemService(MainActivity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * 连续按两次返回则退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - nowtime > 2000) {
				Toast.makeText(this, R.string.click_exit, Toast.LENGTH_SHORT)
						.show();
				nowtime = System.currentTimeMillis();
				return true;
			} else {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void senddata(EditText inputcity) {
		this.inputcity = inputcity;
	}

	@Override
	public void sendcitytext(final String inputcitytext) {
//		this.inputcitytext = inputcitytext;
		tag = 4;
		if ("".equals(inputcitytext)) {
			showToast(getString(R.string.edittext_hint));
			if(HomePageFragment.pDialog != null){
				HomePageFragment.pDialog.dismiss();
			}
		} else {
			SendDataEntity.setCity(inputcitytext);// 获取用户输入城市
			new Thread(new Runnable() {

				@Override
				public void run() {
					sendRequest(inputcitytext);
				}
			}).start();
		}
	}

	private void registerBroadCast()
	{
		mReceiver = new ChangeBgReceiver();
		IntentFilter filter = new IntentFilter("change_background");
		registerReceiver(mReceiver, filter);
	}
	private class ChangeBgReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String path = intent.getStringExtra("path");
			if(path==null)
				Log.i("weather", "path is null");
			else
			{
				Log.i("weather", path);
			}
			boolean auto=intent.getBooleanExtra("auto",false);
			Bitmap bitmap = getBitmapByPath(path,auto);
			if(bitmap != null) {
				mainDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
			}
			
		}
	}
	public Bitmap getBitmapByPath(String path,boolean auto) {
	//	Log.i("weather", "getbitmap is called");
		AssetManager am = this.getAssets();
		Bitmap bitmap = null;
		InputStream is =null;
		try {
			if(auto==false)
			{
			  is = am.open("bkgs/" + path);
			}else if(auto==true)
			{
				Log.i("weather", "before open auto_bkgs");
				is = am.open("autobkgs/" + path);
				
				Log.i("weather", path);
			}
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("weather", "open file error");
		}
		return bitmap;
	}
	
	@Override
	public void showDialog() {
		pDialog = new ProgressDialog(MainActivity.this);
		pDialog.setCancelable(true);// 点击可以取消Dialog的展现
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setMessage("正在更新...");
		pDialog.show();
	}
	private void initBgPic()
	{
        String path=new SharePrefrenceUtil(this).getPath();
		Intent intent=new Intent("change_background");
		intent.putExtra("path", path);
		sendBroadcast(intent);
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
	
	
}
