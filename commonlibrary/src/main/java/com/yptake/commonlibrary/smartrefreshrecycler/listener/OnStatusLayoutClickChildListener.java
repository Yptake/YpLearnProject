package com.yptake.commonlibrary.smartrefreshrecycler.listener;

import android.view.View;

/**
 * 子view的点击事件回调~
 */
public interface OnStatusLayoutClickChildListener {

    /**
     * 空数据布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    void onEmptyChildClick(View view);

    /**
     * 出错布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    void onErrorChildClick(View view);

    /**
     * 无网络布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    void onNoNetWorkChildClick(View view);

    /**
     * 自定义状态布局布局子 View 被点击
     *
     * @param view 被点击的 View
     */
    void onCustomerChildClick(View view);




}
