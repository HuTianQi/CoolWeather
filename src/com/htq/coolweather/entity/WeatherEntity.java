package com.htq.coolweather.entity;

import java.util.List;

/**
 * 
 * @author htq
 * bolg:www.csdn.net/htq__
 */
public class WeatherEntity {

	private String currentCity;//当前城市
	private List<WeatherSubEntity> weather_data;//天气预报信息
	private String pm25;//PM2.5值
	private List<LivingIndexEntity> index;//各项指数
	
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public List<WeatherSubEntity> getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(List<WeatherSubEntity> weather_data) {
		this.weather_data = weather_data;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public List<LivingIndexEntity> getIndex() {
		return index;
	}
	public void setIndex(List<LivingIndexEntity> index) {
		this.index = index;
	}
}
