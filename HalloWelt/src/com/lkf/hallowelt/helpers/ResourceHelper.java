package com.lkf.hallowelt.helpers;

import java.io.File;

import android.os.Environment;

public class ResourceHelper
{
	//Application Resource
	public static String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator + "HalloWelt" + File.separator;;
	
	public static void applicationLoad()
	{
		externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator + "HalloWelt" + File.separator;
	}
}
