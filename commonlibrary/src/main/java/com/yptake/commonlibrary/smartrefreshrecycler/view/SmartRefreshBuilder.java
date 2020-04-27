package com.yptake.commonlibrary.smartrefreshrecycler.view;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnStatusListener;

/**
 * 参数管理~
 */
public final class SmartRefreshBuilder {


    // 自定义的布局~
    StatusLayoutManager mCustomerStatusLayoutManager;
    // 回调监听~
    OnStatusListener mOnStatusListener;

    // 是否需要默认显示loading~
    boolean enableLoading = true;
    // 是否需要刷新~
    boolean enableRefresh = true;
    // 是否需要加载更多~
    boolean enableLoadMore = true;
    // 是否需要刷新的时候禁用view操作~
    boolean mDisableContentWhenRefresh;
    // 是否需要加载的时候禁用view操作~
    boolean mDisableContentWhenLoading;
    // 刷新完成的时间~
    int refreshCompleteDate;
    // 是否需要自动刷新完成 也就是一个假的刷新
    boolean isRefreshComplete;

    // 对应的布局管理器~
    RecyclerView.LayoutManager mLayoutManager;
    // 对应的布局装饰器~
    RecyclerView.ItemDecoration mItemDecoration;

    /**
     * 创建 Build 对象
     */
    public SmartRefreshBuilder() {
    }

    public SmartRefreshBuilder setCustomerStatusLayoutManager(@NonNull StatusLayoutManager mCustomerStatusLayoutManager) {
        this.mCustomerStatusLayoutManager = mCustomerStatusLayoutManager;
        return this;
    }

    public SmartRefreshBuilder setEnableLoading(boolean enableLoading) {
        this.enableLoading = enableLoading;
        return this;
    }

    public SmartRefreshBuilder setEnableRefresh(boolean enableRefresh) {
        this.enableRefresh = enableRefresh;
        return this;
    }

    public SmartRefreshBuilder setEnableLoadMore(boolean enableLoadMore) {
        this.enableLoadMore = enableLoadMore;
        return this;
    }

    public SmartRefreshBuilder setDisableContentWhenRefresh(boolean mDisableContentWhenRefresh) {
        this.mDisableContentWhenRefresh = mDisableContentWhenRefresh;
        return this;
    }

    public SmartRefreshBuilder setDisableContentWhenLoading(boolean mDisableContentWhenLoading) {
        this.mDisableContentWhenLoading = mDisableContentWhenLoading;
        return this;
    }

    public SmartRefreshBuilder setRefreshCompleteDate(int refreshCompleteDate) {
        this.refreshCompleteDate = refreshCompleteDate;
        return this;
    }

    public SmartRefreshBuilder setRefreshComplete(boolean isRefreshComplete) {
        this.isRefreshComplete = isRefreshComplete;
        return this;
    }

    public SmartRefreshBuilder setLayoutManager(@NonNull RecyclerView.LayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        return this;
    }

    public SmartRefreshBuilder setItemDecoration(@NonNull RecyclerView.ItemDecoration mItemDecoration) {
        this.mItemDecoration = mItemDecoration;
        return this;
    }

    public SmartRefreshBuilder setOnStatusListener(@NonNull OnStatusListener mOnStatusListener) {
        this.mOnStatusListener = mOnStatusListener;
        return this;
    }

    public void build(SmartRefreshRecyclerView smartRefreshContainerView) {
        smartRefreshContainerView.initBuildStatus(this);
    }


}
