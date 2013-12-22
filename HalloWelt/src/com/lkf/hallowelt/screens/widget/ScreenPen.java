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
	
	private float INTERVAL = 4f;
	
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
		Pens.get(finger.getID()).process(finger.getPosition());
	}
	
	public static void move(FingerHelper finger)
	{
		Pens.get(finger.getID()).process(finger.getPosition());
	}
	
	public static void endLine(FingerHelper finger)
	{
		Pens.get(finger.getID()).process(finger.getPosition());
//		Pens.get(finger.getID()).state = PenState.Dead;
		deleteIDs.add(finger.getID());
	}
	
	public static void killLine()
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
	}
	
	public static void draw(ColorBatcher theColorBatcher)
	{
		if (IDs.size() > 0)
		{
			for (Integer penID : IDs)
			{
				ScreenPen pen = Pens.get(penID);
				if (pen.thePoints.size() > 1)
				{
					theColorBatcher.draw(pen.thePoints, pen.theWidths, false);
				}
			}
		}
	}
	
	private ArrayList<Vector2D> theGetPoints;
	private ArrayList<Vector2D> theDrawPoints;
	private ArrayList<Vector2D> theWidths;
	private ArrayList<Vector2D> theTempPoints;
	
	private PenState state;
	
	private Vector2D currentPoint;
	
	private float lineT;
	
//	private Vector2D first;
//	private Vector2D second;
//	private Vector2D third;
//	private Vector2D forth;
	
	private ScreenPen()
	{
		theGetPoints = new ArrayList<Vector2D>();
		theDrawPoints = new ArrayList<Vector2D>();
		theWidths = new ArrayList<Vector2D>();
		theTempPoints = new ArrayList<Vector2D>();
		
		currentPoint = new Vector2D();
		init();
	}
	
	private void init()
	{
		lineT = -1;
//		first = null;
//		second = null;
//		third = null;
//		forth = null;
		currentPoint = null;
		
		theGetPoints.clear();
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
		if (currentPoint != null || touchPosition.copy().sub(currentPoint).length() > 2 * INTERVAL)
		{
			currentPoint.set(touchPosition);
			theGetPoints.add(new Vector2D(touchPosition));
		}
	}
	
	public void prepareForDraw()
	{	
		int pointsNumber = theGetPoints.size();
		int ProcessedPointNumber = 0;
		
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
		
/*		switch (pointsNumber)
		{
		case 1:
			
			break;

		default:
			break;
		}
		
		if (pointsNumber < 3 && pointsNumber > 0)
		{
			theDrawPoints.add(theGetPoints.get(0).copy());
			if (pointsNumber == 2)
			{
				theDrawPoints.add(theGetPoints.get(1).copy());
				lastPoint = new Vector2D(theGetPoints.get(1));
				formerLine = theGetPoints.get(0).copy().sub(lastPoint);
				theWidths.add(lastPoint.add(formerLine.getVerticalLine(0.5f, true)));
				theWidths.add(lastPoint.add(formerLine.getVerticalLine(0.5f, false)));
			}
		}
		else if (pointsNumber == 3)
		{
			lastPoint = new Vector2D(theGetPoints.get(0));
			onLinePoint = new Vector2D(lastPoint);
			theDrawPoints.add(lastPoint.copy());
			BezierLine2D.setBezierPoint(theGetPoints.get(0), theGetPoints.get(1), theGetPoints.get(2));
			
			float t = INTERVAL / BezierLine2D.getLineLength();
			float tLength = t;
			
			while (t < 1 - tLength)
			{
				onLinePoint.set(BezierLine2D.getPoint(t));
				theDrawPoints.add(onLinePoint.copy());
				formerLine = onLinePoint.copy().sub(lastPoint);
				theWidths.add(onLinePoint.add(formerLine.getVerticalLine(t * 0.5f / tLength, true)));
				theWidths.add(onLinePoint.add(formerLine.getVerticalLine(t * 0.5f / tLength, false)));
				lastPoint.set(onLinePoint);
				t += tLength;
			}
		}
	}
	
	private boolean addPoints(Vector2D touchPosition)
	{
		if (first == null)
		{
			first = new Vector2D(touchPosition);
			currentPoint = first;
			return true;
		}
		else if (second == null)
		{
			if (touchPosition.copy().sub(first).length() > INTERVAL)
			{
				second = new Vector2D(touchPosition);
				return true;
			}
			else 
			{
				return false;
			}
		}
		else if (third == null) 
		{
			if (touchPosition.copy().sub(second).length() > INTERVAL)
			{
				third = new Vector2D(touchPosition);
				return true;
			}
			else 
			{
				return false;
			}
		}
		else if (forth == null)
		{
			if (touchPosition.copy().sub(third).length() > INTERVAL)
			{
				forth = new Vector2D(touchPosition);
				return true;
			}
			else 
			{
				return false;
			}
		}
		else if (touchPosition.copy().sub(forth).length() > INTERVAL)
		{
			first.set(second);
			second.set(third);
			third.set(forth);
			forth.set(touchPosition);
			return true;
		}
		
		return false;
	}
	
	public void process(Vector2D touch)
	{
		Vector2D formerLine;
		
		float lineLength;
		
		if (addPoints(touch))
		{
			if (first != null && second !=null)
			{
				if (third == null)
				{
					lineLength = second.copy().sub(currentPoint).length();
					lineT = lineLength;
					
					while (lineT > INTERVAL)
					{
						lineT -= INTERVAL;
					}
					
					Vector2D cutLine = second.copy().sub(currentPoint).mul(lineLength - lineT / lineLength);
					onLinePoint = currentPoint.copy().add(cutLine);
					thePoints.add(currentPoint.copy());
					thePoints.add(onLinePoint.copy());
					
					formerLine = onLinePoint.copy().sub(currentPoint);
					theWidths.add(onLinePoint.add(formerLine.getVerticalLine(0.5f, true)));
					theWidths.add(onLinePoint.add(formerLine.getVerticalLine(0.5f, false)));
					
					currentPoint = onLinePoint;
				}
				else
				{
					if (forth != null)
					{
						BezierLine2D.setBezierPoint(first, second, third, forth);
						lineLength = third.copy().sub(second).length();
						
						float t = (INTERVAL - lineT) / lineLength;
						float tLength = INTERVAL / lineLength;
						
						lineT = lineLength + lineT - INTERVAL; 
						while (lineT > INTERVAL)
						{
							onLinePoint = new Vector2D(BezierLine2D.getPointX(t), BezierLine2D.getPointY(t));
							thePoints.add(onLinePoint.copy());
							
							formerLine = onLinePoint.copy().sub(currentPoint);
							theWidths.add(onLinePoint.add(formerLine.getVerticalLine(t / tLength, true)));
							theWidths.add(onLinePoint.add(formerLine.getVerticalLine(t / tLength, false)));
							
							currentPoint = onLinePoint;
							lineT -= INTERVAL;
							t = (lineLength - lineT) / lineLength;
						}
					}
				}
			}	
		}*/
	}
}
