package com.itau.jingdong.widgets.guideview;

import android.graphics.Rect;
import android.view.View;

import java.util.List;

/**
 * 引导提示配置信息
 */
public class GuideItem {

	// 形状
	public enum Shape {
		Rectangle, Circle, CornerRectangle
	}

	// 相对于引导视图，提示的显示位置
	public enum TipDirection {
		Top, Bottom
	}

	// 提示控件屏幕显示位置
	public enum TipGravity {
		Left, Center, Right
	}

	

	// 引导图形状
	private Shape targetShape;
	// 目标view
	private View targetView;
	// 目标view的位置（也可通过百分比获得）
	private Rect targetRect;
	// 目标view的宽度
	private int targetWidth;
	// 目标View的高度
	private int targetHeight;
	// 相对于目标视图左部的偏移位置
	private int targetOffsetLeft;
	// 相对于目标视图顶部的偏移位置, 可为负值
	private int targetOffsetTop;
	// 引导提示
	private View tipView;
	// 引导提示到屏幕顶部的间距
	private int tipOffsetTop = 0;
	// 引导提示到屏幕左边的间距
	private int tipOffsetLeft = 0;
	// 引导提示的入场动画
	private int tipInAnim;
	// 引导提示的出场动画
	private int tipOutAnim;
	// 在引导视图的上面还是下面
	private TipDirection tipDirection;
	// 引导提示的默认屏幕显示位置
	private TipGravity tipGravity = TipGravity.Left;
	// 用于圆形半径的范围设定
	private int radiusScope = 10;
	// 圆半径
	private int radius;
	// 用于显示单个引导项扩展的视图
	private List<ExtraView> extraViews;

	public List<ExtraView> getExtraViews() {
		return extraViews;
	}

	public void setExtraViews(List<ExtraView> extraViews) {
		this.extraViews = extraViews;
	}

	public Shape getTargetShape() {
		return targetShape;
	}

	public void setTargetShape(Shape targetShape) {
		this.targetShape = targetShape;
	}

	public View getTargetView() {
		return targetView;
	}

	public void setTargetView(View targetView) {
		this.targetView = targetView;
	}

	public Rect getTargetRect() {
		return targetRect;
	}

	public void setTargetRect(Rect targetRect) {
		this.targetRect = targetRect;
	}

	public int getTargetWidth() {
		return targetWidth;
	}

	public void setTargetWidth(int targetWidth) {
		this.targetWidth = targetWidth;
	}

	public int getTargetHeight() {
		return targetHeight;
	}

	public void setTargetHeight(int targetHeight) {
		this.targetHeight = targetHeight;
	}

	public int getTargetOffsetLeft() {
		return targetOffsetLeft;
	}

	public void setTargetOffsetLeft(int targetOffsetLeft) {
		this.targetOffsetLeft = targetOffsetLeft;
	}

	public int getTargetOffsetTop() {
		return targetOffsetTop;
	}

	public void setTargetOffsetTop(int targetOffsetTop) {
		this.targetOffsetTop = targetOffsetTop;
	}

	public View getTipView() {
		return tipView;
	}

	public void setTipView(View tipView) {
		this.tipView = tipView;
	}

	public int getTipOffsetTop() {
		return tipOffsetTop;
	}

	public void setTipOffsetTop(int tipOffsetTop) {
		this.tipOffsetTop = tipOffsetTop;
	}

	public int getTipOffsetLeft() {
		return tipOffsetLeft;
	}

	public void setTipOffsetLeft(int tipOffsetLeft) {
		this.tipOffsetLeft = tipOffsetLeft;
	}

	public int getTipInAnim() {
		return tipInAnim;
	}

	public void setTipInAnim(int tipInAnim) {
		this.tipInAnim = tipInAnim;
	}

	public int getTipOutAnim() {
		return tipOutAnim;
	}

	public void setTipOutAnim(int tipOutAnim) {
		this.tipOutAnim = tipOutAnim;
	}

	public TipDirection getTipDirection() {
		return tipDirection;
	}

	public void setTipDirection(TipDirection tipDirection) {
		this.tipDirection = tipDirection;
	}

	public TipGravity getTipGravity() {
		return tipGravity;
	}

	public void setTipGravity(TipGravity tipGravity) {
		this.tipGravity = tipGravity;
	}

	public int getRadiusScope() {
		return radiusScope;
	}

	public void setRadiusScope(int radiusScope) {
		this.radiusScope = radiusScope;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}