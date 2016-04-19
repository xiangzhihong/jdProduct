package com.itau.jingdong.ui;

import net.tsz.afinal.FinalBitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.itau.jingdong.R;
import com.itau.jingdong.ui.base.BaseActivity;

public class GoodsPreviewActivity extends BaseActivity {
	private ImageView iv_pic = null;//商品图片
	private FinalBitmap fb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_preview);
		initView();
		
	}


	@Override
	protected void initView() {
		iv_pic = (ImageView) findViewById(R.id.iv_produce_pic_show);
		fb = FinalBitmap.create(getApplicationContext());
		fb.display(iv_pic,"http://pic16.nipic.com/20110903/3970232_185913684000_2.jpg");
		
	}

}
