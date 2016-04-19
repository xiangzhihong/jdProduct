package com.itau.jingdong.widgets;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itau.jingdong.R;
import com.itau.jingdong.utils.S;
import com.itau.jingdong.utils.Utils;

public class MyLoadingDialog extends MyDialog {
	private Context context;
	private ImageView anim_img;
	private TextView content_tv;

	public MyLoadingDialog(Context context) {
		super(context, R.style.DT_DIALOG_Translucent);
		this.context = context;
		super.createView();
		super.setCancelable(true);
		super.setCanceledOnTouchOutside(true);
		setWindowBgAlpha(0);
		setWindowAlpha((float)0.5);
		int width = Utils.dip2px(context, 110);
		S.p("MyLoadingNowPageDialog:" + width);
		setWindowSize(width, width);
	}

	@Override
	protected View getView() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.loading_dialog_layout, null);
		anim_img = (ImageView) view.findViewById(R.id.anim_img);
		content_tv = (TextView) view.findViewById(R.id.content_tv);
		return view;
	}

	public void startAnimation() {
		S.p("MyLoadingNowPageDialog startAnimation:" + anim_img);
		AnimationDrawable anim = (AnimationDrawable) anim_img.getBackground();
		S.p("MyLoadingNowPageDialog startAnimation:" + anim);
		if (!anim.isRunning()) {
			anim.start();
		}
	}

	public void stopAnimation() {
		AnimationDrawable anim = (AnimationDrawable) anim_img.getBackground();
		if (anim.isRunning()) {
			anim.stop();
		}
	}

	public ImageView getAnim_img() {
		return anim_img;
	}

	public TextView getContent_tv() {
		return content_tv;
	}
}