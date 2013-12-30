package com.lkf.hallowelt.screens.diary;

import java.io.IOException;

import android.database.Cursor;
import android.view.KeyEvent;

import com.lkf.hallowelt.controllers.DiaryController;
import com.lkf.lib.base.LKFController;
import com.lkf.lib.base.LKFScreen;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent.KeyState;
import com.lkf.lib.helpers.FingerHelper;
import com.lkf.lib.render.Sprite;
import com.lkf.lib.render.SpriteBatcher;
import com.lkf.lib.render.Texture;

public class PhotoChooseView extends LKFScreen
{
	//Base Members.
	private final DiaryController theController;
	private SpriteBatcher theBatcher;
	
	//Pictures
	private Texture thePicture;
	private Texture theComponentsAtlas;
	
	//Communicate Members
	private Sprite forward;
	private Sprite backward;
	private Sprite confirm;
	
	private boolean canForward;
	private boolean canBackward;
	
	//data
	private Cursor photos;
	private int pageNumber;
	private int current;
	
	public PhotoChooseView(DiaryController controller, float width, float height, float focalLength)
	{
		super(width, height, focalLength);
		// TODO Auto-generated constructor stub
		theController = controller;
		theBatcher = new SpriteBatcher(5, controller);
	}

	@Override
	public void present(float deltaTime)
	{
		// TODO Auto-generated method stub
		matrixInit();
		
		theController.textureRenderInit();
		theBatcher.beginBatch(thePicture);
		theBatcher.drawBackground();
		theBatcher.endBatch();
		alphaRenderInit();
		theBatcher.beginBatch(theComponentsAtlas);
		if (canForward)
		{
			theBatcher.drawSprite(forward);
		}
		if (canBackward)
		{
			theBatcher.drawSprite(backward);
		}
		theBatcher.drawSprite(confirm);
		theBatcher.endBatch();
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
		if (canForward && forward.touchCheck(finger.getPosition()))
		{
			photos.moveToPrevious();
			setPhoto();
		}
		else if (canBackward && backward.touchCheck(finger.getPosition()))
		{
			photos.moveToNext();
			setPhoto();
		}
		else if (confirm.touchCheck(finger.getPosition()))
		{
			theController.getDatabaseHelper().insertPhoto(photos.getInt(0), theController.getDiaryID(), theController.getCurrentPage());
			theController.getToDiary();
		}
	}

	@Override
	protected void keyPressExecute(LKFKeyEvent event)
	{
		// TODO Auto-generated method stub
		if (event.type == KeyState.KEY_UP)
		{
			switch (event.keyCode)
			{
			case KeyEvent.KEYCODE_BACK:
			{
				theController.getToDiary();
				break;
			}
			default:
				break;
			}
		}
	}

	@Override
	protected void textureLoad()
	{
		// TODO Auto-generated method stub
		photos = theController.getDatabaseHelper().getPhotos();
		pageNumber = photos.getCount();
		if (pageNumber > 0)//must have at least one picture
		{
			photos.moveToFirst();
			current = 1;
			setPhoto(); 
		}
	}
	
	private void setPhoto()
	{
		try
		{
			thePicture = new Texture(theController.readPhoto(photos.getString(1) + ".png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (current == 1)
		{
			canForward = false;
		}
		else 
		{
			canForward = true;
		}
		if (current < pageNumber)
		{
			canBackward = true;
		}
		else 
		{
			canBackward = false;
		}
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
