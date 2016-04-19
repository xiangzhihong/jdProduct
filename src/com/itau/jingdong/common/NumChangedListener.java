package com.itau.jingdong.common;

/**
 * 购物车商品数量改变监听器
 * @author Cool
 *
 */
public interface NumChangedListener {
	/**
	 * 滑动结束
	 */
	public abstract void numchanged(int num);
}
