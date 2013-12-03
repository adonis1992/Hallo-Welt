package com.lkf.hallowelt.controllers;

import java.io.IOException;
import java.util.List;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

import com.lkf.hallowelt.helpers.ResourceHelper;
import com.lkf.hallowelt.screens.camera.CameraView;
import com.lkf.lib.base.LKFScreen;

public class CameraController extends ModelController
{
	//Base member
	private CameraView theCameraView;
	
	//Camera member
	private TextureView theCameraPreviewView;
	private SurfaceTextureListener theCameraPreviewHolder;
	private Camera theCamera;
	private boolean isPreview = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Camera init
		theCameraPreviewHolder = new SurfaceTextureListener() 
		{	
			@Override
			public void onSurfaceTextureUpdated(SurfaceTexture surface)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height)
			{
				Log.v("hehe", "yes");
				// TODO Auto-generated method stub
				if (isPreview)
				{
					theCamera.stopPreview();
					isPreview = false;
				}
				if (theCamera != null)
				{
					Parameters theParameters = theCamera.getParameters();
					List<Size> thePreviewSizes = theParameters.getSupportedPreviewSizes();
					Size thePreviewSize = getCurrentScreenSize(thePreviewSizes, width);
					theParameters.setPreviewSize(thePreviewSize.width, thePreviewSize.height);
					List<Size> thePictureSizes = theParameters.getSupportedPictureSizes();
					Size thePicturesSize = getCurrentScreenSize(thePictureSizes, width);
					theParameters.setPictureSize(thePicturesSize.width, thePicturesSize.height);
					List<int[]> theFpsList = theParameters.getSupportedPreviewFpsRange();
					for (int[] fps : theFpsList)
					{
						theParameters.setPreviewFpsRange(fps[Parameters.PREVIEW_FPS_MIN_INDEX],
								fps[Parameters.PREVIEW_FPS_MAX_INDEX]);
					}
					theCamera.setParameters(theParameters);
					try
					{
						theCamera.setPreviewTexture(surface);
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					theCamera.startPreview();
					theCamera.autoFocus(null);
					isPreview = true;
				}
			}
			
			@Override
			public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
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
				return true;
			}
			
			@Override
			public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height)
			{
				// TODO Auto-generated method stub
				int scale = height;
				if (!isPreview)
				{
					theCamera = Camera.open();
					if (height > width)
					{
						theCamera.setDisplayOrientation(90);
						scale = width;
					}
				}
				if (theCamera != null)
				{
					Parameters theParameters = theCamera.getParameters();
					List<Size> thePreviewSizes = theParameters.getSupportedPreviewSizes();
					Size thePreviewSize = getCurrentScreenSize(thePreviewSizes, scale);
					theParameters.setPreviewSize(thePreviewSize.width, thePreviewSize.height);
					List<Size> thePictureSizes = theParameters.getSupportedPictureSizes();
					Size thePicturesSize = getCurrentScreenSize(thePictureSizes, scale);
					theParameters.setPictureSize(thePicturesSize.width, thePicturesSize.height);
					List<int[]> theFpsList = theParameters.getSupportedPreviewFpsRange();
					for (int[] fps : theFpsList)
					{
						theParameters.setPreviewFpsRange(fps[Parameters.PREVIEW_FPS_MIN_INDEX],
								fps[Parameters.PREVIEW_FPS_MAX_INDEX]);
					}
					theCamera.setParameters(theParameters);
					try
					{
						theCamera.setPreviewTexture(surface);
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					theCamera.startPreview();
					theCamera.autoFocus(null);
					isPreview = true;
				}
			}
		};
		theCameraPreviewView = new TextureView(this);
		theCameraPreviewView.setSurfaceTextureListener(theCameraPreviewHolder);
		
		addView(theCameraPreviewView);
		addBaseView();
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
