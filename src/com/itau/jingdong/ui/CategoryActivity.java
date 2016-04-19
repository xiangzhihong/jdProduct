package com.itau.jingdong.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itau.jingdong.R;
import com.itau.jingdong.adapter.CateListAdapter;
import com.itau.jingdong.ui.base.BaseActivity;


public class CategoryActivity extends BaseActivity implements OnItemClickListener, OnClickListener {

	private Context context;
	private ListView mCateListView;
	private ImageView mCateIndicatorImg;
	private ImageButton mImageBtn;//右侧的按钮
	private LayoutInflater layoutInflater;
	private CateListAdapter mCateListAdapter;
	private String[] mCategories;
	private int mFromY = 0;
	private LinearLayout secondelistviewLayout;
	private ListView catergory_seconde_listview;//菜单的二级分类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_category);
		context=CategoryActivity.this;
		initView();
		
		//左边导航的数据
		mCategories = getResources().getStringArray(R.array.cate_list);
		mCateListAdapter = new CateListAdapter(context, mCategories);
		mCateListView.setAdapter(mCateListAdapter);
		// 用于计算mCateIndicatorImg的高度
				int itemHeight = calculateListViewItemHeight();
				int w = View.MeasureSpec.makeMeasureSpec(0,
						View.MeasureSpec.UNSPECIFIED);
				int h = View.MeasureSpec.makeMeasureSpec(0,
						View.MeasureSpec.UNSPECIFIED);
				mCateIndicatorImg.measure(w, h);

				// 第一次进来设置indicator的位置
				doAnimation(itemHeight / 2 - mCateIndicatorImg.getMeasuredHeight());
		
		
	}


	@Override
	protected void initView() {
		mCateListView=(ListView) findViewById(R.id.catergory_listview);
		mCateIndicatorImg = (ImageView)findViewById(R.id.cate_indicator_img);
		secondelistviewLayout=(LinearLayout) findViewById(R.id.seconde_listview);
		catergory_seconde_listview=(ListView) findViewById(R.id.catergory_seconde_listview);
//		mImageBtn = (ImageButton)findViewById(R.id.image_btn);
		mCateListView.setOnItemClickListener(this);
		catergory_seconde_listview.setAdapter(new CatergorAdapter());
		catergory_seconde_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterview, View view, int parent,
					long id) {
				Toast.makeText(context, "点击了："+adapterview.getItemIdAtPosition(parent), 1).show();
				
			}
		});
		
//		mImageBtn.setOnClickListener(this);
	}
	
	
	private int calculateListViewItemHeight() {
		ListAdapter listAdapter = mCateListView.getAdapter();
		if (listAdapter == null) {
			return 0;
		}

		int totalHeight = 0;
		int count = listAdapter.getCount();
		for (int i = 0; i < count; i++) {
			View listItem = listAdapter.getView(i, null, mCateListView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		return totalHeight / count;
	}
	
	private void doAnimation(int toY) {
		int cateIndicatorY = mCateIndicatorImg.getTop()
				+ mCateIndicatorImg.getMeasuredHeight() / 2;
		TranslateAnimation animation = new TranslateAnimation(0, 0, mFromY
				- cateIndicatorY, toY - cateIndicatorY);
		animation.setInterpolator(new AccelerateDecelerateInterpolator());
		animation.setFillAfter(true);
		animation.setDuration(400);
		mCateIndicatorImg.startAnimation(animation);
		mFromY = toY;
	}
	
	
	/**
	 * 二级分类菜单
	 * @author zhihong
	 *
	 */
	private class CatergorAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImageIds.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressWarnings("null")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder=new ViewHolder();
			layoutInflater=LayoutInflater.from(CategoryActivity.this);
			
			//组装数据
			if(convertView==null){
				convertView=layoutInflater.inflate(R.layout.activity_category_item, null);
				holder.image=(ImageView) convertView.findViewById(R.id.catergory_image);
				holder.title=(TextView) convertView.findViewById(R.id.catergoryitem_title);
				holder.content=(TextView) convertView.findViewById(R.id.catergoryitem_content);
				//使用tag存储数据
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.image.setImageResource(mImageIds[position]);
			holder.title.setText(mTitleValues[position]);
			holder.content.setText(mContentValues[position]);
		//	holder.title.setText(array[position]);
			
			return convertView;
		
		}
		
		
		
	}
	
	
	// 适配显示的图片数组
				private Integer[] mImageIds = {R.drawable.catergory_appliance,R.drawable.catergory_book,R.drawable.catergory_cloth,R.drawable.catergory_deskbook,
						R.drawable.catergory_digtcamer,R.drawable.catergory_furnitrue,R.drawable.catergory_mobile,R.drawable.catergory_skincare
						 };
				//给照片添加文字显示(Title)
				private String[] mTitleValues = { "家电", "图书", "衣服", "笔记本", "数码",
						"家具", "手机", "护肤" };
				
				private String[] mContentValues={"家电/生活电器/厨房电器", "电子书/图书/小说","男装/女装/童装", "笔记本/笔记本配件/产品外设", "摄影摄像/数码配件", 
						"家具/灯具/生活用品", "手机通讯/运营商/手机配件", "面部护理/口腔护理/..."};
			

		 public static class ViewHolder {
				ImageView image;
				TextView title;
				TextView content;
			}

//点击左边的item时候调用本方法
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (null != mCateListAdapter) {
				mCateListAdapter.setSelectedPos(position);
			}
			int toY = view.getTop() + view.getHeight() / 2;
			doAnimation(toY);
			
		}

		@Override
		public void onClick(View v) {
			
		}
	
	
		
		

}
