package com.itau.jingdong.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.itau.jingdong.R;

/**
 * @description: 自定义对话框
 * @author: yangyang
 * @time: 2012-8-20下午6:07:39
 */
public abstract class MyDialog extends Dialog {
	private Window window;
	private LayoutParams layoutParams;

	/**
	 * 
	 * @param context
	 */
	protected MyDialog(Context context) {
		super(context, R.style.DT_DIALOG_THEME);
	}

	/**
	 * 
	 * @param context
	 * @param theme
	 */
	protected MyDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * 
	 */
	protected void createView() {
		super.setContentView(getView());
		window = getWindow();
		layoutParams = window.getAttributes();
		super.setCanceledOnTouchOutside(true);
		window.addFlags(LayoutParams.FLAG_DIM_BEHIND);
		layoutParams.alpha = 0.98765f;
		layoutParams.dimAmount = 0.56789f;
		window.setWindowAnimations(R.style.DT_DIALOG_ANIMATIONS);
	}

	/**
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public void setWindowPosition(int xPos, int yPos) {
		layoutParams.x = xPos;
		layoutParams.y = yPos;
	}

	/**
	 * 
	 * @param width
	 * @param height
	 */
	public void setWindowSize(int width, int height) {
		layoutParams.width = width;
		layoutParams.height = height;
	}

	/**
	 * 
	 * @return
	 */
	public int getWindowWidth() {
		return layoutParams.width;
	}

	/**
	 * 
	 * @return
	 */
	public int getWindowHeight() {
		return layoutParams.height;
	}

	/**
	 * 
	 * @param gravity
	 */
	public void setWindowGravity(int gravity) {
		window.setGravity(gravity);
	}

	/**
	 * 
	 * @param anims
	 */
	public void setWindowAnimations(int anims) {
		window.setWindowAnimations(anims);
	}

	/**
	 * 
	 * @param alpha
	 */
	public void setWindowAlpha(float alpha) {
		layoutParams.alpha = alpha;
	}

	/**
	 * 
	 * @param bgAlpha
	 */
	public void setWindowBgAlpha(float bgAlpha) {
		layoutParams.dimAmount = bgAlpha;
	}

	/**
	 * 
	 * @return
	 */
	protected abstract View getView();
}