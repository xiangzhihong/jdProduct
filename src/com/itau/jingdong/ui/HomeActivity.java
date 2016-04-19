package com.itau.jingdong.ui;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.itau.jingdong.AppManager;
import com.itau.jingdong.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeActivity extends TabActivity {

	public static final String TAG = HomeActivity.class.getSimpleName();

	private RadioGroup mTabButtonGroup;
	private TabHost mTabHost;

	public static final String TAB_MAIN = "MAIN_ACTIVITY";
	public static final String TAB_SEARCH = "SEARCH_ACTIVITY";
	public static final String TAB_CATEGORY = "CATEGORY_ACTIVITY";
	public static final String TAB_CART = "CART_ACTIVITY";
	public static final String TAB_PERSONAL = "PERSONAL_ACTIVITY";
	private long mExitTime=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(this);
		setContentView(R.layout.activity_home);
		findViewById();
		initView();
	}

	private void findViewById() {
		mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
	}

	private void initView() {

		mTabHost = getTabHost();

		Intent i_main = new Intent(this, IndexActivity.class);
		Intent i_search = new Intent(this, SearchActivity.class);
		Intent i_category = new Intent(this, CategoryNewActivity.class);
		Intent i_cart = new Intent(this, CartActivity.class);
		Intent i_personal = new Intent(this, PersonalActivity.class);

		mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
				.setContent(i_main));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_SEARCH)
				.setIndicator(TAB_SEARCH).setContent(i_search));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CATEGORY)
				.setIndicator(TAB_CATEGORY).setContent(i_category));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CART).setIndicator(TAB_CART)
				.setContent(i_cart));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSONAL)
				.setIndicator(TAB_PERSONAL).setContent(i_personal));

		mTabHost.setCurrentTabByTag(TAB_MAIN);

		mTabButtonGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.home_tab_main:
							mTabHost.setCurrentTabByTag(TAB_MAIN);
							break;

						case R.id.home_tab_search:
							mTabHost.setCurrentTabByTag(TAB_SEARCH);
							break;

						case R.id.home_tab_category:
							mTabHost.setCurrentTabByTag(TAB_CATEGORY);
							break;

						case R.id.home_tab_cart:
							mTabHost.setCurrentTabByTag(TAB_CART);
							break;

						case R.id.home_tab_personal:
							mTabHost.setCurrentTabByTag(TAB_PERSONAL);
							break;

						default:
							break;
						}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_about:

			break;

		case R.id.menu_setting:

			break;

		case R.id.menu_history:

			break;

		case R.id.menu_feedback:

			break;

		case R.id.menu_exit:

			showAlertDialog("退出程序", "确定退出高仿京东商城？", "确定", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					AppManager.getInstance().AppExit(getApplicationContext());
					ImageLoader.getInstance().clearMemoryCache();
				}
			}, "取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});

			break;

		case R.id.menu_help:

			break;

		default:
			break;
		}
		return true;
	}

	/** 含有标题、内容、两个按钮的对话框 **/
	protected void showAlertDialog(String title, String message,
			String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		new AlertDialog.Builder(this).setTitle(title).setMessage(message)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					Toast.makeText(this, "再按一次退出高仿京东商城", Toast.LENGTH_SHORT)
							.show();
					mExitTime = System.currentTimeMillis();
				} 
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
