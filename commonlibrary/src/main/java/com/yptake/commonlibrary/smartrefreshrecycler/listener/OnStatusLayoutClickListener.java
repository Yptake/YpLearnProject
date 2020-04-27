package com.yptake.commonlibrary.smartrefreshrecycler.listener;

import android.view.View;

/**
 * 默认的view点击事件回调
 */
public interface OnStatusLayoutClickListener {

    /**
     * 空数据布局 View 被点击
     *
     * @param view 被点击的 View
     */
    void onEmptyClick(View view);

    /**
     * 出错布局 View 被点击
     *
     * @param view 被点击的 View
     */
    void onErrorClick(View view);

    /**
     * 无网络布局 View 被点击
     *
     * @param view 被点击的 View
     */
    void onNoNetWorkClick(View view);

    /**
     * 自定义状态布局布局 View 被点击
     *
     * @param view 被点击的 View
     */
    void onCustomerClick(View view);




}
