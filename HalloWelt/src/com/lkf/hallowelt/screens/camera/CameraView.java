package com.lkf.hallowelt.screens.camera;

import java.io.IOException;

import android.view.KeyEvent;

import com.lkf.hallowelt.controllers.CameraController;
import com.lkf.lib.base.LKFController;
import com.lkf.lib.base.LKFScreen;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent.KeyState;
import com.lkf.lib.helpers.FingerHelper;
import com.lkf.lib.physics.Rectangle2D;
import com.lkf.lib.render.Sprite;
import com.lkf.lib.render.SpriteBatcher;
import com.lkf.lib.render.Texture;
import com.lkf.lib.render.TextureRegion;

public class CameraView extends LKFScreen
{
	//Base Members.
	private final LKFController theController;
	private SpriteBatcher theBatcher;
	
	//Pictures 
	private Texture theComponentsAtlas;
	
	//Communicate Members
	private Sprite test;
	
	public CameraView(LKFController controller, float width, float height, float focalLength)
	{
		super(width, height, focalLength); 
		// TODO Auto-generated constructor stub
		theController = controller;
		
		theBatcher = new SpriteBatcher(5, controller, 360, 640);
		test = new Sprite(new Rectangle2D(0, 285, 130, 205));
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
		if (test.touchCheck(finger.getPosition()))
		{
			moveCamera(test.getPosition().getCenter(), test.getPosition().width, 50);
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
				theController.finish();
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
		try
		{
			theComponentsAtlas = new Texture(theController.readAssetFile("MainMenuView.png"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void textureRegionInit()
	{
		// TODO Auto-generated method stub
		TextureRegion.textureLoad(theComponentsAtlas);
		test.setTexture(new TextureRegion(0, 150, 130, 205));
		TextureRegion.dispose();
	}

	@Override
	public void present(float deltaTime)
	{
		// TODO Auto-generated method stub
		theController.textureRenderInit();
		matrixInit();
		
		alphaRenderInit();
		theBatcher.beginBatch(theComponentsAtlas);
		theBatcher.drawSprite(test);
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
	protected LKFController getController()
	{
		// TODO Auto-generated method stub
		return theController;
	}

}
