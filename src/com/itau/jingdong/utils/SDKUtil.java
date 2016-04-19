package com.itau.jingdong.utils;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

public class SDKUtil {
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void setBackground(View view, Drawable drawable) {
		int left = view.getPaddingLeft();
		int right = view.getPaddingRight();
		int top = view.getPaddingTop();
		int bottom = view.getPaddingBottom();
		if (Build.VERSION.SDK_INT >= 16) {
			view.setBackground(drawable);
		} else {
			view.setBackgroundDrawable(drawable);
		}
		view.setPadding(left, top, right, bottom);
	}

	@SuppressWarnings("deprecation")
	public static int paramsParent() {
		if (Build.VERSION.SDK_INT >= 8) {
			return android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		} else {
			return android.view.ViewGroup.LayoutParams.FILL_PARENT;
		}
	}

}
