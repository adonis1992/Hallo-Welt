package com.lkf.hallowelt.screens.widget;

import static com.lkf.lib.physics.BezierLine2D.getXParameters;
import static com.lkf.lib.physics.BezierLine2D.getYParameters;

import java.util.ArrayList;

import android.util.SparseArray;

import com.lkf.lib.helpers.FingerHelper;
import com.lkf.lib.physics.BezierLine2D;
import com.lkf.lib.physics.Vector2D;

public class ScreenPen
{	
	private static SparseArray<ScreenPen> thePens = new SparseArray<ScreenPen>();
	
	public static void initLine(FingerHelper finger)
	{
		thePens.put(finger.getID(), new ScreenPen());
	}
	
	public static void move(FingerHelper finger)
	{
//		thePens.get(finger.getID()).add
	}
	
	public static void endLine(FingerHelper finger)
	{
		
	}
	
	private ArrayList<Vector2D> thePoints;
	private float[] theParameters;
	
	private Vector2D first = null;
	private Vector2D second = null;
	private Vector2D third = null;
	private Vector2D forth = null;
	
	private ScreenPen()
	{
		thePoints = new ArrayList<Vector2D>();
		theParameters = new float[7];
	}
	
	private boolean addPoints(Vector2D touchPosition)
	{
		if (first == null)
		{
			first = new Vector2D(touchPosition);
			return true;
		}
		else if (second == null && touchPosition.copy().sub(first).length() > 1)
		{
			second = new Vector2D(touchPosition);
			return true;
		}
		else if (third == null && touchPosition.copy().sub(second).length() > 1) 
		{
			third = new Vector2D(touchPosition);
			return true;
		}
		else if (forth == null && touchPosition.copy().sub(third).length() > 1)
		{
			forth = new Vector2D(touchPosition);
			return true;
		}
		else if (touchPosition.copy().sub(forth).length() > 1)
		{
			first.set(second);
			second.set(third);
			third.set(forth);
			forth.set(touchPosition);
			return true;
		}
		
		return false;
	}
	
	private void getT(Vector2D circleCenter)
	{
		theParameters[0] = (getXParameters()[0] * getXParameters()[0]) + (getYParameters()[0] * getYParameters()[0]) - 4 - (2 * getXParameters()[0] * circleCenter.x) - (2 * getYParameters()[0] * circleCenter.y) + (circleCenter.x * circleCenter.x) + (circleCenter.y *circleCenter.y);
		theParameters[1] = (2 * getXParameters()[0] * getXParameters()[1]) + (2 * getYParameters()[0] *getYParameters()[1]) - (2 * getXParameters()[1] * circleCenter.x) - (2 * getYParameters()[1] * circleCenter.y);
		theParameters[2] = (getXParameters()[1] * getXParameters()[1]) + (getYParameters()[1] * getYParameters()[1]) + (2 * getXParameters()[0] * getXParameters()[2]) + (2 * getYParameters()[0] * getYParameters()[2]) - (2 * getXParameters()[2] * circleCenter.x) - (2 * getYParameters()[2] * circleCenter.y);
		theParameters[3] = (2 * getXParameters()[0] * getXParameters()[3]) + (2 * getYParameters()[0] * getYParameters()[3]) + (2 * getXParameters()[1] * getXParameters()[2]) + (2 * getYParameters()[1] * getYParameters()[2]) - (2 * getXParameters()[3] * circleCenter.x) - (2 * getYParameters()[3] * circleCenter.y);
		theParameters[4] = (getXParameters()[2] * getXParameters()[2]) + (getYParameters()[2] * getYParameters()[2]) + (2 * getXParameters()[1] * getXParameters()[3]) + (2 * getYParameters()[1] * getYParameters()[3]);
		theParameters[5] = (2 * getXParameters()[2] * getXParameters()[3]) + (2 * getYParameters()[2] * getYParameters()[3]);
		theParameters[6] = (getXParameters()[3] * getXParameters()[3]) + (getYParameters()[3] * getYParameters()[3]);
	}
	
	public void exec(Vector2D touch)
	{
		if (addPoints(touch))
		{
			BezierLine2D.setBezierPoint(first, second, third, forth);
		}
	}
}
