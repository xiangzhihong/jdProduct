package com.itau.jingdong.widgets;

import com.baidu.mapapi.map.MapView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyWebMapView extends MapView {
	
	public MyWebMapView(Context arg0, AttributeSet arg1, int arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	public MyWebMapView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MyWebMapView(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	@Override    
    public boolean dispatchTouchEvent(MotionEvent ev) {   
        getParent().requestDisallowInterceptTouchEvent(true);  
        return super.dispatchTouchEvent(ev);    
    }  
}
