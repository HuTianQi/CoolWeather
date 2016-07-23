package com.htq.coolweather.entity;


public class CityManagerEntity {

	private String city;//城市
	private String weatherimage;//天气图片
	private String temp;//温度
	private String weather;//天气
	private String dec;//描述
	
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getWeatherimage() {
		return weatherimage;
	}
	public void setWeatherimage(String weatherimage) {
		this.weatherimage = weatherimage;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getDec() {
		return dec;
	}
	public void setDec(String dec) {
		this.dec = dec;
	}
	
}
