package com.lkf.hallowelt.screens.diary;

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
		plusMark = new Sprite(null);//你来加大小
		deleteMark = new Sprite(null);//加大小
		
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
//			theCases.add(new Sprite(getCasePosition(current)));
		}
		for (int current = 0; current < bookNumber; current++)
		{
			cursor.moveToNext();
			theBookIDs.add(cursor.getInt(0));
			theBooks.add(new LongPressSprite(getBookPosition(current)));
		}
	}
	
/*	private Rectangle2D getCasePosition(int row)
	{
		float positionY = xxx + xxx * row;
		return new Rectangle2D(xxx, positionY, xxx, xxx);//剩下数据你填
	}*/
	
	private Rectangle2D getBookPosition(int order)
	{
		return null;
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
		TextureRegion.textureLoad(theDiaryAtlas);
		for (Sprite theCase : theCases)
		{
			theCase.setTexture(null); //xieshang
		}
		for (LongPressSprite theBook : theBooks)
		{
			theBook.setTexture(null);//xieshang
		}
		plusMark.setTexture(null);//xieshang
		deleteMark.setTexture(null);//xieshang
		TextureRegion.dispose();
	}

	@Override
	protected LKFController getController()
	{
		// TODO Auto-generated method stub
		return theController;
	}

}
