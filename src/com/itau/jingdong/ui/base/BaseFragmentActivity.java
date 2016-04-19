package com.itau.jingdong.ui.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BaseFragmentActivity extends FragmentActivity {
	private int BACK=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	
	@Override
	public void onBackPressed() {
		
	}
	
}
