package com.lkf.hallowelt.controllers;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lkf.hallowelt.R;

public class MapController extends Activity
{	
	private static Matrix theScaleMatrix;
	private static String[] tableNames;
	
	static
	{
		theScaleMatrix = new Matrix();
		theScaleMatrix.postScale(0.2f, 0.2f);
		tableNames = new String[1];
		tableNames[0] = "PhotoInformation";
	}
	
//	private GoogleMap theMap;
//	private OnMarkerClickListener theMarkerClickListener;
//	private LatLng theCurrentLocaiton;
	
//	private DatabaseHelper theDatabaseHelper;
//	private SQLiteDatabase theDatabase;
	
//	private String theSDCardPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.maplayout);
		
/*		theSDCardPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "hallowelt/writings/";
		theMarkerClickListener = new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker)
			{
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		theDatabaseHelper = new DatabaseHelper(this, "hallowelt.db");
		theDatabase = theDatabaseHelper.getReadableDatabase();
		
		Intent gottenInformation = getIntent();
		Bundle gottenData = gottenInformation.getExtras();
		theCurrentLocaiton = new LatLng(gottenData.getFloat("lat"), gottenData.getFloat("lng"));*/
	}
	
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
//		setUpMapIfNeeded();
	}

/*	private void setUpMapIfNeeded()
	{
		if (theMap == null)
		{
			theMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			theMap.setMyLocationEnabled(true);
			theMap.setOnMarkerClickListener(theMarkerClickListener);
			Cursor cursor = theDatabase.rawQuery("SELECT * FROM " + tableNames[0], null);
			if (cursor != null)
			{
				while (cursor.moveToNext())
				{
					LatLng position = new LatLng(cursor.getFloat(1), cursor.getFloat(2));
					MarkerOptions theOptions = new MarkerOptions();
					theOptions.position(position);
					theOptions.icon(BitmapDescriptorFactory.fromBitmap(Scale(BitmapFactory.decodeFile(theSDCardPath + cursor.getString(7)))));
					theMap.addMarker(theOptions);
				}
			}
			theMap.moveCamera(CameraUpdateFactory.newLatLng(theCurrentLocaiton));
		}
	}
	
	private static Bitmap Scale(Bitmap bitmap)
	{
		Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), theScaleMatrix, true);
		bitmap.recycle();
		return resizeBitmap;
	}*/
}
