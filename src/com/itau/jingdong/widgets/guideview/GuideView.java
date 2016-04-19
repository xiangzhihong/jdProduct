package com.itau.jingdong.widgets.guideview;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

/**
 * 引导试图组合控件 <br>
 * 引导、动画 <br>
 * <br>
 * <p/>
 * 也可按百分比来处理：设计图的宽高为width, height,然后view1的位置为x1,y1,在真实手机上，手机屏幕宽为m_w,手机屏幕高为m_h，
 * 以m_w/width *x1得到x, m_h/height*y1得到y
 *
 * @author xujian
 */
public class GuideView extends View {
    private Canvas canvas;
    private Context context;
    private String maskColor = "#95000000";
    private Point point;
    private boolean showOneByOne;
    private int currentIndex = 0;
    private int drawBorder = 3;
    private boolean canDraw; // 避免默认视图调用ondraw，如在数据加载完毕再创建引导
    private List<GuideItem> guideItems;

    public GuideView(Context context) {
        this(context, null);
    }

    public GuideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        guideItems = new ArrayList<GuideItem>();
        this.context = context;
    }

    public void addExtraView(GuideItem gi, ExtraView ev) {
        if (gi == null || ev == null)
            return;
        List<ExtraView> views = gi.getExtraViews();
        if (views == null)
            views = new ArrayList<ExtraView>();
        if (views.contains(ev))
            views.remove(ev);
        views.add(ev);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canDraw == false)
            return;

        this.canvas = canvas;
        if (guideItems.size() == 0)
            return;
        if (!showOneByOne) { // 一次性刷新显示
            int viewCount = guideItems.size();
            for (int i = 0; i < viewCount; i++) {
                drawCanvas(guideItems.get(i));
            }
        } else { // 逐渐显示出来
            if (currentIndex >= guideItems.size())
                return;
            drawCanvas(guideItems.get(currentIndex));
        }
    }

    /**
     * 向画布上画引导内容
     *
     * @param gi
     */
    private void drawCanvas(GuideItem gi) {
        canvas.save();
        Bitmap tempBitmap = Bitmap.createBitmap(point.x, point.y,
                Config.ARGB_4444);
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawColor(Color.parseColor(maskColor));

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        // paint.setDither(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        switch (gi.getTargetShape()) {
            case Rectangle:
                tempCanvas.drawRect(gi.getTargetRect(), paint);
                // tempCanvas.drawRect(new Rect(150, 10, 200, 60), paint);
                break;
            case CornerRectangle:
                RectF rf = new RectF(gi.getTargetRect());
                tempCanvas.drawRoundRect(rf, 7.5f, 7.5f, paint);
                break;
            case Circle:
                int cx = gi.getTargetWidth() / 2 + gi.getTargetRect().left
                        + gi.getTargetOffsetLeft();
                int cy = gi.getTargetHeight() / 2 + gi.getTargetRect().top
                        + gi.getTargetOffsetTop();
                int radius = (int) Math.sqrt(Math.pow(gi.getTargetWidth(), 2)
                        + Math.pow(gi.getTargetHeight(), 2))
                        / 2 - gi.getRadiusScope();
                gi.setRadius(radius);

                tempCanvas.drawCircle(cx, cy, radius, paint);
                break;
            default:
                break;
        }

        canvas.drawBitmap(tempBitmap, 0, 0, null);
        canvas.restore();

        setPositionAnim(gi);
    }

    /**
     * 定位并播放提示动画
     *
     * @param gi
     */
    private void setPositionAnim(GuideItem gi) {
        View tipView = gi.getTipView();

        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        switch (gi.getTargetShape()) {
            case Rectangle:
            case CornerRectangle:
                if (gi.getTipDirection() == GuideItem.TipDirection.Top) {
                    top = gi.getTargetRect().top - gi.getTargetHeight() / 2
                            + gi.getTipOffsetTop();
                    bottom = gi.getTargetRect().bottom - gi.getTargetHeight();
                } else {
                    top = gi.getTargetRect().top + gi.getTargetHeight()
                            + gi.getTipOffsetTop();
                    bottom = gi.getTargetRect().bottom + gi.getTargetHeight() / 2;
                }
                break;
            case Circle:
                if (gi.getTipDirection() == GuideItem.TipDirection.Top) {
                    top = gi.getTargetRect().top - gi.getTargetHeight()
                            + gi.getTipOffsetTop();
                    bottom = gi.getTargetRect().bottom - gi.getTargetHeight();
                } else {
                    top = gi.getTargetRect().top + gi.getTargetHeight()
                            + gi.getTipOffsetTop();
                    bottom = gi.getTargetRect().bottom + gi.getTargetHeight() / 2;
                }
                break;
        }

        top += drawBorder * 2;

        // 显示位置处理
        Rect rect = null;
        switch (gi.getTipGravity()) {
            case Center:
                rect = getCenterRect(gi.getTipView());
                left = rect.left + gi.getTipOffsetLeft();
                right = rect.right;
                break;
            case Right:
                rect = getRightRect(gi.getTipView());
                left = rect.left + gi.getTipOffsetLeft();
                right = rect.right;
                break;
            case Left:
                left += gi.getTipOffsetLeft();
                break;
        }
        // tipView.layout(left, top, right, bottom);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = left;
        lp.topMargin = top;
        tipView.setLayoutParams(lp);

        // 出场动画
        tipView.setVisibility(View.VISIBLE);
        Animation outAnim = AnimationUtils.loadAnimation(context,
                gi.getTipInAnim());
        tipView.startAnimation(outAnim);

        showExtraView(gi, true);
    }

    // 显示或隐藏扩展的视图
    private void showExtraView(GuideItem gi, boolean show) {
        if (gi == null || gi.getExtraViews() == null)
            return;
        List<ExtraView> views = gi.getExtraViews();
        for (final ExtraView v : views) {
            if (show) {
                v.getV().setVisibility(View.VISIBLE);
                if (v.getInAnim() != 0) {
                    Animation outAnim = AnimationUtils.loadAnimation(context,
                            v.getInAnim());
                    v.getV().startAnimation(outAnim);
                }
            } else {
                if (v.getOutAnim() != 0) {
                    Animation outAnim = AnimationUtils.loadAnimation(context,
                            v.getOutAnim());
                    v.getV().startAnimation(outAnim);
                }
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.getV().setVisibility(View.GONE);
                    }
                }, 500);
            }
        }
    }

    // 引导提示居中对齐时的位置
    private Rect getCenterRect(View v) {
        if (point == null)
            return null;
        int left = point.x / 2 - v.getWidth() / 2;
        int top = point.y / 2 - v.getHeight() / 2;
        int right = point.x / 2 + v.getWidth() / 2;
        int bottom = point.y / 2 + v.getHeight() / 2;
        return new Rect(left, top, right, bottom);
    }

    // 引导提示居右对齐时的位置
    private Rect getRightRect(View v) {
        if (point == null)
            return null;
        int left = point.x / 2;
        int right = point.x / 2 + v.getWidth();
        return new Rect(left, 0, right, 0);
    }

    private Rect getLeftRect(View v) {
        if (point == null)
            return null;
        int left = 0;
        int right = point.x - v.getWidth();
        return new Rect(left, 0, right, 0);
    }

    /**
     * 初始化
     *
     * @param context
     * @param showOneByOne 是否逐一显示动画
     */
    public void init(Context context, boolean showOneByOne) {
        Activity act = (Activity) context;
        if (act != null) {
            DisplayMetrics dm = new DisplayMetrics();
            Display dp = act.getWindowManager().getDefaultDisplay();
            dp.getMetrics(dm);

            point = new Point(dm.widthPixels, dm.heightPixels);
        }
        this.showOneByOne = showOneByOne;
        this.reset();
    }

    // 获取状态栏高度
    private int getStatusBarHeight(Context context) {
        Activity act = (Activity) context;
        Rect rect = new Rect();
        Window win = act.getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    // 获得视图在UI上的绝对位置
    private Rect getViewScreenPosition(Context context, GuideItem gi) {
        View v = gi.getTargetView();
        int statusBarHeight = getStatusBarHeight(context);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        Rect rect = new Rect();
        rect.left = x;
        rect.top = y - statusBarHeight;
        // rect.right = point.x - (x + v.getWidth());
        // rect.bottom = point.y - (y + v.getHeight());
        rect.right = x + v.getWidth();
        rect.bottom = rect.top + v.getHeight();

        // 在实际尺寸下增加白色轮廓
        if (rect.left - drawBorder > 0)
            rect.left = rect.left - drawBorder;
        if (rect.top - drawBorder > 0)
            rect.top = rect.top - drawBorder;
        rect.right = rect.right + drawBorder;
        rect.bottom = rect.bottom + drawBorder;

        return rect;
    }

    public void reset() {
        currentIndex = 0;
        hideTipView();
        guideItems.clear();
        setVisibility(View.GONE);
    }

    // 隐藏当前屏幕的引导视图
    private void hideTipView() {
        if (guideItems == null || guideItems.size() == 0)
            return;
        for (GuideItem gi : guideItems) {
            if (gi.getTipView() != null) {
                gi.getTipView().setVisibility(View.INVISIBLE);
            }
            if (gi.getExtraViews() != null) {
                for (ExtraView ev : gi.getExtraViews()) {
                    if (ev != null && ev.getV() != null) {
                        ev.getV().setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

    /**
     * 注册guide指示对象
     *
     * @param targetView
     */

    public void regist(GuideItem.Shape shape, View targetView, View tipView,
                       GuideItem.TipDirection tipDirection, GuideItem.TipGravity tipGravity, int tipInAnim,
                       int tipOutAnim) {
        regist(shape, targetView, tipView, tipDirection, 0, tipGravity, 0,
                tipInAnim, tipOutAnim);
    }

    public void regist(GuideItem.Shape shape, View targetView, View tipView,
                       GuideItem.TipDirection tipDirection, GuideItem.TipGravity tipGravity, int tipInAnim,
                       int tipOutAnim, List<ExtraView> ev) {
        regist(shape, targetView, tipView, tipDirection, 0, tipGravity, 0,
                tipInAnim, tipOutAnim, ev);
    }

    public void regist(GuideItem.Shape shape, View targetView, View tipView,
                       GuideItem.TipDirection tipDirection, int tipOffsetTop, GuideItem.TipGravity tipGravity,
                       int tipOffsetLeft, int tipInAnim, int tipOutAnim) {
        regist(shape, targetView, 0, 0, tipView, tipDirection, tipOffsetTop,
                tipGravity, tipOffsetLeft, tipInAnim, tipOutAnim, null);
    }

    public void regist(GuideItem.Shape shape, View targetView, View tipView,
                       GuideItem.TipDirection tipDirection, int tipOffsetTop, GuideItem.TipGravity tipGravity,
                       int tipOffsetLeft, int tipInAnim, int tipOutAnim, List<ExtraView> ev) {
        regist(shape, targetView, 0, 0, tipView, tipDirection, tipOffsetTop,
                tipGravity, tipOffsetLeft, tipInAnim, tipOutAnim, ev);
    }

    public void regist(GuideItem.Shape shape, View targetView, View tipView,
                       GuideItem.TipDirection tipDirection, int tipOffsetTop, int tipInAnim,
                       int tipOutAnim) {
        regist(shape, targetView, 0, 0, tipView, tipDirection, tipOffsetTop,
                GuideItem.TipGravity.Left, 0, tipInAnim, tipOutAnim, null);
    }

    public void regist(GuideItem.Shape shape, View targetView, View tipView,
                       GuideItem.TipDirection tipDirection, int tipOffsetTop, int tipInAnim,
                       int tipOutAnim, List<ExtraView> ev) {
        regist(shape, targetView, 0, 0, tipView, tipDirection, tipOffsetTop,
                GuideItem.TipGravity.Left, 0, tipInAnim, tipOutAnim, ev);
    }

    /**
     * 注册引导对象
     *
     * @param shape            形状
     * @param targetView       待引导的目标视图
     * @param targetOffsetLeft 相对于目标视图左边的偏移位置，用于微调整
     * @param targetOffsetTop  相对于目标视图顶部的偏移位置，用于微调整
     * @param tipView          引导提示
     * @param tipDirection     引导提示在目标视图的上面还是下面
     * @param tipOffsetTop     相对于顶部的偏移位置
     * @param tipGravity       引导提示的水平对齐方式
     * @param tipOffsetLeft    引导提示相对于水平居中的偏移位置，用于微调整
     * @param tipInAnim        引导提示进场动画
     * @param tipOutAnim       引导提示离场动画
     */
    public void regist(final GuideItem.Shape shape, final View targetView,
                       final int targetOffsetLeft, final int targetOffsetTop,
                       final View tipView, final GuideItem.TipDirection tipDirection,
                       final int tipOffsetTop, final GuideItem.TipGravity tipGravity,
                       final int tipOffsetLeft, final int tipInAnim, final int tipOutAnim,
                       final List<ExtraView> ev) {
        if (targetView == null || targetView.getVisibility() != View.VISIBLE)
            return;
        targetView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        targetView.getViewTreeObserver()
                                .removeOnPreDrawListener(this);
                        Rect rect = new Rect();
                        // rect.left = targetView.getLeft();
                        // rect.right = targetView.getRight();
                        // rect.top = targetView.getTop();
                        // rect.bottom = targetView.getBottom();

                        GuideItem gi = new GuideItem();
                        gi.setTargetShape(shape);
                        gi.setTargetView(targetView);
                        gi.setTargetWidth(targetView.getWidth());
                        gi.setTargetHeight(targetView.getHeight());
                        gi.setTargetOffsetTop(targetOffsetTop);
                        gi.setTargetOffsetLeft(targetOffsetLeft);
                        gi.setTipView(tipView);
                        gi.setTipDirection(tipDirection);
                        gi.setTipOffsetTop(tipOffsetTop);
                        gi.setTipOffsetLeft(tipOffsetLeft);
                        gi.setTipGravity(tipGravity);
                        gi.setTipInAnim(tipInAnim);
                        gi.setTipOutAnim(tipOutAnim);
                        gi.setExtraViews(ev);

                        rect = getViewScreenPosition(context, gi);
                        gi.setTargetRect(rect);

                        if (guideItems == null) {
                            guideItems = new ArrayList<GuideItem>();
                        }
                        if (guideItems.contains(gi))
                            guideItems.remove(gi);
                        guideItems.add(gi);
                        return true;
                    }
                });
    }

    /**
     * 点击时显示下一个提示引导
     */
    public void showNext() {
        if (currentIndex < guideItems.size()) {
            final GuideItem gi = guideItems.get(currentIndex);
            if (gi.getTipOutAnim() != 0) {
                // Animation anim = AnimationUtils.loadAnimation(context,
                // gi.getTipOutAnim());
                // gi.getTipView().startAnimation(anim);
                // gi.getTargetView().postDelayed(new Runnable() {
                // @Override
                // public void run() {
                // gi.getTipView().setVisibility(View.INVISIBLE);
                // showExtraView(gi, false);
                // }
                // }, 500);
                gi.getTipView().setVisibility(View.INVISIBLE);
                showExtraView(gi, false);
            }
            currentIndex++;
            if (currentIndex < guideItems.size())
                invalidate();
            else
                this.setVisibility(View.GONE);
        } else {
            this.setVisibility(View.GONE);
        }
    }

    /**
     * 当前页动画选项是否引导完成
     */
    public boolean haShowOver() {
        if (guideItems == null || guideItems.size() == 0)
            return true;
        if (currentIndex < guideItems.size())
            return false;
        return true;
    }

    public void show(boolean canDraw) {
        this.canDraw = canDraw;
        this.setVisibility(View.VISIBLE);
    }
}
