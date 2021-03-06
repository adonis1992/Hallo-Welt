package com.lkf.hallowelt.screens.widget;

import java.util.ArrayList;
import java.util.HashSet;

import android.util.SparseArray;

import com.lkf.lib.base.Pool;
import com.lkf.lib.base.Pool.PoolFactory;
import com.lkf.lib.helpers.FingerHelper;
import com.lkf.lib.physics.BezierLine2D;
import com.lkf.lib.physics.Vector2D;
import com.lkf.lib.render.ColorBatcher;

public class ScreenPen
{	
	private static enum PenState
	{
		Init, Normal, Dead, Finish
	}
	
	private static float INTERVAL = 4f;
	
	private static boolean recordFlag;
	
	private static Pool<ScreenPen> PenPool;
	private static HashSet<Integer> deleteIDs = new HashSet<Integer>();
	private static HashSet<Integer> IDs = new HashSet<Integer>();
	private static SparseArray<ScreenPen> Pens = new SparseArray<ScreenPen>();
	
	static
	{
		PoolFactory<ScreenPen> factory = new PoolFactory<ScreenPen>() {

			@Override
			public ScreenPen createObject()
			{
				// TODO Auto-generated method stub
				return new ScreenPen();
			}
		};
		
		PenPool = new Pool<ScreenPen>(factory, 20);
		recordFlag = false;
	}
	
	public static void initLine(FingerHelper finger)
	{
		IDs.add(finger.getID());
		Pens.put(finger.getID(), PenPool.newObject());
		Pens.get(finger.getID()).addPointFromTouch(finger.getPosition());
	}
	
	public static void move(FingerHelper finger)
	{
		Pens.get(finger.getID()).addPointFromTouch(finger.getPosition());
	}
	
	public static void endLine(FingerHelper finger)
	{
		Pens.get(finger.getID()).addPointFromTouch(finger.getPosition());
		Pens.get(finger.getID()).state = PenState.Dead;
		recordFlag = true;
	}
	
	public static boolean needDraw()
	{
		return (IDs.size() > 0);
	}
	
	public static boolean needRecord()
	{
		return recordFlag;
	}
	
	public static void draw(ColorBatcher theColorBatcher)
	{
		for (Integer penID : IDs)
		{
			ScreenPen pen = Pens.get(penID);
			if (pen.drawFlag)
			{
				theColorBatcher.draw(pen.theDrawPoints, pen.theWidths, pen.startFlag, pen.endFlag);
			}	
		}
	}
	
	public static void drawProcess()
	{
		for (Integer penID : IDs)
		{
			ScreenPen pen = Pens.get(penID);
			if (pen.state != PenState.Finish)
			{
				pen.prepareForDraw();
				if (pen.state == PenState.Dead)
				{
					pen.PrepareForEnd();
					pen.state = PenState.Finish;
				}
			}
		}
	}
	
	public static void saveProcess()
	{
		for (Integer penID : IDs)
		{
			ScreenPen pen = Pens.get(penID);
			if (pen.state == PenState.Finish)
			{
				pen.pointsClear();
				pen.state = PenState.Init;
				pen.startFlag = true;
				pen.endFlag = false;
				PenPool.free(pen);
				Pens.remove(penID);
				deleteIDs.add(penID);
			}
			else 
			{
				pen.spilt();
			}
		}
		for (Integer penID : deleteIDs)
		{
			IDs.remove(penID);
		}
		deleteIDs.clear();
		recordFlag = false;
	}
	
	private ArrayList<Vector2D> theGetPoints;
	private ArrayList<Vector2D> theDrawPoints;
	private ArrayList<Vector2D> theWidths;
	private Vector2D currentPoint;
	
	private float lineT;
	private PenState state;
	private boolean drawFlag;
	private boolean startFlag;
	private boolean endFlag;
	
	private ScreenPen()
	{
		theGetPoints = new ArrayList<Vector2D>();
		theDrawPoints = new ArrayList<Vector2D>();
		theWidths = new ArrayList<Vector2D>();
		
		currentPoint = new Vector2D();
		lineT = -1;
		drawFlag = false;
		startFlag = true;
		endFlag = false;
		state = PenState.Init;
	}
	
	private void pointsClear()
	{
		theDrawPoints.clear();
		theWidths.clear();
		
		drawFlag = false;
	}
	
	private void spilt()
	{
		Vector2D point = theDrawPoints.get(theDrawPoints.size() - 1);
		Vector2D pointLeft = theWidths.get(theWidths.size() - 2);
		Vector2D pointRight = theWidths.get(theWidths.size() - 1);
		pointsClear();
		theDrawPoints.add(point);
		theWidths.add(pointLeft);
		theWidths.add(pointRight);
		startFlag = false;
	}
	
	public boolean getInit()
	{
		return (state == PenState.Init);
	}
	
	public void addPointFromTouch(Vector2D touchPosition)
	{
		if (state == PenState.Init)
		{
			currentPoint.set(touchPosition);
			theGetPoints.add(touchPosition.copy());
			state = PenState.Normal;
		}
		else if	(touchPosition.copy().sub(currentPoint).length() > 2 * INTERVAL)
		{
			theGetPoints.add(touchPosition.copy());
		}
	}
	
	private void prepareForDraw()
	{	
		if (theGetPoints.size() > 3)
		{
			Vector2D formerLine;//Lines!!!!
			Vector2D onLinePoint = new Vector2D();
			
			float tLength;
			
			while (theGetPoints.size() > 3)
			{
				BezierLine2D.setBezierPoint(theGetPoints.get(0), theGetPoints.get(1), theGetPoints.get(2), theGetPoints.get(3));
				tLength = INTERVAL / BezierLine2D.getLineLength();
				
				if (lineT == -1)
				{
					lineT = tLength;
					currentPoint.set(theGetPoints.get(0));
					onLinePoint.set(currentPoint);
					theDrawPoints.add(currentPoint.copy());
				}
				
				while (lineT < 1)
				{
					onLinePoint.set(BezierLine2D.getPoint(lineT));
					theDrawPoints.add(onLinePoint.copy());
					formerLine = onLinePoint.copy().sub(currentPoint);
					theWidths.add(onLinePoint.add(formerLine.getVerticalLine(lineT * 0.5f / tLength, true)));
					theWidths.add(onLinePoint.add(formerLine.getVerticalLine(lineT * 0.5f / tLength, false)));
					currentPoint.set(onLinePoint);
					lineT += tLength;
				}
				
				lineT -= 1;
				theGetPoints.remove(0);
				theGetPoints.remove(0);
				theGetPoints.remove(0);
			}
			
			drawFlag = true;
		}
	}
	
	private void PrepareForEnd()
	{
		Vector2D formerLine;//Lines!!!!		
		int pointsNumber = theGetPoints.size();
		
		endFlag = true;
		
		if (theDrawPoints.size() == 0)
		{
			switch (pointsNumber)
			{
			case 1:
			{
				Vector2D point = theGetPoints.get(0);
				theDrawPoints.add(point.copy());
				theWidths.add(point.copy().add(4,4));
				theWidths.add(point.copy().add(-4,4));
				theWidths.add(point.copy().add(4,-4));
				theWidths.add(point.copy().add(-4,-4));
				break;
			}
			case 2:
			{
				Vector2D point1 = theGetPoints.get(0);
				Vector2D point3 = theGetPoints.get(1);
				Vector2D point2 = point1.copy().add(point3).mul(0.5f);
				
				theDrawPoints.add(point1.copy());
				theDrawPoints.add(point2.copy());
				theDrawPoints.add(point3.copy());
				
				formerLine = point2.copy().sub(point3);
				theWidths.add(point2.add(formerLine.getVerticalLine(0.5f, true)));
				theWidths.add(point2.add(formerLine.getVerticalLine(0.5f, false)));
				break;
			}
			case 3:
			{
				Vector2D point1 = theGetPoints.get(0);
				Vector2D point2 = theGetPoints.get(1);
				Vector2D point3 = theGetPoints.get(2);
				Vector2D onLinePoint = new Vector2D();
				currentPoint.set(point1);
				BezierLine2D.setBezierPoint(point1, point2, point3);
				
				theDrawPoints.add(point1.copy());
				
				float tLength = INTERVAL / BezierLine2D.getLineLength();
				lineT = tLength;
				
				while (lineT < 1)
				{
					onLinePoint.set(BezierLine2D.getPoint(lineT));
					theDrawPoints.add(onLinePoint.copy());
					formerLine = onLinePoint.copy().sub(currentPoint);
					theWidths.add(onLinePoint.add(formerLine.getVerticalLine(lineT * 0.5f / tLength, true)));
					theWidths.add(onLinePoint.add(formerLine.getVerticalLine(lineT * 0.5f / tLength, false)));
					currentPoint.set(onLinePoint);
					lineT += tLength;
				}
				if (!currentPoint.equals(point3))
				{
					theDrawPoints.add(point3.copy());
				}
				break;
			}
			}
		}
		else 
		{
			switch (pointsNumber)
			{
			case 1:
			{
				theDrawPoints.remove(theDrawPoints.size() - 1);
				theDrawPoints.add(theGetPoints.get(0).copy());
				break;
			}
			case 2:
			{
				Vector2D point1 = theDrawPoints.get(theDrawPoints.size() - 1);
				Vector2D point2 = theGetPoints.get(1);
				
				theDrawPoints.add(point2.copy());
				
				formerLine = point2.copy().sub(point1);
				theWidths.add(point1.add(formerLine.getVerticalLine(0.5f, true)));
				theWidths.add(point1.add(formerLine.getVerticalLine(0.5f, false)));
				break;
			}
			case 3:
			{
				Vector2D point1 = theGetPoints.get(0);
				Vector2D point2 = theGetPoints.get(1);
				Vector2D point3 = theGetPoints.get(2);
				Vector2D onLinePoint = new Vector2D();
				BezierLine2D.setBezierPoint(point1, point2, point3);
				
				float tLength = INTERVAL / BezierLine2D.getLineLength();
				
				while (lineT < 1)
				{
					onLinePoint.set(BezierLine2D.getPoint(lineT));
					theDrawPoints.add(onLinePoint.copy());
					formerLine = onLinePoint.copy().sub(currentPoint);
					theWidths.add(onLinePoint.add(formerLine.getVerticalLine(lineT * 0.5f / tLength, true)));
					theWidths.add(onLinePoint.add(formerLine.getVerticalLine(lineT * 0.5f / tLength, false)));
					currentPoint.set(onLinePoint);
					lineT += tLength;
				}
				if (currentPoint.equals(point3))
				{
					theDrawPoints.remove(theDrawPoints.size() - 1);
					theDrawPoints.add(point3.copy());
				}
				break;
			}
			}
		}
		
		drawFlag = true;
		theGetPoints.clear();
		lineT = -1;
	}
}
