package com.lkf.hallowelt.controllers;

import java.io.IOException;
import java.util.List;

import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.ViewGroup.LayoutParams;

import com.lkf.hallowelt.helpers.ResourceHelper;
import com.lkf.hallowelt.screens.camera.CameraView;
import com.lkf.lib.base.LKFScreen;

public class CameraController extends ModelController implements SurfaceTextureListener
{
	//Base member
	private CameraView theCameraView;
	
	//Camera member
	private TextureView theCameraPreviewView;
	private Camera theCamera;
//	private boolean isPreview = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Camera init
		theCameraPreviewView = new TextureView(this);
		theCameraPreviewView.setSurfaceTextureListener(this);
//		theCameraPreviewView.setAlpha(0.5f);
		
		addContentView(theCameraPreviewView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		getSurfaceView().getHolder().setFormat(PixelFormat.TRANSPARENT);
//		setEGLContextFactory( new ContextFactory() );
		getSurfaceView().setZOrderOnTop(true);
	}
	
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) 
	{
		theCamera = Camera.open();

        try 
        {
        	theCamera.setPreviewTexture(surface);
        	theCamera.startPreview();
        } 
        catch (IOException ioe) 
        {
            // Something bad happened
        	throw new RuntimeException("wocao");
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) 
    {
        // Ignored, Camera does all the work for us
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) 
    {
    	Log.v("hehe", "yes");
    	theCamera.stopPreview();
    	theCamera.release();
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) 
    {
        // Invoked every time there's a new Camera preview frame
    	Log.v("hehe", "yes");
    }

	@Override
	public LKFScreen getStartScreen()
	{
		// TODO Auto-generated method stub
		theCameraView = new CameraView(this, 360, 640, ResourceHelper.COORDINATE_HELPER.focalLenth);
		return theCameraView;
	}
}
