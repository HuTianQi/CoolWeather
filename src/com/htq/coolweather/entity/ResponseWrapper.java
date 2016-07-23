package com.htq.coolweather.entity;

import java.util.List;

/**
 * 
 * @author htq 爱丽颖的颖火虫
 * bolg:www.csdn.net/htq__
 */
public class ResponseWrapper {

	private int error;//错误次数
	private String status;//返回结果状态信息
	private String date;//当前时间
	private List<WeatherEntity> results;//天气预报信息
	
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<WeatherEntity> getResults() {
		return results;
	}
	public void setResults(List<WeatherEntity> results) {
		this.results = results;
	}
	
}
