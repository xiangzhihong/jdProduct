package com.itau.jingdong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.itau.jingdong.R;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.utils.Utils;

public class OrderFilledActivity extends BaseActivity {
	
	private ImageView bar_back;
	private TextView bar_title;
	private TextView order_name;
	private TextView order_mobile;
	private View choose_pay_method;
	
	
	
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.order_filled_layout);
			initView();
		}
	
	@Override
	protected void initView() {
		
		bar_back=(ImageView) findViewById(R.id.bar_back);
		bar_back.setVisibility(View.VISIBLE);
		bar_back.setOnClickListener(orderClick);
		bar_title=(TextView) findViewById(R.id.bar_title);
		bar_title.setText("确认订单");
		order_name=(TextView) findViewById(R.id.order_name);
		order_name.setText("张三丰");
		order_mobile=(TextView) findViewById(R.id.order_mobile);
		order_mobile.setText(Utils.hideMobileNum(order_mobile.getText().toString()));
		choose_pay_method=findViewById(R.id.choose_pay_method);
		choose_pay_method.setOnClickListener(orderClick);
		
	}

	
	
	private OnClickListener orderClick=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.bar_back:
				finish();
				break;

			case R.id.choose_pay_method:
				startActivity(new Intent(OrderFilledActivity.this, PayMethodActivity.class));
				break;	
			default:
				break;
			}
			
		}
	};
	
	
}
