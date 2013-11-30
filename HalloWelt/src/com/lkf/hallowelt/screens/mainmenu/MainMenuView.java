package com.lkf.hallowelt.screens.mainmenu;

import java.io.IOException;

import android.util.Log;

import com.lkf.hallowelt.controllers.MainMenuController;
import com.lkf.lib.base.LKFController;
import com.lkf.lib.base.LKFScreen;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent;
import com.lkf.lib.base.framework.InputBase.LKFTouchEvent;
import com.lkf.lib.physics.Plane3D;
import com.lkf.lib.physics.Rectangle2D;
import com.lkf.lib.physics.Vector3D;
import com.lkf.lib.render.Sprite;
import com.lkf.lib.render.SpriteBatcher;
import com.lkf.lib.render.Texture;
import com.lkf.lib.render.TextureRegion;

public class MainMenuView extends LKFScreen
{
	//Base members.
	private final MainMenuController theController;
	private SpriteBatcher theBatcher;
	private Plane3D thePlane;
	
	//Pictures
	private Texture theBackgroundAtlas;
	private Texture theComponentsAtlas;
	
	//Communicate Member
	private Sprite theWindow;
	private Sprite theMap;
	private Sprite theBookcase;
	
	//Flags
	private boolean textureInit;
	
	public MainMenuView(LKFController controller, float width, float height)
	{
		super(width, height); 
		// TODO Auto-generated constructor stub
		theController = (MainMenuController) controller;
		thePlane = new Plane3D(new Vector3D(0, 0, 0), new Vector3D(0, 1, 0));
		
		theBatcher = new SpriteBatcher(5, controller, 360, 640);
		theWindow = new Sprite(new Rectangle2D(10, 333, 130, 207));
		theMap = new Sprite(new Rectangle2D(170, 470, 170, 120));
		theBookcase = new Sprite(new Rectangle2D(160, 50, 200, 380));
		
		//Flag init.
		textureInit = true;
	}

	@Override
	protected void touchDownExecute(LKFTouchEvent event)
	{
		// TODO Auto-generated method stub
		test(event.pointer);
	}

	@Override
	protected void touchMoveExecute(LKFTouchEvent event)
	{
		// TODO Auto-generated method stub
		test(event.pointer);
	}

	@Override
	protected void touchUpExecute(LKFTouchEvent event)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void keyPressExecute(LKFKeyEvent event)
	{
		// TODO Auto-generated method stub
		
	}
	
	private void test(int fingerID)
	{
		Log.v("hehe", theController.getFinger(fingerID).getPosition().x + "  " + theController.getFinger(fingerID).getPosition().y);
	}

	@Override
	public void present(float deltaTime)
	{
		// TODO Auto-generated method stub
		theController.textureRenderInit();
		
		theBatcher.beginBatch(theBackgroundAtlas);
		theBatcher.drawBackground();
		theBatcher.endBatch();
		alphaRenderInit();
		theBatcher.beginBatch(theComponentsAtlas);
		theBatcher.drawSprite(theWindow);
		theBatcher.drawSprite(theMap);
		theBatcher.drawSprite(theBookcase);
		theBatcher.endBatch();
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume()
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
		
		if (textureInit)
		{
			TextureRegion.textureLoad(theComponentsAtlas);
			theWindow.setTexture(new TextureRegion(10, 100, 130, 207));
			theMap.setTexture(new TextureRegion(170, 50, 170, 120));
			theBookcase.setTexture(new TextureRegion(160, 210, 200, 380));
			TextureRegion.dispose();
			
			textureInit = false;
		}
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

	@Override
	protected Plane3D getPlane()
	{
		// TODO Auto-generated method stub
		return thePlane;
	}
}
