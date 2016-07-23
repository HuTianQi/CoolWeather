package com.htq.coolweather.base;


public class SendDataEntity {

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

	
}
