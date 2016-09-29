package com.widght;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.tools.Tools;

/**
 * Created by Fang Ruijiao on 2016/9/28.
 */
public class StickyNavLayout extends LinearLayout implements NestedScrollingParent {

    private Scroller mScroller;
    public int mTopViewHeight = 0;

    public ViewPager mViewPager;
    public View mTopView;
    public View mTitleView;

    private boolean titleIsShow = false;

    public StickyNavLayout(Context context) {
        super(context);
        init();
    }

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mScroller = new Scroller(getContext());
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
    }

    @Override
    public void onStopNestedScroll(View child) {

    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (getScrollY() >= mTopViewHeight) return false;
        fling((int) velocityY);
        return true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if(dy != 0){ //当没滑动时，不处理
            boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
            boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);
            if (hiddenTop || showTop)
            {
                titleIsShow = false;
                mTitleView.setVisibility(View.GONE);
                reSetViewPagerHeight();
                scrollBy(0, dy);
                consumed[1] = dy;
            }else if(getScrollY() > 0){
                titleIsShow = true;
                mTitleView.setVisibility(View.VISIBLE);
                reSetViewPagerHeight();
            }
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if (getScrollY() >= mTopViewHeight) return false;
        fling((int) velocityY);
        return true;
    }

    public void fling(int velocityY){
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    /**
     * 用于滑动头部控件的，当头部空间被滑出屏幕外，此方法不再被调用
     * @param x
     * @param y
     */
    @Override
    public void scrollTo(int x, int y)
    {
        titleIsShow = false;
        mTitleView.setVisibility(View.GONE);
        reSetViewPagerHeight();
        if (y < 0){
            y = 0;
        }
        if (y > mTopViewHeight){
            y = mTopViewHeight;
        }
        if (y != getScrollY()){
            super.scrollTo(x, y);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        reSetViewPagerHeight();
    }

    private void reSetViewPagerHeight(){
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        if(titleIsShow)
            params.height = getMeasuredHeight() - Tools.dp2px(getContext(),68);
        else
            params.height = getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTopView.getMeasuredHeight();
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
}
