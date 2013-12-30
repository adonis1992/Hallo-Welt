package com.lkf.hallowelt.screens.diary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import android.database.Cursor;

import com.lkf.hallowelt.controllers.DiaryController;
import com.lkf.lib.base.LKFController;
import com.lkf.lib.base.LKFScreen;
import com.lkf.lib.base.framework.InputBase.LKFKeyEvent;
import com.lkf.lib.helpers.FingerHelper;
import com.lkf.lib.physics.Rectangle2D;
import com.lkf.lib.render.LongPressSprite;
import com.lkf.lib.render.Sprite;
import com.lkf.lib.render.SpriteBatcher;
import com.lkf.lib.render.Texture;
import com.lkf.lib.render.TextureRegion;

public class BookCaseView extends LKFScreen
{
	//Base Members.
	private final DiaryController theController;
	private SpriteBatcher theBatcher;
	
	//Pictures
	private Texture theDiaryAtlas;
	
	//Communicate Members
	private ArrayList<Sprite> theCases;
	private ArrayList<LongPressSprite> theBooks;
	private ArrayList<Integer> theBookIDs;
	private Sprite plusMark;
	private Sprite deleteMark;
	
	//dynamic things
	private int rowNumber;

	public BookCaseView(DiaryController controller, float width, float height, float focalLength)
	{
		super(width, height, focalLength);
		// TODO Auto-generated constructor stub
		theController = controller;
		
		theBatcher = new SpriteBatcher(5, controller);
		theCases = new ArrayList<Sprite>();
		theBooks = new ArrayList<LongPressSprite>();
		theBookIDs = new ArrayList<Integer>();
		plusMark = new Sprite(new Rectangle2D(40, 310, 44, 44));
		deleteMark = new Sprite(new Rectangle2D(40, 310, 31, 34));
		
		Cursor cursor = controller.getDatabaseHelper().getDiary(); 
		int bookNumber = cursor.getCount();
		if (bookNumber > 15)
		{
			rowNumber = bookNumber / 3 + 1;
		}
		else 
		{
			rowNumber = 5;
		}
		for (int current = 0; current < rowNumber; current++)
		{
 			theCases.add(new Sprite(getCasePosition(current)));
		}
		for (int current = 0; current < bookNumber; current++)
		{
			cursor.moveToNext();
			theBookIDs.add(cursor.getInt(0));
			theBooks.add(new LongPressSprite(getBookPosition(current)));
		}
	}
	
 	private Rectangle2D getCasePosition(int row)
	{
		float positionY = 136 + 136 * row;
		return new Rectangle2D(0, positionY, 360, 136);
	}
	
	private Rectangle2D getBookPosition(int order)
	{
		float positionX = 20 + 118 * ( (order + 2) % 3 );
		float positionY = 123 * ( (order + 2) / 3 );
		return new Rectangle2D(positionX, positionY, 360, 136);
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
		try
		{
			theDiaryAtlas = new Texture(theController.readAssetFile("Bookcase.png"));
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
		TextureRegion.textureLoad(theDiaryAtlas);
		for (Sprite theCase : theCases)
		{
			theCase.setTexture(new TextureRegion(0, 0, 360, 136));
		}
		for (LongPressSprite theBook : theBooks)
		{
			theBook.setTexture(new TextureRegion(0, 140, 84, 110));
		}
		plusMark.setTexture(new TextureRegion(0, 260, 44, 44));
		deleteMark.setTexture(new TextureRegion(120, 260, 31, 34));
		TextureRegion.dispose();
	}

	@Override
	protected LKFController getController()
	{
		// TODO Auto-generated method stub
		return theController;
	}

}
