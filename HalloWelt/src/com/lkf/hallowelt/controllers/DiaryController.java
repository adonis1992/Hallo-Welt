package com.lkf.hallowelt.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;

import com.lkf.hallowelt.helpers.ResourceHelper;
import com.lkf.hallowelt.screens.diary.BookCaseView;
import com.lkf.hallowelt.screens.diary.DiaryView;
import com.lkf.hallowelt.screens.diary.PhotoChooseView;
import com.lkf.lib.base.LKFScreen;

public class DiaryController extends ModelController
{	
	//Screens
	private BookCaseView theBookCaseView;
	private DiaryView theDiaryView;
	private PhotoChooseView thePhotoChooseView;
	
	private int diaryID;
	private int currentPage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addBaseView();
	}
	
	@Override
	public LKFScreen getStartScreen()
	{
		// TODO Auto-generated method stub
		theBookCaseView = new BookCaseView(this, 360, 640, ResourceHelper.COORDINATE_HELPER.focalLenth);
		theDiaryView = new DiaryView(this, 360, 640, ResourceHelper.COORDINATE_HELPER.focalLenth);
		thePhotoChooseView = new PhotoChooseView(this, 360, 640, ResourceHelper.COORDINATE_HELPER.focalLenth);
		return theBookCaseView;
	}

	public InputStream readPhoto(String fileName) throws IOException
	{
		return readFile("photos" + File.separator + fileName);
	}
	
	public int getDiaryID()
	{
		return diaryID;
	}
	
	public int getCurrentPage()
	{
		return currentPage;
	}
	
	public void getToDiary()
	{
		setScreen(theDiaryView);
	}
}
