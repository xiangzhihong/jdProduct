package com.itau.jingdong.widgets.guideview;

import android.view.View;

// 引导画布额外显示的视图
public class ExtraView {
	public ExtraView() {
	}

	public ExtraView(int inAnim, int outAnim, View v) {
		this.inAnim = inAnim;
		this.outAnim = outAnim;
		this.v = v;
	}

	public int inAnim;
	public int outAnim;
	public View v;

	public int getInAnim() {
		return inAnim;
	}

	public void setInAnim(int inAnim) {
		this.inAnim = inAnim;
	}

	public int getOutAnim() {
		return outAnim;
	}

	public void setOutAnim(int outAnim) {
		this.outAnim = outAnim;
	}

	public View getV() {
		return v;
	}

	public void setV(View v) {
		this.v = v;
	}
}
