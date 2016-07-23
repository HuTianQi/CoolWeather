package com.htq.coolweather.entity;

/**
 * 
 * @author htq 
 * blog:http://www.csdn.net/htq__
 */

public class SendDataEntity {//通过百度地图定位得到的城市名来获取该城市的天气信息的实体类

	public static String city = "";
	public static String json = "json";
	public static String ak = "iGs8rFvzh1e8c7C9DjXT5toK";
	
	public static void setCity(String city) {
		SendDataEntity.city = city;
	}
	public static void setJson(String json) {
		SendDataEntity.json = json;
	}
	public static void setAk(String ak) {
		SendDataEntity.ak = ak;
	}
	public static String getCity() {
		return city;
	}
	public static String getJson() {
		return json;
	}
	public static String getAk() {
		return ak;
	}
	public static String getData() {
		return "http://api.map.baidu.com/telematics/v3/weather?location=" +
				city + "&output="+ json +"&ak="+ ak;
	}
//http://api.map.baidu.com/telematics/v3/weather?location=武汉&output=json&ak=iGs8rFvzh1e8c7C9DjXT5toK

	
}
