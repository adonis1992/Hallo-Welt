package com.lkf.hallowelt.controllers;

import java.io.IOException;
import java.util.List;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.Display;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;

import com.lkf.hallowelt.helpers.ResourceHelper;
import com.lkf.hallowelt.screens.camera.CameraView;
import com.lkf.lib.base.LKFScreen;

public class CameraController extends ModelController implements Callback
{
	//Base member
	private CameraView theCameraView;
	
	//Camera member
	private SurfaceView theCameraPreviewView;
	private Camera theCamera;
	private boolean isPreview = false;
	
	private Point theDisplaySize;
	private int theWidthRequest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		WindowManager wManager = (WindowManager)getSystemService(WINDOW_SERVICE);
		Display display = wManager.getDefaultDisplay();
		theDisplaySize = new Point();
		display.getSize(theDisplaySize);
		
		//Camera init
		theCameraPreviewView = new SurfaceView(this);
		theCameraPreviewView.getHolder().addCallback(this);;
		
		addContentView(theCameraPreviewView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addBaseView();
		
		getSurfaceView().getHolder().setFormat(PixelFormat.TRANSPARENT);
		getSurfaceView().setZOrderOnTop(true);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format,
			int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		// TODO Auto-generated method stub
		if (!isPreview)
		{
			theCamera = Camera.open();
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			{
				theCamera.setDisplayOrientation(90);
				theWidthRequest = theDisplaySize.y;
				Log.d("orentation", "portrait");
			}
			else
			{
				theWidthRequest = theDisplaySize.x;
				Log.d("orentation", "landcape");
			}
		}
		if (!isPreview && theCamera != null)
		{
			try
			{
				Parameters theParameters = theCamera.getParameters();
				List<Size> thePreviewSizes = theParameters.getSupportedPreviewSizes();
				Size thePreviewSize = getCurrentScreenSize(thePreviewSizes, theWidthRequest);
				theParameters.setPreviewSize(thePreviewSize.width, thePreviewSize.height);
				List<Size> thePictureSizes = theParameters.getSupportedPictureSizes();
				Size thePicturesSize = getCurrentScreenSize(thePictureSizes, theWidthRequest);
				theParameters.setPictureSize(thePicturesSize.width, thePicturesSize.height);
				List<int[]> theFpsList = theParameters.getSupportedPreviewFpsRange();
				for (int[] fps : theFpsList)
				{
					theParameters.setPreviewFpsRange(fps[Parameters.PREVIEW_FPS_MIN_INDEX],
							fps[Parameters.PREVIEW_FPS_MAX_INDEX]);
				}
				theCamera.setParameters(theParameters);
				theCamera.setPreviewDisplay(holder);
				theCamera.startPreview();
				theCamera.autoFocus(null);
			}
			catch (IOException ex)
			{
				// TODO: handle exception
				Log.d("CameraError", "Create error!");
				ex.printStackTrace();
			}
			isPreview = true;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// TODO Auto-generated method stub
		if (theCamera != null)
		{
			if (isPreview)
			{
				theCamera.stopPreview();
				isPreview = false;
			}
			theCamera.release();
			theCamera = null;
		}
	}

	@Override
	public LKFScreen getStartScreen()
	{
		// TODO Auto-generated method stub
		theCameraView = new CameraView(this, 360, 640, ResourceHelper.COORDINATE_HELPER.focalLenth);
		return theCameraView;
	}
	
	private Size getCurrentScreenSize(List<Size> sizeList, int widthRequest)
	{
		if (sizeList != null && sizeList.size() > 0)
		{
			int[] array = new int[sizeList.size()];
			int temp = 0;
			for (Size size : sizeList)
			{
				array[temp++] = Math.abs(size.width - widthRequest);
			}
			temp = 0;
			int index = 0;
			for (int i = 0; i < array.length; i++)
			{
				if (i == 0)
				{
					temp = array[i];
					index = 0;
				}
				else if (array[i] < temp)
				{
					index = i;
					temp = array[i];
				}
			}
			return sizeList.get(index);
		}
		return null;
	}
}
