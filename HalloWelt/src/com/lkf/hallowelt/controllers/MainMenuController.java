package com.lkf.hallowelt.controllers;

import com.lkf.hallowelt.helpers.ResourceHelper;
import com.lkf.hallowelt.screens.mainmenu.MainMenuView;
import com.lkf.lib.base.LKFScreen;
import com.lkf.lib.helpers.CoordinateHelper;

public class MainMenuController extends ModelController
{

	@Override
	public LKFScreen getStartScreen()
	{
		// TODO Auto-generated method stub
		return new MainMenuView(this, 360, 640, ResourceHelper.COORDINATE_HELPER.focalLenth);
	}
	
	@Override
	public CoordinateHelper getCoordinateHelper()
	{
		return ResourceHelper.COORDINATE_HELPER;
	}
}
