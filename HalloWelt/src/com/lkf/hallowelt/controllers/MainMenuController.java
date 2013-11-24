package com.lkf.hallowelt.controllers;

import com.lkf.hallowelt.screens.mainmenu.MainMenuView;
import com.lkf.lib.base.LKFScreen;

public class MainMenuController extends ModelController
{

	@Override
	public LKFScreen getStartScreen()
	{
		// TODO Auto-generated method stub
		return new MainMenuView(this);
	}

}
