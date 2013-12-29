package com.lkf.hallowelt.helpers;

import java.util.Calendar;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String TABLE_PHOTO = "PhotoInformation";
	private static final String TABLE_DIARY = "DiaryInformation";
	private static final String TABLE_PHOTO_DIARY_LINK = "PhotoDiaryLink";
	
	private static final String[] PHOTO_INFORMATION = {"Name", "Lat", "Lon", "Year", "Month", "Day", "Daytype"};
	private static final String[] DIARY_INFORMATION = {"PageNumber", "CurrentPage"};
	private static final String[] PHOTO_DIARY_LINK = {"PhotoID", "DairyID", "Pagination", "PositionX", "PositionY", "RotateAngel"};
	
	private SQLiteDatabase database;
	private Calendar currentTime;
 	
	public DatabaseHelper(Context context)
	{
		super(context, "hallowelt.db", null, 1);
		database = getWritableDatabase();
		currentTime = Calendar.getInstance();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PHOTO + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "Name TEXT, Lat FLOAT, Lon FLOAT, Year INTEGER, Month INTEGER, Day INTEGER, Daytype INTEGER);");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DIARY + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "PageNumber INTEGER, CurrentPage INTEGER);");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PHOTO_DIARY_LINK + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "PhotoID INTEGER, DairyID INTEGER, Pagination INTEGER, PositionX FLOAT, PositionY FLOAT, RotateAngel INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub

	}

	public void insertPhoto(Location location, long fileName)
	{
		currentTime.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); 
		
		ContentValues theValues = new ContentValues();//改为局部变量
		theValues.put(PHOTO_INFORMATION[0], location.getLatitude());
		theValues.put(PHOTO_INFORMATION[1], location.getLongitude());
		theValues.put(PHOTO_INFORMATION[2], currentTime.get(Calendar.YEAR));
		theValues.put(PHOTO_INFORMATION[3], currentTime.get(Calendar.MONTH));
		theValues.put(PHOTO_INFORMATION[4], currentTime.get(Calendar.DAY_OF_MONTH));
		theValues.put(PHOTO_INFORMATION[5], currentTime.get(Calendar.DAY_OF_WEEK));
		theValues.put(PHOTO_INFORMATION[6], fileName+".png");
		database.insert(TABLE_PHOTO, TABLE_DIARY, null);
	}
	
	public void insertPage(int diaryID, int photoID)
	{
		ContentValues theValues = new ContentValues();//改为局部变量
		Cursor cursor = getDiary(diaryID);
		if (cursor.getCount() == 0)
		{
			throw new RuntimeException("The diary isn't exist!");
		}
		else 
		{
			
		}
	}
	
	public Cursor getPhotos()
	{
		return database.rawQuery("SELECT * FROM " + TABLE_PHOTO, null);
	}
	
	public Cursor getDiary(int diaryID)
	{
		return database.rawQuery("SELECT * FROM " + DIARY_INFORMATION + " WHERE id = " + diaryID, null);
	}
}
