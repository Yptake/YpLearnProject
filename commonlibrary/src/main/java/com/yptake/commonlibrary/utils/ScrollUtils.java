package com.yptake.commonlibrary.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.yptake.commonlibrary.widget.ConsecutiveScrollerLayout;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ScrollUtils {


    public static int computeVerticalScrollOffset(View view) {
        try {
            Method method = View.class.getDeclaredMethod("computeVerticalScrollOffset");
            method.setAccessible(true);
            return (int) method.invoke(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view.getScrollY();
    }

    public static int computeVerticalScrollRange(View view) {
        try {
            Method method = View.class.getDeclaredMethod("computeVerticalScrollRange");
            method.setAccessible(true);
            return (int) method.invoke(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view.getHeight();
    }

    public static int computeVerticalScrollExtent(View view) {
        try {
            Method method = View.class.getDeclaredMethod("computeVerticalScrollExtent");
            method.setAccessible(true);
            return (int) method.invoke(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view.getHeight();
    }

    /**
     * 获取View滑动到自身顶部的偏移量
     *
     * @param view
     * @return
     */
    public static int getScrollTopOffset(View view) {
        if (isConsecutiveScrollerChild(view) && canScrollVertically(view, -1)) {
            return -computeVerticalScrollOffset(view);
        } else {
            return 0;
        }
    }

    /**
     * 获取View滑动到自身底部的偏移量
     *
     * @param view
     * @return
     */
    public static int getScrollBottomOffset(View view) {
        if (isConsecutiveScrollerChild(view) && canScrollVertically(view, 1)) {
            return computeVerticalScrollRange(view) - computeVerticalScrollOffset(view)
                    - computeVerticalScrollExtent(view);
        } else {
            return 0;
        }
    }

    /**
     * 是否是可以垂直滚动View。(内容可以滚动，或者本身就是个滚动布局)
     *
     * @param view
     * @return
     */
    public static boolean canScrollVertically(View view) {
        return isConsecutiveScrollerChild(view) && (canScrollVertically(view, 1) || canScrollVertically(view, -1));
    }

    /**
     * Check if this view can be scrolled vertically in a certain direction.
     *
     * @param direction Negative to check scrolling up, positive to check scrolling down.
     * @return true if this view can be scrolled in the specified direction, false otherwise.
     */
    public static boolean canScrollVertically(View view, int direction) {
        if (view instanceof AbsListView) {
            AbsListView listView = (AbsListView) view;
            return listView.canScrollList(direction);
        } else {
            return view.canScrollVertically(direction);
        }
    }

    /**
     * 获取当前触摸点下的View
     * @param rootView
     * @param touchX
     * @param touchY
     * @return
     */
    public static List<View> getTouchViews(View rootView, int touchX, int touchY) {
        List views = new ArrayList();
        addTouchViews(views, rootView, touchX, touchY);
        return views;
    }

    public static void addTouchViews(List<View> views, View view, int touchX, int touchY) {
        if (isTouchPointInView(view, touchX, touchY)) {
            views.add(view);
        }

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                addTouchViews(views, viewGroup.getChildAt(i), touchX, touchY);
            }
        }
    }

    /**
     * 判断触摸点是否在View内
     * @param view
     * @param x
     * @param y
     * @return
     */
    public static boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        int left = position[0];
        int top = position[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (x >= left && x <= right && y >= top && y <= bottom) {
            return true;
        }
        return false;
    }

    public static List<Integer> getScrollOffsetForViews(List<View> views) {
        List<Integer> offsets = new ArrayList<>();
        for (View view : views) {
            offsets.add(computeVerticalScrollOffset(view));
        }
        return offsets;
    }

    public static boolean equalsOffsets(List<Integer> offsets1, List<Integer> offsets2) {
        if (offsets1.size() != offsets2.size()) {
            return false;
        }

        int size = offsets1.size();

        for (int i = 0; i < size; i++) {
            if (!offsets1.get(i).equals(offsets2.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断View是否是支持连续滚动的
     *
     * @param view
     * @return
     */
    public static boolean isConsecutiveScrollerChild(View view) {

        ViewGroup.LayoutParams lp = view.getLayoutParams();

        if (lp instanceof ConsecutiveScrollerLayout.LayoutParams) {
            return ((ConsecutiveScrollerLayout.LayoutParams) lp).isConsecutive;
        }

        return true;
    }

}