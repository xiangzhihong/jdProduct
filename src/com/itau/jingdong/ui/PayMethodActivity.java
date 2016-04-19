package com.itau.jingdong.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itau.jingdong.R;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.utils.SDKUtil;
import com.itau.jingdong.utils.Utils;
import com.itau.jingdong.widgets.MyAlertDialog;
import com.itau.jingdong.widgets.MyAlertDialog.MyListener;
import com.itau.jingdong.widgets.OrderDetailPopWindows;
/**
 * 
 * @author xiangzhihong
 *  选择支付配送方式
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class PayMethodActivity extends BaseActivity {
	private static int CONTACT_CODE = 0x1001;
	private Context ct;
	private ImageView bar_back;
	private TextView bar_title;
	private EditText order_contact_name,order_contact_tel;
	private ImageView order_contact_image;
	private String contactName,contactMobile;
	private ImageView cunkuan_check;
	private TextView order_countdown;
	private int secondTime=30*60;//剩余支付时间
	private LinearLayout order_detail_pop_layout;//查看产品详情
	private OrderDetailPopWindows odp;
	private LinearLayout pay_order_bootom;
	private ImageView pay_method_image;
	private LinearLayout choose_pay_method_layout;
	private ImageView defaultCheck;
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			if (msg.obj.equals("sencond")) {
				order_countdown.setText(Utils.orderWaitTiex(msg.arg1));
				if (msg.arg1 < 0) {
					order_countdown.setText(Utils.orderWaitTiex(0));
					waitTiemDialog();
				}
			}
		};
	};
	
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			ct=PayMethodActivity.this;
			setContentView(R.layout.pay_method_layout);
			initView();
			responseOrderWaitPayTime(secondTime);
			payMethods();
		}
	
	

	@Override
	protected void initView() {
		bar_back=(ImageView) findViewById(R.id.bar_back);
		bar_back.setVisibility(View.VISIBLE);
		bar_back.setOnClickListener(orderClick);
		bar_title=(TextView) findViewById(R.id.bar_title);
		bar_title.setText("选择配送方式");
		order_contact_name=(EditText) findViewById(R.id.order_contact_name);
		order_contact_tel=(EditText) findViewById(R.id.order_contact_tel);
		order_contact_image=(ImageView) findViewById(R.id.order_contact_image);
		order_contact_image.setOnClickListener(contactListener);
		cunkuan_check=(ImageView) findViewById(R.id.cunkuan_check);
		cunkuan_check.setVisibility(View.GONE);
		order_countdown=(TextView) findViewById(R.id.order_countdown);
		order_detail_pop_layout=(LinearLayout) findViewById(R.id.order_detail_pop_layout);
		pay_order_bootom=(LinearLayout) findViewById(R.id.pay_order_bootom);
		order_detail_pop_layout.setOnClickListener(orderClick);
		
		pay_method_image=(ImageView) findViewById(R.id.pay_method_image);
		choose_pay_method_layout=(LinearLayout) findViewById(R.id.choose_pay_method_layout);
	}

	//选择支付方式
	private void payMethods() {
		choose_pay_method_layout.removeAllViews();
		LayoutInflater inflater = LayoutInflater.from(ct);
		initPayCheckLayout(inflater, R.drawable.weixin_pay_icon);
	}

	
	private void initPayCheckLayout(LayoutInflater inflater, 
			int drawable) {
		View layout = inflater.inflate(R.layout.pay_method_item, null);
		RelativeLayout payLayout = (RelativeLayout) layout
				.findViewById(R.id.payLayout);
		ImageView payIcon = (ImageView) layout.findViewById(R.id.payIcon);
		TextView payTitle = (TextView) layout.findViewById(R.id.payTitle);
		TextView payRecomment = (TextView) layout
				.findViewById(R.id.payRecomment);
		ImageView payCheck = (ImageView) layout.findViewById(R.id.payCheck);
		View line = layout.findViewById(R.id.line);
		SDKUtil.setBackground(payIcon, getResources()
				.getDrawable(drawable));
		payTitle.setText("支付宝客户端支付");
		
		choose_pay_method_layout.addView(layout);
	}
	
	int seconds=0;
	private void responseOrderWaitPayTime(int secondTime){
		seconds=secondTime;
		if (seconds < 24 * 60 * 60) {
			order_countdown.setText(Utils.orderWaitTiex(seconds));
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (seconds >= 0 &&order_countdown != null) {
						seconds--;
						try {
							Thread.sleep(1000);
							Message msg = new Message();
							msg.arg1 = seconds;
							msg.obj = "sencond";
							handler.sendMessage(msg);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		} else{
			order_countdown.setVisibility(View.GONE);
		}
	}
	
	private void waitTiemDialog() {
		MyAlertDialog dialog = new MyAlertDialog(
				PayMethodActivity.this, "亲,您已超过最晚可支付时间,请重新下单", new MyListener() {
					@Override
					public void onConfirm() {
//						payBackDetail();
					}
					@Override
					public void onCancel() {
					}
				});
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.getTitle().setText("支付超时");
		dialog.getBt_confirm().setText("我知道了");
		dialog.show();
	}
	
	private OnClickListener contactListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_PICK);
			intent.setData(ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, CONTACT_CODE);
		}
	};
	//支付点击事件；
	
	private OnClickListener orderClick=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.bar_back:
				finish();
				break;
			//查看账单详情	
			case R.id.order_detail_pop_layout:
				showDetail();
				break;
			default:
				break;
			}
			
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((requestCode == CONTACT_CODE) && resultCode == Activity.RESULT_OK) {
			if (data == null) {
				return;
			}
			Uri result = data.getData();
			String contactId = result.getLastPathSegment();
			contactName = Utils.getPhoneContacts(PayMethodActivity.this, contactId);
			contactMobile = Utils.getPhoneNumber(PayMethodActivity.this, contactId);
			order_contact_name.setText(contactName);
			order_contact_name.setSelection(contactName.length());
			order_contact_tel.setText(contactMobile);
			order_contact_tel.setSelection(contactMobile.length());
			
		}
	}

	protected void showDetail() {
		odp=new OrderDetailPopWindows(PayMethodActivity.this);
		odp.setBackgroundDrawable(new BitmapDrawable());
		pay_method_image.setBackgroundDrawable(getResources().getDrawable(R.drawable.show_more_down));
		int[] location = new int[2];  
		order_detail_pop_layout.getLocationOnScreen(location);  
		odp.showAtLocation(order_detail_pop_layout, Gravity.BOTTOM, 0, Utils.dip2px(ct, 48));
	};
	private Drawable getpopBg() {
		ShapeDrawable bgdrawable = new ShapeDrawable(new OvalShape());
		bgdrawable.getPaint().setColor(getResources().getColor(R.color.transparent));
		return bgdrawable;
	}
}
