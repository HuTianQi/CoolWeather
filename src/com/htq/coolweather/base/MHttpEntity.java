package com.htq.coolweather.base;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import android.net.Uri;
import android.os.Message;

public class MHttpEntity {
	
	private Message message;
	private HttpEntity hentity;
	public static final int succeed = 1;
	public static final int fail = 2;
	public static final int nonet = 3;
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public HttpEntity getHentity() {
		return hentity;
	}
	public void setHentity(HttpEntity hentity) {
		this.hentity = hentity;
	}

	public static MHttpEntity sendhttpclient(String str) {
		MHttpEntity mhe = new MHttpEntity();
		Message Mesg = Message.obtain();
		HttpEntity he = null;
		HttpClient hClient = new DefaultHttpClient();// 实例化得到一个网络连接对象
		HttpConnectionParams.setConnectionTimeout(hClient.getParams(), 5000);//连接超时设置
		String mstr = Uri.decode(str);// 将String类型转为uri.有中文则必有此句。
		HttpGet hget = new HttpGet(mstr);// 创建Http请求(get请求)
		try {
			HttpResponse re = hClient.execute(hget);// 执行一个请求
			if (re.getStatusLine().getStatusCode() == 200) {
				he = re.getEntity();// 获得数据实体
				Mesg.arg1 = succeed;// 保存网络状态
			} else {
				Mesg.arg1 = fail;// 保存网络状态
			}
		} catch (SocketTimeoutException e) {
			Mesg.arg1 = fail;
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Mesg.arg1 = fail;
			e.printStackTrace();
		} catch (IOException e) {
			Mesg.arg1 = fail;
			e.printStackTrace();
		}
		mhe.setMessage(Mesg);
		mhe.setHentity(he);
		return mhe;
	}
}
