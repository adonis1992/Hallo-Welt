package com.lkf.hallowelt.screens.widget;

import static com.lkf.lib.physics.BezierLine2D.getXParameters;
import static com.lkf.lib.physics.BezierLine2D.getYParameters;

import java.util.ArrayList;

import android.util.Log;
import android.util.SparseArray;

import com.lkf.lib.base.Pool;
import com.lkf.lib.base.Pool.PoolFactory;
import com.lkf.lib.helpers.FingerHelper;
import com.lkf.lib.physics.BezierLine2D;
import com.lkf.lib.physics.LKFMath;
import com.lkf.lib.physics.Vector2D;

public class ScreenPen
{	
	private static enum PenState
	{
		Init, Normal, ReBuild, Dead
	}
	
	private static Pool<ScreenPen> thePenPool;
	private static SparseArray<ScreenPen> thePens = new SparseArray<ScreenPen>();
	
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
		
		thePenPool = new Pool<ScreenPen>(factory, 20);
	}
	
	public static void initLine(FingerHelper finger)
	{
		thePens.put(finger.getID(), thePenPool.newObject());
		thePens.get(finger.getID()).process(finger.getPosition());
	}
	
	public static void move(FingerHelper finger)
	{
		thePens.get(finger.getID()).process(finger.getPosition());
	}
	
	public static void endLine(FingerHelper finger)
	{
		thePens.get(finger.getID()).process(finger.getPosition());
		thePens.get(finger.getID()).state = PenState.Dead;
	}
	
	public static void killLine(FingerHelper finger)
	{
		thePenPool.free(thePens.get(finger.getID()));
		thePens.remove(finger.getID());
	}
	
	private ArrayList<Vector2D> thePoints;
//	private float[] theParameters;
	
	public PenState state;
	
	private Vector2D currentPoint;
	private Vector2D onLinePoint;
	
	private float lineT;
	
	private Vector2D first = null;
	private Vector2D second = null;
	private Vector2D third = null;
	private Vector2D forth = null;
	
	private ScreenPen()
	{
		thePoints = new ArrayList<Vector2D>();
		lineT = 1;
		
		state = PenState.Normal;
		
//		theParameters = new float[7];
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
			if (touchPosition.copy().sub(first).length() > 1)
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
			if (touchPosition.copy().sub(second).length() > 1)
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
			if (touchPosition.copy().sub(third).length() > 1)
			{
				forth = new Vector2D(touchPosition);
				return true;
			}
			else 
			{
				return false;
			}
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
	
	//我很没志气地放弃治疗了 T_T
/*	private float getT(Vector2D circleCenter) 
	{
		theParameters[0] = (getXParameters()[0] * getXParameters()[0]) + (getYParameters()[0] * getYParameters()[0]) - 4 - (2 * getXParameters()[0] * circleCenter.x) - (2 * getYParameters()[0] * circleCenter.y) + (circleCenter.x * circleCenter.x) + (circleCenter.y *circleCenter.y);
		theParameters[1] = (2 * getXParameters()[0] * getXParameters()[1]) + (2 * getYParameters()[0] *getYParameters()[1]) - (2 * getXParameters()[1] * circleCenter.x) - (2 * getYParameters()[1] * circleCenter.y);
		theParameters[2] = (getXParameters()[1] * getXParameters()[1]) + (getYParameters()[1] * getYParameters()[1]) + (2 * getXParameters()[0] * getXParameters()[2]) + (2 * getYParameters()[0] * getYParameters()[2]) - (2 * getXParameters()[2] * circleCenter.x) - (2 * getYParameters()[2] * circleCenter.y);
		theParameters[3] = (2 * getXParameters()[0] * getXParameters()[3]) + (2 * getYParameters()[0] * getYParameters()[3]) + (2 * getXParameters()[1] * getXParameters()[2]) + (2 * getYParameters()[1] * getYParameters()[2]) - (2 * getXParameters()[3] * circleCenter.x) - (2 * getYParameters()[3] * circleCenter.y);
		theParameters[4] = (getXParameters()[2] * getXParameters()[2]) + (getYParameters()[2] * getYParameters()[2]) + (2 * getXParameters()[1] * getXParameters()[3]) + (2 * getYParameters()[1] * getYParameters()[3]);
		theParameters[5] = (2 * getXParameters()[2] * getXParameters()[3]) + (2 * getYParameters()[2] * getYParameters()[3]);
		theParameters[6] = (getXParameters()[3] * getXParameters()[3]) + (getYParameters()[3] * getYParameters()[3]);
		return LKFMath.getXInSix(theParameters);
	}*/
	
	public void process(Vector2D touch)
	{
		if (addPoints(touch))
		{
			if (first != null && second !=null)
			{
				if (third == null)
				{
					float lineLength = second.copy().sub(currentPoint).length();
					
					while (currentPoint.copy().sub(second).length() > 2)
					{
						onLinePoint = new Vector2D(second.copy().sub(currentPoint).mul((float) 2 / lineLength).add(currentPoint));
						lineT = 
						thePoints.add(onLinePoint);
						currentPoint = onLinePoint;
					}
				}
				else
				{
					if (forth == null)
					{
						BezierLine2D.setBezierPoint(first, second, second, third);
					}
					else
					{
						BezierLine2D.setBezierPoint(first, second, third, forth);
					}
					
					float t = 0;
					
					while (currentPoint.copy().sub(third).length() > 2 )
					{
//						t = getT(currentPoint);
						onLinePoint = new Vector2D(BezierLine2D.getPointX(t), BezierLine2D.getPointY(t));
						thePoints.add(onLinePoint);
						Log.v("hehe", t+"");
						Log.v("hehe", currentPoint.x + " " + currentPoint.y);
						currentPoint = onLinePoint;
						try
						{
							Thread.sleep(1000);
						} catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}	
		}
	}
}
