package com.lkf.hallowelt.screens.camera;

import java.io.IOException;
import android.view.KeyEvent;

import com.lkf.hallowelt.controllers.CameraController;
import com.lkf.hallowelt.screens.widget.ScreenPen;
import com.lkf.lib.base.LKFController;
import com.lkf.lib.base.LKFScreen;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent.KeyState;
import com.lkf.lib.helpers.FingerHelper;
import com.lkf.lib.physics.Rectangle2D;
import com.lkf.lib.render.ColorBatcher;
import com.lkf.lib.render.Sprite;
import com.lkf.lib.render.SpriteBatcher;
import com.lkf.lib.render.Texture;
import com.lkf.lib.render.TextureRegion;

public class CameraView extends LKFScreen
{
	//Base Members.
	private final CameraController theController;
	private SpriteBatcher theBatcher;
	private ColorBatcher theColorBatcher;
	
	//Pictures 
	private Texture theComponentsAtlas;
	private Texture theBrushtoolAtlas;
	
	//Communicate Members
	private Sprite theBack;
	private Sprite theFilter;
	private Sprite theCamera;
	private Sprite thePencil;
	private Sprite theBrushback;
	private Sprite theBin;
	private Sprite theEraser;
	private Sprite theBlack;
	private Sprite theWhite;
	private Sprite theRed;
	private Sprite theYellow;
	private Sprite theBlue;
	private Sprite theGreen;
	
	public CameraView(CameraController controller, float width, float height, float focalLength)
	{
		super(width, height, focalLength); 
		// TODO Auto-generated constructor stub
		theController = controller;
		
		theBatcher = new SpriteBatcher(14, controller);
		theBack = new Sprite(new Rectangle2D(0, 0, 360, 73));
		theFilter = new Sprite(new Rectangle2D(20, 8, 56, 57));
		theCamera = new Sprite(new Rectangle2D(140, 5, 76, 60));
		thePencil = new Sprite(new Rectangle2D(285, 9, 56, 56));
		theBrushback = new Sprite(new Rectangle2D(0, 73, 360, 73));
		theBin = new Sprite(new Rectangle2D(10, 80, 42, 50));
		theEraser = new Sprite(new Rectangle2D(300, 80, 54, 52));
		theBlack = new Sprite(new Rectangle2D(70, 95, 20, 20));
		theWhite = new Sprite(new Rectangle2D(110, 95, 20, 20));
		theRed = new Sprite(new Rectangle2D(150, 95, 20, 20));
		theYellow = new Sprite(new Rectangle2D(190, 95, 20, 20));
		theBlue = new Sprite(new Rectangle2D(230, 95, 20, 20));
		theGreen = new Sprite(new Rectangle2D(270, 95, 20, 20));
		
		theColorBatcher = new ColorBatcher(controller);
	}

	@Override
	protected void touchDownExecute(FingerHelper finger)
	{
		// TODO Auto-generated method stub
		ScreenPen.initLine(finger);
	}

	@Override
	protected void touchMoveExecute(FingerHelper finger)
	{
		// TODO Auto-generated method stub
		ScreenPen.move(finger);
	}

	@Override
	protected void touchUpExecute(FingerHelper finger)
	{
		// TODO Auto-generated method stub
		ScreenPen.endLine(finger);
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
			theBrushtoolAtlas = new Texture(theController.readAssetFile("Brush.png"));
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
		TextureRegion.textureLoad(theBrushtoolAtlas);
		theBrushback.setTexture(new TextureRegion(0, 0, 360, 73));
		theBin.setTexture(new TextureRegion(0, 80, 42, 50));
		theEraser.setTexture(new TextureRegion(60, 80, 54, 52));
		theBlack.setTexture(new TextureRegion(120, 80, 20, 20));
		theWhite.setTexture(new TextureRegion(149, 80, 20, 20));
		theRed.setTexture(new TextureRegion(179, 80, 20, 20));
		theYellow.setTexture(new TextureRegion(210, 80, 20, 20));
		theBlue.setTexture(new TextureRegion(240, 80, 20, 20));
		theGreen.setTexture(new TextureRegion(270, 80, 20, 20));
		TextureRegion.dispose();
	}

	@Override
	public void present(float deltaTime)
	{
		// TODO Auto-generated method stub
		matrixInit();
		
		alphaRenderInit();
/*		theController.textureRenderInit();
		theBatcher.beginBatch(theComponentsAtlas);
		theBatcher.drawSprite(theBack);
		theBatcher.drawSprite(theFilter);
		theBatcher.drawSprite(theCamera);
		theBatcher.drawSprite(thePencil);
		theBatcher.endBatch();
		theBatcher.beginBatch(theBrushtoolAtlas);
		theBatcher.drawSprite(theBrushback);
		theBatcher.drawSprite(theBin);
		theBatcher.drawSprite(theEraser);
		theBatcher.drawSprite(theBlack);
		theBatcher.drawSprite(theWhite);
		theBatcher.drawSprite(theRed);
		theBatcher.drawSprite(theYellow);
		theBatcher.drawSprite(theBlue);
		theBatcher.drawSprite(theGreen);
		theBatcher.endBatch();*/
		
		theController.colorRenderInit();
		ScreenPen.draw(theColorBatcher);
//		ScreenPen.killLine();
//		theColorBatcher.draw();
		
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
