package com.lkf.hallowelt.controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.lkf.hallowelt.helpers.ResourceHelper;
import com.lkf.lib.base.LKFController;

public abstract class ModelController extends LKFController
{
	@Override
	protected InputStream readFile(String filePath) throws IOException
	{
		// TODO Auto-generated method stub
		return new FileInputStream(ResourceHelper.externalStoragePath + filePath);
	}

	@Override
	protected OutputStream writeFile(String filePath) throws IOException
	{
		// TODO Auto-generated method stub
		return new FileOutputStream(ResourceHelper.externalStoragePath + filePath);
	}

}
