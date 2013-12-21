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
	
	private float INTERVAL = 10f;
	
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
	
	private ArrayList<Vector2D> thePoints;
	private ArrayList<Vector2D> theWidths;
	
	private PenState state;
	
	private Vector2D currentPoint;
	private Vector2D onLinePoint;
	
	private float lineT;
	
	private Vector2D first;
	private Vector2D second;
	private Vector2D third;
	private Vector2D forth;
	
	private ScreenPen()
	{
		thePoints = new ArrayList<Vector2D>();
		theWidths = new ArrayList<Vector2D>();
		init();
	}
	
	private void init()
	{
		lineT = 2;
		first = null;
		second = null;
		third = null;
		forth = null;
		thePoints.clear();
		theWidths.clear();
		
		state = PenState.Init;
	}
	
	public boolean getInit()
	{
		return (state == PenState.Init);
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
		}
	}
}
