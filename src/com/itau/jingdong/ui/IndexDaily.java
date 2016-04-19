package com.itau.jingdong.ui;

import android.os.Bundle;
import android.view.Window;

import com.itau.jingdong.R;
import com.itau.jingdong.ui.base.BaseActivity;

public class IndexDaily extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index_daily);
	}


	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

}
