package com.lkf.hallowelt.screens.mainmenu;

import java.io.IOException;

import com.lkf.hallowelt.controllers.MainMenuController;
import com.lkf.lib.base.LKFController;
import com.lkf.lib.base.LKFScreen;
import com.lkf.lib.render.SpriteBatcher;
import com.lkf.lib.render.Texture;
import com.lkf.lib.render.TextureRegion;

public class MainMenuView extends LKFScreen
{
	//Base members.
	private final MainMenuController theController;
//	private Vector2D theTouchPosition;
	private SpriteBatcher theBatcher;
	
	//Pictures
	private Texture theBackgroundAtlas;
	private TextureRegion theBackground;
	private Texture theComponentsAtlas;
	private TextureRegion theWindow;
	
	//Flags
	private boolean textureInit;
	
	public MainMenuView(LKFController controller)
	{
		super(); 
		// TODO Auto-generated constructor stub
		theController = (MainMenuController) controller;
//		theTouchPosition = new Vector2D();
		theBatcher = new SpriteBatcher(2, controller, 360, 640);
		
		//Flag init.
		textureInit = true;
	}

	@Override
	public void update(float deltaTime)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void present(float deltaTime)
	{
		// TODO Auto-generated method stub
		theController.cameraInit();
		theController.rotateInSceneXY(0);
		theController.textureRenderInit();
		
		theController.moveInSceneXY(0, 0);
		theBatcher.beginBatch(theBackgroundAtlas);
		theBatcher.drawBackground();
		theBatcher.endBatch();
/*		theBatcher.beginBatch(theComponentsAtlas);
		theBatcher.drawSprite(100, 100, 128, 207, theWindow);
		theBatcher.endBatch();*/
		
		theCounter.logFrame();
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
			theBackgroundAtlas = new Texture(theController.readAssetFile("background.png"));
			theComponentsAtlas = new Texture(theController.readAssetFile("window.png"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (textureInit)
		{
			TextureRegion.textureLoad(theBackgroundAtlas);
			theBackground = TextureRegion.getFullTextureRegion();
			TextureRegion.dispose();
			TextureRegion.textureLoad(theComponentsAtlas);
			theWindow = TextureRegion.getFullTextureRegion();
			TextureRegion.dispose();
			
			textureInit = false;
		}
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub

	}

}
