package com.lkf.hallowelt.screens.diary;

import com.lkf.hallowelt.controllers.DiaryController;
import com.lkf.lib.base.LKFController;
import com.lkf.lib.base.LKFScreen;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent;
import com.lkf.lib.helpers.FingerHelper;
import com.lkf.lib.render.Sprite;
import com.lkf.lib.render.SpriteBatcher;

public class PhotoChooseView extends LKFScreen
{
	//Base Members.
	private final DiaryController theController;
	private SpriteBatcher theBatcher;
	
	//Communicate Members
	private Sprite forward;
	private Sprite backward;
	private Sprite confirm;
	
	
	public PhotoChooseView(DiaryController controller, float width, float height, float focalLength)
	{
		super(width, height, focalLength);
		// TODO Auto-generated constructor stub
		theController = controller;
	}

	@Override
	public void present(float deltaTime)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void touchDownExecute(FingerHelper finger)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void touchMoveExecute(FingerHelper finger)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void touchUpExecute(FingerHelper finger)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void keyPressExecute(LKFKeyEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void textureLoad()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void textureRegionInit()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected LKFController getController()
	{
		// TODO Auto-generated method stub
		return theController;
	}

}
