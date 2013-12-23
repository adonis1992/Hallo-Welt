package com.lkf.hallowelt.screens.widget;

import java.util.ArrayList;
import java.util.HashSet;

import android.util.Log;
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
		Init, Normal, Dead
	}
	
	private static float INTERVAL = 4f;
	
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
		deleteIDs.add(finger.getID());
	}
	
/*	public static void killLine()
	{
		if (deleteIDs.size() > 0)
		{
			for (Integer penID : deleteIDs)
			{
				Pens.get(penID).init();
				PenPool.free(Pens.get(penID));
				Pens.remove(penID);
				IDs.remove(penID);
			}
		}
	}*/
	
	public static void draw(ColorBatcher theColorBatcher)
	{
		if (IDs.size() > 0)
		{
			for (Integer penID : IDs)
			{
				ScreenPen pen = Pens.get(penID);
				pen.prepareForDraw();
				if (pen.state == PenState.Dead)
				{
					pen.PrepareForEnd();
				}
				theColorBatcher.draw(pen.theDrawPoints, pen.theWidths, false);
			}
		}
	}
	
	private ArrayList<Vector2D> theGetPoints;
	private ArrayList<Vector2D> theDrawPoints;
	private ArrayList<Vector2D> theWidths;
	private ArrayList<Vector2D> theTempPoints;
	private Vector2D currentPoint;
	
	private float lineT;
	private PenState state;
	
	private ScreenPen()
	{
		theGetPoints = new ArrayList<Vector2D>();
		theDrawPoints = new ArrayList<Vector2D>();
		theWidths = new ArrayList<Vector2D>();
		theTempPoints = new ArrayList<Vector2D>();
		
		currentPoint = new Vector2D();
		lineT = -1;
		init();
	}
	
	private void init()
	{		
		theDrawPoints.clear();
		theWidths.clear();
		
		state = PenState.Init;
	}
	
	public boolean getInit()
	{
		return (state == PenState.Init);
	}
	
	public void addPointFromTouch(Vector2D touchPosition)
	{
		if (state == PenState.Init)
		{
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
		int pointsNumber = theGetPoints.size();
		int ProcessedPointNumber = 0;
		
		Log.v("hehe process point", "" + pointsNumber);
		if (pointsNumber > 4) 
		{	
			Vector2D formerLine;//Lines!!!!
			Vector2D onLinePoint = new Vector2D();
			
			float tLength;
			
			for (; pointsNumber - ProcessedPointNumber > 4; ProcessedPointNumber += 4)
			{
				BezierLine2D.setBezierPoint(theGetPoints.get(ProcessedPointNumber), theGetPoints.get(ProcessedPointNumber + 1),
						theGetPoints.get(ProcessedPointNumber + 2), theGetPoints.get(ProcessedPointNumber + 3));
				
				tLength = INTERVAL / BezierLine2D.getLineLength();
				if (lineT == -1)
				{
					lineT = tLength;
					currentPoint.set(theGetPoints.get(0));
					onLinePoint.set(currentPoint);
					theDrawPoints.add(currentPoint.copy());
					Log.v("hehe process init", theDrawPoints.size() + "");
					Log.v("hehe process init", lineT + "");
				}
				while (lineT < 1)
				{
					onLinePoint.set(BezierLine2D.getPoint(lineT));
					theDrawPoints.add(onLinePoint.copy());
					Log.v("hehe process next", theDrawPoints.size() + "");
					formerLine = onLinePoint.copy().sub(currentPoint);
					theWidths.add(onLinePoint.add(formerLine.getVerticalLine(lineT * 0.5f / tLength, true)));
					theWidths.add(onLinePoint.add(formerLine.getVerticalLine(lineT * 0.5f / tLength, false)));
					currentPoint.set(onLinePoint);
					lineT += tLength;
					Log.v("hehe process while", lineT + "");
				}
				Log.v("hehe process for", lineT + "");
				lineT -= 1;
			}
			while (ProcessedPointNumber < pointsNumber)
			{
				theTempPoints.add(theGetPoints.get(ProcessedPointNumber));
				ProcessedPointNumber++;
			}
			theGetPoints.clear();
			theGetPoints.addAll(theTempPoints);
			theTempPoints.clear();
		}
	}
	
	private void PrepareForEnd()
	{
		Vector2D formerLine;//Lines!!!!		
		int pointsNumber = theGetPoints.size();
		
		if (theDrawPoints.size() == 0)
		{
			switch (pointsNumber)
			{
			case 1:
			{
				Vector2D point = theGetPoints.get(0).copy();
				theDrawPoints.add(point.copy());
				theWidths.add(point.copy().add(4,4));
				theWidths.add(point.copy().add(-4,4));
				theWidths.add(point.copy().add(4,-4));
				theWidths.add(point.copy().add(-4,-4));
				break;
			}
			case 2:
			{
				Vector2D point1 = theGetPoints.get(0).copy();
				Vector2D point3 = theGetPoints.get(1).copy();
				Vector2D point2 = point1.copy().add(point3).mul(0.5f);
				
				theDrawPoints.add(point1.copy());
				theDrawPoints.add(point2.copy());
				theDrawPoints.add(point3.copy());
				
				formerLine = point2.copy().sub(point2);
				theWidths.add(point2.add(formerLine.getVerticalLine(0.5f, true)));
				theWidths.add(point2.add(formerLine.getVerticalLine(0.5f, false)));
				break;
			}
			case 3:
			{
				Vector2D point1 = theGetPoints.get(0).copy();
				Vector2D point2 = theGetPoints.get(1).copy();
				Vector2D point3 = theGetPoints.get(2).copy();
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
			case 4: 
			{
				Vector2D point1 = theGetPoints.get(0).copy();
				Vector2D point2 = theGetPoints.get(1).copy();
				Vector2D point3 = theGetPoints.get(2).copy();
				Vector2D point4 = theGetPoints.get(3).copy();
				Vector2D onLinePoint = new Vector2D();
				currentPoint.set(point1);
				BezierLine2D.setBezierPoint(point1, point2, point3, point4);
				
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
				if (!currentPoint.equals(point4))
				{
					theDrawPoints.add(point4.copy());
				}
				break;
			}
			default:
			{
				break;//This will never be get in.
			}
			}
		}
		else 
		{
			Vector2D point1 = theGetPoints.get(0).copy();
			Vector2D point2 = theGetPoints.get(1).copy();
			Vector2D point3 = theGetPoints.get(2).copy();
			Vector2D point4 = theGetPoints.get(3).copy();
			Vector2D onLinePoint = new Vector2D();
			currentPoint.set(point1);
			BezierLine2D.setBezierPoint(point1, point2, point3, point4);
			
			theDrawPoints.add(point1.copy());
			
			float tLength = INTERVAL / BezierLine2D.getLineLength();
			lineT += tLength;
			
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
			if (!currentPoint.equals(point4))
			{
				theDrawPoints.add(point4.copy());
			}
		}
		theGetPoints.clear();
		lineT = -1;
	}
}
