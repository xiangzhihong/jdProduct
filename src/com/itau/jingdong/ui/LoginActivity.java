package com.itau.jingdong.ui;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.itau.jingdong.R;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.utils.Constant;

public class LoginActivity extends BaseActivity implements OnClickListener {
	
private static final String Tag="LoginActivity";
	private ImageView loginLogo,login_more;
	private EditText loginaccount,loginpassword;
	private ToggleButton isShowPassword;
	private boolean isDisplayflag=false;//是否显示密码
	private String getPassword,getUsername;
	private Button loginBtn,register;
	private Intent mIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}
	
	@Override
	protected void initView() {
		loginLogo=(ImageView)this.findViewById(R.id.logo);
		login_more=(ImageView)this.findViewById(R.id.login_more);
		loginaccount=(EditText)this.findViewById(R.id.loginaccount);
		loginpassword=(EditText)this.findViewById(R.id.loginpassword);
		
		isShowPassword=(ToggleButton)this.findViewById(R.id.isShowPassword);
		loginBtn=(Button)this.findViewById(R.id.login);
		loginBtn.setOnClickListener(this);
		register=(Button)this.findViewById(R.id.register);
		
		
		register.setOnClickListener(this);
		
		isShowPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				if(getpassword.equals("")||getpassword.length()<=0){
//					DisPlay("密码不能为空");
//				}
				
				if(isChecked){
					//隐藏
					loginpassword.setInputType(0x90);    
					//loginpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}else{
					//明文显示	
					loginpassword.setInputType(0x81); 
					//loginpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
				Log.i("togglebutton", ""+isChecked);
				//loginpassword.postInvalidate();
			}
		});
	
		
	
	}

	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.register:
		mIntent=new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(mIntent);
		break;

	case R.id.login:
		dologin();
		break;
	default:
		break;
	}
		
	}

	private void dologin() {
		getUsername=loginaccount.getText().toString().trim();
		getPassword=loginpassword.getText().toString().trim();
		if(TextUtils.isEmpty(getUsername)||TextUtils.isEmpty(getPassword)){
			Toast.makeText(this, "用户民或者密码不能为空", 1).show();
		}else{
			setResult(Constant.RESULTCODE);
			finish();
		}
		
	}

}
