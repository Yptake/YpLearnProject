package com.yptake.commonlibrary.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;


import androidx.viewpager.widget.ViewPager;

import com.yptake.commonlibrary.R;


/**
 * 是否可以滑动的viewpager
 */
public class IsScrollViewPager extends ViewPager {

    public boolean isScroll = false;

    public boolean isScroll() {
        return isScroll;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }

    public IsScrollViewPager(Context context) {
        super(context);
    }

    public IsScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IsScrollViewPager);
        isScroll = array.getBoolean(R.styleable.IsScrollViewPager_isScroll, false);
        array.recycle();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

}

