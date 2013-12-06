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
	private final CameraController theController;
	private SpriteBatcher theBatcher;
	
	//Pictures 
	private Texture theComponentsAtlas;
	
	//Communicate Members
	private Sprite theBack;
	private Sprite theFilter;
	private Sprite theCamera;
	private Sprite thePencil;
	
	public CameraView(CameraController controller, float width, float height, float focalLength)
	{
		super(width, height, focalLength); 
		// TODO Auto-generated constructor stub
		theController = controller;
		
		theBatcher = new SpriteBatcher(5, controller);
		theBack = new Sprite(new Rectangle2D(0, 0, 360, 73));
		theFilter = new Sprite(new Rectangle2D(20, 8, 56, 57));
		theCamera = new Sprite(new Rectangle2D(140, 5, 76, 60));
		thePencil = new Sprite(new Rectangle2D(285, 9, 56, 56));
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
			theComponentsAtlas = new Texture(theController.readAssetFile("Camera.png"));
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
		theBack.setTexture(new TextureRegion(0, 0, 360, 73));
		theFilter.setTexture(new TextureRegion(20, 135, 56, 57));
		theCamera.setTexture(new TextureRegion(100, 135, 76, 60));
		thePencil.setTexture(new TextureRegion(200, 135, 56, 56));
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
		theBatcher.drawSprite(theBack);
		theBatcher.drawSprite(theFilter);
		theBatcher.drawSprite(theCamera);
		theBatcher.drawSprite(thePencil);
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
