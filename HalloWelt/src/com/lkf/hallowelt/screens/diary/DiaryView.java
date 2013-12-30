package com.lkf.hallowelt.screens.diary;

import java.io.IOException;
import java.util.ArrayList;

import com.lkf.hallowelt.controllers.DiaryController;
import com.lkf.lib.base.LKFController;
import com.lkf.lib.base.LKFScreen;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent;
import com.lkf.lib.helpers.FingerHelper;
import com.lkf.lib.physics.Rectangle2D;
import com.lkf.lib.render.ColorBatcher;
import com.lkf.lib.render.Sprite;
import com.lkf.lib.render.SpriteBatcher;
import com.lkf.lib.render.Texture;
import com.lkf.lib.render.TextureRegion;

public class DiaryView extends LKFScreen
{
	//Base Members.
	private final DiaryController theController;
	private SpriteBatcher theBatcher;
	private ColorBatcher theColorBatcher;
	
	//Pictures
	private Texture theDiaryAtlas;
	private Texture theComponentsAtlas;
	private ArrayList<Texture> thePhotos;
	
	//Communicate Members
	private Sprite thePencil;
	private Sprite thePencilclk;
	private Sprite theEraser;
	private Sprite theEraserclk;
	private Sprite thePhoto;
	private Sprite theText;
	private Sprite theBin;
	private Sprite theBack;
	private Sprite theBackclk;
	private Sprite theForward;
	private Sprite theForwardclk;
	
	private Sprite thePencils;
	private Sprite thePencilsclk;
	private Sprite theErasers;
	private Sprite theErasersclk;
	private Sprite theBlack;
	private Sprite theBlackclk;
	private Sprite theWhite;
	private Sprite theWhiteclk;
	private Sprite theRed;
	private Sprite theRedclk;
	private Sprite theYellow;
	private Sprite theYellowclk;
	private Sprite theBlue;
	private Sprite theBlueclk;
	private Sprite theGreen;
	private Sprite theGreenclk;
	
	public DiaryView(DiaryController controller, float width, float height, float focalLength)
	{
		super(width, height, focalLength);
		// TODO Auto-generated constructor stub
        theController = controller;
		
		theBatcher = new SpriteBatcher(5, controller);
		thePencil = new Sprite(new Rectangle2D(0, 435, 70, 70));
		thePencilclk = new Sprite(new Rectangle2D(0, 435, 70, 70)); // Click on
		theEraser = new Sprite(new Rectangle2D(0, 360, 70, 70));
		theEraserclk = new Sprite(new Rectangle2D(0, 360, 70, 70)); // Click on
		thePhoto = new Sprite(new Rectangle2D(0, 290, 70, 70));
		theText = new Sprite(new Rectangle2D(0, 215, 70, 70));
		theBin = new Sprite(new Rectangle2D(0, 135, 70, 70));
		theBack = new Sprite(new Rectangle2D(30, 21, 43, 39));
		theBackclk = new Sprite(new Rectangle2D(30, 21, 43, 39)); // Click on
		theForward = new Sprite(new Rectangle2D(287, 21, 43, 39));
		theForwardclk = new Sprite(new Rectangle2D(287, 21, 43, 39)); // Click on
		
		thePencils = new Sprite(new Rectangle2D(0, 435, 70, 70));
		thePencilsclk = new Sprite(new Rectangle2D(0, 435, 70, 70));
		theErasers = new Sprite(new Rectangle2D(0, 135, 70, 70));
		theErasersclk = new Sprite(new Rectangle2D(0, 135, 70, 70));
		theBlack = new Sprite(new Rectangle2D(0, 360, 70, 70));
		theBlackclk = new Sprite(new Rectangle2D(0, 360, 70, 70));
		theWhite = new Sprite(new Rectangle2D(15, 370, 20, 20));
		theWhiteclk = new Sprite(new Rectangle2D(15, 370, 20, 20));
		theRed = new Sprite(new Rectangle2D(15, 330, 20, 20));
		theRedclk = new Sprite(new Rectangle2D(15, 330, 20, 20));
		theYellow = new Sprite(new Rectangle2D(15, 290, 20, 20));
		theYellowclk = new Sprite(new Rectangle2D(15, 290, 20, 20));
		theBlue = new Sprite(new Rectangle2D(15, 250, 20, 20));
		theBlueclk = new Sprite(new Rectangle2D(15, 250, 20, 20));
		theGreen = new Sprite(new Rectangle2D(15, 210, 20, 20));
		theGreenclk = new Sprite(new Rectangle2D(15, 210, 20, 20));

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
		try
		{
			theDiaryAtlas = new Texture(theController.readAssetFile("DiaryBackground.png"));
			theComponentsAtlas = new Texture(theController.readAssetFile("DiaryView.png"));
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
		thePencil.setTexture(new TextureRegion(140, 570, 70, 70));
		thePencilclk.setTexture(new TextureRegion(280, 570, 70, 70)); // Click on
		theEraser.setTexture(new TextureRegion(0, 570, 70, 70));
		theEraserclk.setTexture(new TextureRegion(210, 570, 70, 70)); // Click on
		thePhoto.setTexture(new TextureRegion(0, 500, 70, 70));
		theText.setTexture(new TextureRegion(70, 570, 70, 70));
		theBin.setTexture(new TextureRegion(70, 500, 70, 70));
		theBack.setTexture(new TextureRegion(140, 531, 43, 39));
		theBackclk.setTexture(new TextureRegion(210, 531, 43, 39)); // Click on
		theForward.setTexture(new TextureRegion(210, 461, 43, 39));
		theForwardclk.setTexture(new TextureRegion(140, 461, 43, 39)); // Click on
		
		thePencils.setTexture(new TextureRegion(140, 570, 70, 70));
		thePencilsclk.setTexture(new TextureRegion(140, 570, 70, 70));
		theErasers.setTexture(new TextureRegion(0, 570, 70, 70));
		theErasersclk.setTexture(new TextureRegion(0, 570, 70, 70));
		theBlack.setTexture(new TextureRegion(70, 480, 20, 20));
		theBlackclk.setTexture(new TextureRegion(0, 480, 70, 70));
		theWhite.setTexture(new TextureRegion(70, 450, 20, 20));
		theWhiteclk.setTexture(new TextureRegion(0, 450, 70, 70));
		theRed.setTexture(new TextureRegion(70, 420, 20, 20));
		theRedclk.setTexture(new TextureRegion(0, 420, 70, 70));
		theYellow.setTexture(new TextureRegion(70, 390, 20, 20));
		theYellowclk.setTexture(new TextureRegion(0, 390, 70, 70));
		theBlue.setTexture(new TextureRegion(70, 360, 20, 20));
		theBlueclk.setTexture(new TextureRegion(0, 360, 70, 70));
		theGreen.setTexture(new TextureRegion(70, 330, 20, 20));
		theGreenclk.setTexture(new TextureRegion(0, 330, 70, 70));
		TextureRegion.dispose();

	}
	
	@Override
	public void present(float deltaTime)
	{
		// TODO Auto-generated method stub
        matrixInit();
		
		theController.textureRenderInit();
		theBatcher.beginBatch(theDiaryAtlas);
		theBatcher.drawBackground();
		theBatcher.endBatch();
		alphaRenderInit();
		theBatcher.beginBatch(theComponentsAtlas);
		theBatcher.drawSprite(thePencil);
		theBatcher.drawSprite(thePencilclk);
		theBatcher.drawSprite(theEraser);
		theBatcher.drawSprite(theEraserclk);
		theBatcher.drawSprite(thePhoto);
		theBatcher.drawSprite(theText);
		theBatcher.drawSprite(theBin);
		theBatcher.drawSprite(theBack);
		theBatcher.drawSprite(theBackclk);
		theBatcher.drawSprite(theForward);
		theBatcher.drawSprite(theForwardclk);
		
		theBatcher.drawSprite(thePencils);
		theBatcher.drawSprite(thePencilsclk);
		theBatcher.drawSprite(theErasers);
		theBatcher.drawSprite(theErasersclk);
		theBatcher.drawSprite(theBlack);
		theBatcher.drawSprite(theBlackclk);
		theBatcher.drawSprite(theWhite);
		theBatcher.drawSprite(theWhiteclk);
		theBatcher.drawSprite(theRed);
		theBatcher.drawSprite(theRedclk);
		theBatcher.drawSprite(theYellow);
		theBatcher.drawSprite(theYellowclk);
		theBatcher.drawSprite(theBlue);
		theBatcher.drawSprite(theBlueclk);
		theBatcher.drawSprite(theGreen);
		theBatcher.drawSprite(theGreenclk);
		theBatcher.endBatch();

	}

	@Override
	protected LKFController getController()
	{
		// TODO Auto-generated method stub
		return theController;
	}

}
