package com.itau.jingdong.common;

/**
 * 购物车商品状态改变监听器
 * @author Cool
 *
 */
public interface CartItemChangedListener {
	/**
	 * 商品数量改变
	 */
	public abstract void itemNumChanged(int position,int num);
	/**
	 * 商品选中状态
	 */
	public abstract void itemCheckChanged(int position,boolean isCheck);
}
