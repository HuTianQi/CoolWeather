package com.htq.coolweather.entity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteCityManager extends SQLiteOpenHelper{

	public SQLiteCityManager(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table coolWeather(_id integer primary key autoincrement, cityname varchar(20), "
				+ "imageurl varchar(20), weather varchar(20), temp varchar(20));";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists person;");
	}

}
