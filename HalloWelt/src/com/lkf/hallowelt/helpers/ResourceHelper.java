package com.lkf.hallowelt.helpers;

import java.io.File;

import com.lkf.lib.helpers.CoordinateHelper;

import android.os.Environment;

public class ResourceHelper
{
	//Application Resource
	public static final String EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator + "HalloWelt";
	public static final CoordinateHelper COORDINATE_HELPER = CoordinateHelper.getHelper(360, 640);
}
