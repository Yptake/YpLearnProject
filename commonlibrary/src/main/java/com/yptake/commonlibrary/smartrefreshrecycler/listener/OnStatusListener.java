package com.yptake.commonlibrary.smartrefreshrecycler.listener;


import android.view.View;

import androidx.annotation.NonNull;

public interface OnStatusListener {

    /**
     * 第一次发起请求~
     */
    void onRequest();

    /**
     * 刷新发起请求~
     */
    void onRefresh();

    /**
     * 加载更多请求~
     */
    void onLoadMore();

    /**
     * 当前数据为空了~
     */
    void onEmpty();

    /**
     * 错误的情况~
     */
    void onError();

    /**
     * 无网络的情况~
     */
    void onNoNetwork();

    /**
     * 自定义布局的子view
     */
    void onCustomerClick(@NonNull View view);




}
