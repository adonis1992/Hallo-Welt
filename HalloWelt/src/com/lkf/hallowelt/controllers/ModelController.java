package com.lkf.hallowelt.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.lkf.hallowelt.helpers.ResourceHelper;
import com.lkf.lib.base.LKFController;
import com.lkf.lib.helpers.CoordinateHelper;

public abstract class ModelController extends LKFController
{
	static
	{
		File storage = new File(ResourceHelper.EXTERNAL_STORAGE_PATH);
		if (!storage.exists())
		{
			storage.mkdir();
			new File(ResourceHelper.EXTERNAL_STORAGE_PATH + File.separator + "test").mkdir();
		}
	}
	
	@Override
	protected InputStream readFile(String filePath) throws IOException
	{
		// TODO Auto-generated method stub
		return new FileInputStream(ResourceHelper.EXTERNAL_STORAGE_PATH + File.separator + filePath);
	}

	@Override
	protected OutputStream writeFile(String filePath) throws IOException
	{
		// TODO Auto-generated method stub
		return new FileOutputStream(ResourceHelper.EXTERNAL_STORAGE_PATH + File.separator + filePath);
	}
	
	public OutputStream write(String filePath) throws IOException
	{
		// TODO Auto-generated method stub
		return new FileOutputStream(ResourceHelper.EXTERNAL_STORAGE_PATH + File.separator+ filePath);
	}

	@Override
	public CoordinateHelper getCoordinateHelper()
	{
		return ResourceHelper.COORDINATE_HELPER;
	}
}
