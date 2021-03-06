package com.lkf.hallowelt.controllers;

import android.os.Bundle;

import com.lkf.hallowelt.helpers.ResourceHelper;
import com.lkf.hallowelt.screens.mainmenu.MainMenuView;
import com.lkf.lib.base.LKFScreen;

public class MainMenuController extends ModelController
{
	//View Member
	private MainMenuView theMainMenuView;

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
		theMainMenuView = new MainMenuView(this, 360, 640, ResourceHelper.COORDINATE_HELPER.focalLenth);
		return theMainMenuView;
	}
}
