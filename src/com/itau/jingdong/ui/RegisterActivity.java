package com.itau.jingdong.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.itau.jingdong.R;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.utils.CommonTools;

public class RegisterActivity extends BaseActivity {

	private EditText mobile;
	private String registerNum;
	private ImageButton checkBox;
	private Button access_password, register_mormal;
	private ImageView bar_back;
	private TextView bar_title;
	private CommonTools tools;
	private boolean flag = false;
	private Intent mIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		initView();
	}


	@Override
	protected void initView() {
		bar_back=(ImageView) this.findViewById(R.id.bar_back);
		bar_back.setOnClickListener(click);
		bar_title=(TextView) this.findViewById(R.id.bar_title);
		bar_title.setText("注册");
		mobile = (EditText) this.findViewById(R.id.edit_mobile);
		checkBox = (ImageButton) this.findViewById(R.id.checkBox);
		access_password = (Button) this.findViewById(R.id.access_password);
		register_mormal = (Button) this.findViewById(R.id.register_mormal);
		
		tools = new CommonTools();
		registerNum = mobile.getText().toString();
		// 判断是否是手机
		tools.isMobileNO(registerNum);
		if (flag == false) {
			DisPlay("您输入的手机号不合法");
		}

		checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (flag == false) {
					access_password.setTextColor(Color.BLACK);
					flag = true;
				}

				else {
					access_password.setTextColor(Color.WHITE);
				}

			}
		});
		
		/**
		 * 跳转到普通注册*/
		register_mormal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RegisterActivity.this, RegisterBormalActivity.class));
				
			}
		});

	}
	
	private OnClickListener click=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			finish();
			
		}
	};

}
