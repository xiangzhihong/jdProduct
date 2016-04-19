package com.itau.jingdong;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.itau.jingdong.config.Constants;
import com.itau.jingdong.image.ImageLoaderConfig;


public class BaseApplication extends Application {

	private static Context mContext;
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext=getApplicationContext();
		ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public static Context getInstance() {
		return mContext;
	}
}
