package com.itau.jingdong.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.itau.jingdong.R;
import com.itau.jingdong.adapter.CartItemAdapter;
import com.itau.jingdong.common.CartItemChangedListener;
import com.itau.jingdong.entity.CartProduct;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.utils.Constant;
/**
 * 购物车
 * @author xiangzhihong
 *
 */

public class CartActivity extends BaseActivity implements OnClickListener,CartItemChangedListener {

	private Button cart_login,cart_market;
	private Intent mIntent;
	private LinearLayout cart_no_login;
	private View cart_list_layout;
	private Map<Integer, CartProduct> unCheckedList = new HashMap<Integer, CartProduct>();
	private CartItemAdapter cartItemAdapter;
	private List<CartProduct> cProducts = new ArrayList<CartProduct>();
	private boolean checkMark = true;// true状态变化来自全选按钮，false状态变化来自列表状态变化
	private CheckBox allCheck;// 全部选中按钮
	private TextView allSum;// 总计
	private TextView allProductSum;// 商品金额
	private ImageView iv_buy_product;// 购买商品
	private ListView cartList = null;// 商品列表
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		initData();
		initView();
	}


	@Override
	protected void initView() {
		cart_login=(Button)this.findViewById(R.id.cart_login);
		cart_market=(Button)  this.findViewById(R.id.cart_market);
		cart_login.setOnClickListener(this);
		cart_market.setOnClickListener(this);
		cart_no_login=(LinearLayout) findViewById(R.id.cart_no_login);
		cart_list_layout=findViewById(R.id.cart_list_layout);
		allCheck = (CheckBox) findViewById(R.id.cb_cart_all_check);
		allSum = (TextView) findViewById(R.id.tv_cart_all_sum);
		allProductSum = (TextView) findViewById(
				R.id.tv_cart_product_sum);
		iv_buy_product = (ImageView) findViewById(R.id.iv_cart_buy);
		cartList = (ListView)findViewById(R.id.lv_cart_list);
		iv_buy_product.setOnClickListener(clickListener);
		
		initAllCheck();
	}

	//全选操作
	private void initAllCheck() {
		allCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
				if (checkMark) {
					for (CartProduct cProduct : cProducts) {
						cProduct.setCheck(isChecked);
					}
					if (isChecked) {
						unCheckedList.clear();
					} else {
						for (int i = 0; i < cProducts.size(); i++) {
							unCheckedList.put(i, cProducts.get(i));
						}
					}
					cartItemAdapter.notifyDataSetChanged();
				}				
			}
		});
		if (cartItemAdapter == null) {
			cartItemAdapter = new CartItemAdapter(this, cProducts);
			cartItemAdapter.setCartItemChangedListener(this);
		}
		cartList.setAdapter(cartItemAdapter);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cart_login:
			mIntent=new Intent(this, LoginActivity.class);
			startActivityForResult(mIntent,Constant.CARTCODE);
			
			break;
			
		case R.id.cart_market:
			startActivity(new Intent(this, GoodsDetailsActivity.class));
			break;

		default:
			break;
		}
		
	}
   
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==Constant.CARTCODE&&resultCode==Constant.RESULTCODE){
			cart_no_login.setVisibility(View.GONE);
			cart_list_layout.setVisibility(View.VISIBLE);
			initListView();
		}
	}


	private void initListView() {
         initData();		
	}


	private void initData() {
		for (int i = 0; i < 10; i++) {
			cProducts
					.add(new CartProduct(
							true,
							2,
							"586.00",
							"活动商品已购满100.00元，已减10.00元现金，祝您购物愉快！",
							"",
							"跑鞋",
							"精品男士休闲运动鞋,精品推荐，值得拥有",
							"",
							"41",
							"380.00",
							"",
							"",
							"http://imgtest-lx.meilishuo.net/pic/_o/bf/ac/4f6accadddd95324144477652c25_1200_1200.jpg",
							""));
			if (i==3||i==5) {
				cProducts.get(i).setRemind(null);
			}
		}
	}
	
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.iv_cart_buy:
				Intent intent = new Intent(CartActivity.this,OrderFilledActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}
	};

	@Override
	public void itemNumChanged(int position, int num) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void itemCheckChanged(int position, boolean isCheck) {
		if (isCheck) {
			if (unCheckedList.containsKey(position)) {
				unCheckedList.remove(cProducts.get(position));
				unCheckedList.keySet().remove(position);
			}
		} else {
			checkMark = false;
			allCheck.setChecked(false);
			unCheckedList.put(position, cProducts.get(position));
			checkMark = true;
		}
		if (unCheckedList.size() == 0) {
			checkMark = false;
			allCheck.setChecked(true);
			checkMark = true;
		}
		
	}
}
