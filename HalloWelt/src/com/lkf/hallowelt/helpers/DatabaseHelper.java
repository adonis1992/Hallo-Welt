package com.lkf.hallowelt.helpers;

import java.util.Calendar;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String TABLE_PHOTO = "PhotoInformation";
	private static final String TABLE_DIARY = "DiaryInformation";
	private static final String TABLE_PAGE = "PageInformation";
	private static final String TABLE_PHOTO_DIARY_LINK = "PhotoDiaryLink";
	
	private static final String[] PHOTO_INFORMATION = {"Name", "Lat", "Lon", "Year", "Month", "Day", "Daytype"};
	private static final String[] DIARY_INFORMATION = {"PageNumber", "CurrentPage"};
	private static final String[] PAGE_INFORMATION = {"DiaryID", "Pagination"};
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
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PAGE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "DiaryID INTEGER, Pagination INTEGER);");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PHOTO_DIARY_LINK + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "PhotoID INTEGER, DairyID INTEGER, Pagination INTEGER, PositionX FLOAT, PositionY FLOAT, RotateAngel FLOAT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub

	}

	public void insertPhoto(Location location, long fileName)
	{
		currentTime.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); 
		
		ContentValues theValues = new ContentValues();
		theValues.put(PHOTO_INFORMATION[0], location.getLatitude());
		theValues.put(PHOTO_INFORMATION[1], location.getLongitude());
		theValues.put(PHOTO_INFORMATION[2], currentTime.get(Calendar.YEAR));
		theValues.put(PHOTO_INFORMATION[3], currentTime.get(Calendar.MONTH));
		theValues.put(PHOTO_INFORMATION[4], currentTime.get(Calendar.DAY_OF_MONTH));
		theValues.put(PHOTO_INFORMATION[5], currentTime.get(Calendar.DAY_OF_WEEK));
		theValues.put(PHOTO_INFORMATION[6], fileName+".png");
		database.insert(TABLE_PHOTO, null, theValues);
	}
	
	public Cursor getPhotos()
	{
		return database.rawQuery("SELECT * FROM " + TABLE_PHOTO, null);
	}
	
	//Don't use now
	public void deletePhoto(String id)
	{
		database.delete(TABLE_PHOTO, "id = " + id, null);
	}
	
	public void insertDiary()
	{
		ContentValues theValues = new ContentValues();
		theValues.put(DIARY_INFORMATION[0], 1);
		theValues.put(DIARY_INFORMATION[1], 1);
		database.insert(TABLE_DIARY, null, theValues);
	}
	
	public Cursor getDiary()
	{
		return database.rawQuery("SELECT * FROM " + DIARY_INFORMATION, null);
	}
	
	public Cursor getDiary(int diaryID)
	{
		return database.rawQuery("SELECT * FROM " + DIARY_INFORMATION + " WHERE id = " + diaryID, null);
	}
	
	public void updateDairyPage(int pageNumber, int diaryID)
	{
		ContentValues theValues = new ContentValues();
		theValues.put(DIARY_INFORMATION[0], pageNumber);
		database.update(TABLE_DIARY, theValues, "id = " + diaryID, null);
	}
	
	public void updateDairyCurrent(int current, int diaryID)
	{
		ContentValues theValues = new ContentValues();
		theValues.put(DIARY_INFORMATION[1], current);
		database.update(TABLE_DIARY, theValues, "id = " + diaryID, null);
	}
	
	public void deleteDiary(int diaryID)
	{
		database.delete(TABLE_PAGE, "DairyID = " + diaryID, null);
		database.delete(TABLE_DIARY, "id = " + diaryID, null);
	}
	
	public void insertPage(int diaryID)
	{
		Cursor cursor = getDiary(diaryID);
		if (cursor.getCount() == 0)
		{
			throw new RuntimeException("The diary isn't exist!");
		}
		else 
		{
			cursor.moveToNext();
			int pageNumber = cursor.getInt(1) + 1;
			cursor.close();
			ContentValues theValues = new ContentValues();
			
			theValues.put(PAGE_INFORMATION[0], diaryID);
			theValues.put(PAGE_INFORMATION[1], pageNumber);
			database.insert(TABLE_PAGE, null, theValues);
			
			updateDairyPage(pageNumber, diaryID);
		}
	}
	
	public void deletePage(int diaryID, int pagination)
	{
		database.delete(TABLE_PAGE, "DiaryID = " + diaryID + "and Pagination = " + pagination, null);
	}
	
	public void insertPhoto(int photoID, int diaryID, int pagination)
	{
		ContentValues theValues = new ContentValues();
		theValues.put(PHOTO_DIARY_LINK[0], photoID);
		theValues.put(PHOTO_DIARY_LINK[1], diaryID);
		theValues.put(PHOTO_DIARY_LINK[2], pagination);
		theValues.put(PHOTO_DIARY_LINK[3], 0);
		theValues.put(PHOTO_DIARY_LINK[4], 0);
		theValues.put(PHOTO_DIARY_LINK[5], 0);
		database.insert(TABLE_DIARY, null, theValues);
	}
	
	public void updatePhoto(int photoID, int diaryID, int pagination, float PositionX, float PositionY, float RotateAngel)
	{
		ContentValues theValues = new ContentValues();
		theValues.put(PHOTO_DIARY_LINK[3], PositionX);
		theValues.put(PHOTO_DIARY_LINK[4], PositionY);
		theValues.put(PHOTO_DIARY_LINK[5], RotateAngel);
		database.update(TABLE_PHOTO_DIARY_LINK, theValues, PHOTO_DIARY_LINK[0] + " = " + photoID + " and "
				+ PHOTO_DIARY_LINK[1] + " = " + diaryID + " and " + PHOTO_DIARY_LINK[2] + " = " + pagination, null);
	}
	
	public void deletePhoto(int photoID, int diaryID, int pagination)
	{
		database.delete(TABLE_PHOTO_DIARY_LINK, PHOTO_DIARY_LINK[0] + " = " + photoID + " and "
				+ PHOTO_DIARY_LINK[1] + " = " + diaryID + " and " + PHOTO_DIARY_LINK[2] + " = " + pagination, null);
	}
}
