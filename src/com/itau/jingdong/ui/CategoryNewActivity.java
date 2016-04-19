package com.itau.jingdong.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itau.jingdong.R;
import com.itau.jingdong.fragment.FragmentType;
import com.itau.jingdong.ui.base.BaseFragmentActivity;
import com.itau.jingdong.utils.Utils;
/**
 * 京东分类菜单new
 * @author xiangzhihong
 *
 */

public class CategoryNewActivity extends BaseFragmentActivity {
	private Context context;
	public String toolsList[]=new String[]{"常用分类","潮流女装","品牌男装","内衣配饰","家用电器","运动户外","营养保健","奢侈礼品","生活服务","旅游出行","手机数码","电脑办公","个护化妆","母婴频道","食物生鲜"};;
	private TextView toolsTextViews[];
	private View views[];
	private LayoutInflater inflater;
	private ScrollView scrollView;
	private int scrllViewWidth = 0, scrollViewMiddle = 0;
	private ViewPager shop_pager;
	private int currentItem=0;
	private ShopAdapter shopAdapter;
	private TextView textView;
	private View indicator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_layout);
		context=CategoryNewActivity.this;
		initView();
		showData();
		initPager();
	}




	protected void initView() {
		scrollView=(ScrollView) findViewById(R.id.tools_scrlllview);
		shopAdapter=new ShopAdapter(getSupportFragmentManager());
		inflater=LayoutInflater.from(this);
	}
	View view;
	private void showData() {
		LinearLayout toolsLayout=(LinearLayout) findViewById(R.id.tools);
		toolsTextViews=new TextView[toolsList.length];
		views=new View[toolsList.length];
		
		for (int i = 0; i < toolsList.length; i++) {
			view=inflater.inflate(R.layout.item_b_top_nav_layout, null);
			view.setId(i);
			view.setOnClickListener(toolsItemListener);
		    textView=(TextView) view.findViewById(R.id.text);
			textView.setText(toolsList[i]);
			toolsLayout.addView(view);
			toolsTextViews[i]=textView;
			views[i]=view;
		    indicator=view.findViewById(R.id.indicator_line);
		}
		changeTextColor(0);
		
	}
	
	private void initPager() {
		shop_pager=(ViewPager)findViewById(R.id.goods_pager);
		shop_pager.setAdapter(shopAdapter);		
		shop_pager.setOnPageChangeListener(onPageChangeListener);
	}
	
	private View.OnClickListener toolsItemListener =new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			shop_pager.setCurrentItem(v.getId());
		}
	};
	
	private void changeTextColor(int id) {
		for (int i = 0; i < toolsTextViews.length; i++) {
			if(i!=id){
			   toolsTextViews[i].setBackgroundResource(android.R.color.transparent);
			   toolsTextViews[i].setTextColor(0xff000000);
			}
		}
		toolsTextViews[id].setBackgroundResource(android.R.color.white);
		toolsTextViews[id].setTextColor(0xffff5d5e);
//		Drawable img = view.getResources().getDrawable(R.drawable.blue_vertical_line);
//		img.setBounds(Utils.dip2px(context, 8), 0, img.getMinimumWidth(), img.getMinimumHeight());
//		toolsTextViews[id].setCompoundDrawables(img, null, null, null); //设置左图标
	}
	
	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			if(shop_pager.getCurrentItem()!=arg0)shop_pager.setCurrentItem(arg0);
			if(currentItem!=arg0){
				changeTextColor(arg0);
				changeTextLocation(arg0);
			}
			currentItem=arg0;
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};
	
private void changeTextLocation(int clickPosition) {
		
		int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(views[clickPosition]) / 2));
		scrollView.smoothScrollTo(0, x);
	}
	
private int getScrollViewMiddle() {
	if (scrollViewMiddle == 0)
		scrollViewMiddle = getScrollViewheight() / 2;
	return scrollViewMiddle;
}
	private class ShopAdapter extends FragmentPagerAdapter {
		public ShopAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment =new FragmentType();
			Bundle bundle=new Bundle();
			String str=toolsList[arg0];
			bundle.putString("typename",str);
			fragment.setArguments(bundle);
			return fragment;
		}
		
		@Override
		public int getCount() {
			return toolsList.length;
		}
	}
	private int getScrollViewheight() {
		if (scrllViewWidth == 0)
			scrllViewWidth = scrollView.getBottom() - scrollView.getTop();
		return scrllViewWidth;
	}
	
	
	private int getViewheight(View view) {
		return view.getBottom() - view.getTop();
	}
	
}
