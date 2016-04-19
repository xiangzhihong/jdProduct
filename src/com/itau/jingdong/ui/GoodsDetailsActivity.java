package com.itau.jingdong.ui;

import net.tsz.afinal.FinalBitmap;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itau.jingdong.R;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.widgets.MyLoadingDialog;
import com.itau.jingdong.widgets.NumEditText;

public class GoodsDetailsActivity extends BaseActivity {
	private TextView titleText = null;//页面标题
	private TextView tv_price = null;//单价
	private TextView tv_bagweight = null;//鞋码
	private ImageView iv_pic = null;//商品图片
	private TextView tv_category = null;//商品类别
	private TextView tv_number= null;//商品编码
	private TextView tv_name= null;//商品名称
	private TextView tv_model = null;//货物型号
	private TextView tv_info = null;//货物信息
	private NumEditText net_count = null;//购买数量
	private RatingBar rb_value = null;//商品评价分数
	private TextView tv_focus = null;//商品评价人数
	private LinearLayout ll_buy_evaluation = null;//晒单讨论
	private TextView tv_buy_evaluation_num;//晒单讨论数量
	private LinearLayout ll_consultation = null;//购买咨询
	private TextView tv_consultation_num;//购买咨询数量
	private ImageView iv_buy= null;//立即购买
	private ImageView iv_add_cart = null;//添加购物车
	private FinalBitmap fb;
	private String[] pagweights = {"38", "39", "40", "41", "42", "43"};
	private int bagSelected = 0;
	private MyLoadingDialog loadingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_details);
		initView();
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		titleText = (TextView) findViewById(R.id.bar_title);
		titleText.setText("商品详情");
		
		iv_pic = (ImageView) findViewById(R.id.iv_product_pic);
		tv_price = (TextView) findViewById(R.id.tv_product_price);
		tv_bagweight = (TextView) findViewById(R.id.tv_product_bagweight);
		tv_category = (TextView) findViewById(R.id.tv_product_category);
		tv_number = (TextView) findViewById(R.id.tv_product_fnumber);
		tv_name = (TextView) findViewById(R.id.tv_product_name);
		tv_model = (TextView) findViewById(R.id.tv_product_model);
		tv_info = (TextView) findViewById(R.id.tv_product_details);
		net_count = (NumEditText) findViewById(R.id.net_product_count);
		rb_value = (RatingBar) findViewById(R.id.rb_product_value);
		tv_focus = (TextView) findViewById(R.id.tv_product_value);
		ll_buy_evaluation = (LinearLayout) findViewById(R.id.ll_product_buy_evaluation);
		tv_buy_evaluation_num = (TextView) findViewById(R.id.tv_buy_evaluation_count);
		ll_consultation = (LinearLayout) findViewById(R.id.ll_product_consultation);
		tv_consultation_num = (TextView) findViewById(R.id.tv_consultation_count);
		iv_buy = (ImageView) findViewById(R.id.iv_product_buy);
		iv_add_cart = (ImageView) findViewById(R.id.iv_product_add_cart);
		
		iv_pic.setOnClickListener(clickListener);
		tv_bagweight.setOnClickListener(clickListener);
		ll_buy_evaluation.setOnClickListener(clickListener);
		ll_consultation.setOnClickListener(clickListener);
		iv_buy.setOnClickListener(clickListener);
		iv_add_cart.setOnClickListener(clickListener);
		
		tv_bagweight.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		fb = FinalBitmap.create(getApplicationContext());
		iv_pic.setScaleType(ScaleType.CENTER_CROP);
		fb.display(iv_pic,"http://pic16.nipic.com/20110903/3970232_185913684000_2.jpg");
		showProgressDialog();
	}
	
	Intent intent;
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.iv_product_pic:
				 intent = new Intent(GoodsDetailsActivity.this, GoodsPreviewActivity.class);
				startActivity(intent);
				break;
			case R.id.tv_product_bagweight:
				new AlertDialog.Builder(GoodsDetailsActivity.this)
					.setTitle("选择鞋码")   
					.setSingleChoiceItems(pagweights, bagSelected, new DialogInterface.OnClickListener() {   
						public void onClick(DialogInterface dialog, int item) {  
						tv_bagweight.setText(pagweights[item]);
						bagSelected = item;
					 	dialog.cancel();   
				  }   
				}).show();
				break;
			case R.id.ll_product_buy_evaluation:
				Toast.makeText(getApplicationContext(), "晒单讨论", Toast.LENGTH_SHORT).show();
				break;
			case R.id.ll_product_consultation:
				Toast.makeText(getApplicationContext(), "购买咨询", Toast.LENGTH_SHORT).show();
				break;
			case R.id.iv_product_buy:
				 intent = new Intent(GoodsDetailsActivity.this, OrderFilledActivity.class);
				startActivity(intent);
				break;
			case R.id.iv_product_add_cart:
				Toast.makeText(getApplicationContext(), "添加购物车", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};




}
