package com.lkf.hallowelt.screens.mainmenu;

import java.io.IOException;

import android.view.KeyEvent;

import com.lkf.hallowelt.controllers.MainMenuController;
import com.lkf.lib.base.LKFController;
import com.lkf.lib.base.LKFScreen;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent.KeyState;
import com.lkf.lib.helpers.FingerHelper;
import com.lkf.lib.physics.Rectangle2D;
import com.lkf.lib.physics.Vector2D;
import com.lkf.lib.render.Sprite;
import com.lkf.lib.render.SpriteBatcher;
import com.lkf.lib.render.Texture;
import com.lkf.lib.render.TextureRegion;

public class MainMenuView extends LKFScreen
{
	//Base Members.
	private final MainMenuController theController;
	private SpriteBatcher theBatcher;
	
	//Pictures
	private Texture theBackgroundAtlas;
	private Texture theComponentsAtlas;
	
	//Communicate Members
	private Sprite theWindow;
	private Sprite theMap;
	private Sprite theBookcase;
	private Sprite theHelp;
	
	private int delay = 50;
	
	public MainMenuView(MainMenuController controller, float width, float height, float focalLength)
	{
		super(width, height, focalLength); 
		// TODO Auto-generated constructor stub
		theController = controller;
		
		theBatcher = new SpriteBatcher(5, controller);
		theWindow = new Sprite(new Rectangle2D(10, 250, 140, 215));
		theMap = new Sprite(new Rectangle2D(170, 440, 170, 125));
		theBookcase = new Sprite(new Rectangle2D(150, 65, 210, 320));
        theHelp = new Sprite(new Rectangle2D(20, 25, 130, 120));
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
		if (theWindow.touchCheck(finger.getPosition()))
		{
			moveCamera(theWindow.getPosition().getCenter(), theWindow.getPosition().width, delay);
		}
		else if (theMap.touchCheck(finger.getPosition()))
		{
			moveCamera(theMap.getPosition().getCenter(), theMap.getPosition().width, delay);
		}
		else if (theBookcase.touchCheck(finger.getPosition()))
		{
			moveCamera(theBookcase.getPosition().getCenter(), theBookcase.getPosition().width, delay);
		}
		else if (theHelp.touchCheck(finger.getPosition()))
		{
			moveCamera(theHelp.getPosition().getCenter(), theHelp.getPosition().width, delay);
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
				moveCamera(new Vector2D(worldWidth / 2, worldHeight / 2), worldWidth, delay);
				break;
			}
			case KeyEvent.KEYCODE_MENU:
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
			theBackgroundAtlas = new Texture(theController.readAssetFile("RoomBackground.png"));
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
		theWindow.setTexture(new TextureRegion(10, 150, 140, 215));
		theMap.setTexture(new TextureRegion(170, 50, 170, 125));
		theBookcase.setTexture(new TextureRegion(150, 230, 210, 320));
        theHelp.setTexture(new TextureRegion(20, 470, 130, 120));
		TextureRegion.dispose();
	}

	@Override
	public void present(float deltaTime)
	{
		// TODO Auto-generated method stub
		matrixInit();
		
		theController.textureRenderInit();
		theBatcher.beginBatch(theBackgroundAtlas);
		theBatcher.drawBackground();
		theBatcher.endBatch();
		alphaRenderInit();
		theBatcher.beginBatch(theComponentsAtlas);
		theBatcher.drawSprite(theWindow);
		theBatcher.drawSprite(theMap);
		theBatcher.drawSprite(theBookcase);
		theBatcher.drawSprite(theHelp);
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
		return theController;
	}
}
