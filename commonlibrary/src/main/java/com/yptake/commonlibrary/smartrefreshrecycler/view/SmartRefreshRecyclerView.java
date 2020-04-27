package com.yptake.commonlibrary.smartrefreshrecycler.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yptake.commonlibrary.R;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnSimpleStatusLayoutClickListener;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnStatusListener;


/**
 * 智能刷新列表
 */
public class SmartRefreshRecyclerView extends LinearLayout {


    private LayoutInflater inflater;
    private NestSmartRefreshLayout mNestSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    protected View mContentView;


    // 自定义的布局~
    private StatusLayoutManager mCustomerStatusLayoutManager;
    // 回调监听
    private OnStatusListener mOnStatusListener;


    // 是否需要默认显示loading
    public boolean enableLoading = true;
    // 是否需要刷新
    public boolean enableRefresh = true;
    // 是否需要加载更多
    public boolean enableLoadMore = true;
    // 是否需要刷新的时候禁用view操作
    private boolean mDisableContentWhenRefresh;
    // 是否需要加载的时候禁用view操作
    private boolean mDisableContentWhenLoading;
    // 刷新完成的时间~
    private int refreshCompleteDate;
    // 是否需要自动刷新完成 也就是一个假的刷新
    private boolean isRefreshComplete;

    // 对应的布局管理器
    private RecyclerView.LayoutManager mLayoutManager;
    // 对应的布局装饰器
    private RecyclerView.ItemDecoration mItemDecoration;


    public SmartRefreshRecyclerView(Context context) {
        this(context, null, 0);
    }

    public SmartRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void initBuildStatus(SmartRefreshBuilder builder) {
        this.mCustomerStatusLayoutManager = builder.mCustomerStatusLayoutManager;
        this.mOnStatusListener = builder.mOnStatusListener;
        this.enableLoading = builder.enableLoading;
        this.enableRefresh = builder.enableRefresh;
        this.enableLoadMore = builder.enableLoadMore;
        this.refreshCompleteDate = builder.refreshCompleteDate;
        this.isRefreshComplete = builder.isRefreshComplete;
        this.mLayoutManager = builder.mLayoutManager;
        this.mItemDecoration = builder.mItemDecoration;
        this.mDisableContentWhenRefresh = builder.mDisableContentWhenRefresh;
        this.mDisableContentWhenLoading = builder.mDisableContentWhenLoading;

        initRecyclerView();
        initStatusLayout();
        initSmartRefreshLayout();

    }

    public void init() {
        setOrientation(LinearLayout.VERTICAL);
        mContentView = inflate(R.layout.common_refresh_recyclerview_layout);
        addView(mContentView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mNestSmartRefreshLayout = mContentView.findViewById(R.id.smart_refresh_view);
        mRecyclerView = mContentView.findViewById(R.id.recycler_view);

    }


    /**
     * 初始化recyclerview
     */
    public void initRecyclerView() {

        if (mLayoutManager != null) {
            mRecyclerView.setLayoutManager(mLayoutManager);
        } else {
            mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(getContext()));
        }

        if (mItemDecoration != null) {
            removeItemDecoration(mRecyclerView);
            mRecyclerView.addItemDecoration(mItemDecoration);
        }

    }


    /**
     * 初始化状态布局
     */
    public void initStatusLayout() {
        // 已有布局管理器就不再对它实例化处理了
        if (mCustomerStatusLayoutManager == null) {
            mCustomerStatusLayoutManager = new StatusLayoutManager.Builder(mNestSmartRefreshLayout)
                    .setDefaultLayoutsBackgroundColor(Color.TRANSPARENT)
                    // 自定义布局
                    .setLoadingLayout(inflate(R.layout.layout_status_layout_manager_loading))
                    .setEmptyLayout(inflate(R.layout.layout_status_layout_manager_empty))
                    .setErrorLayout(inflate(R.layout.layout_status_layout_manager_error))
                    .build();
        }

        mCustomerStatusLayoutManager.setOnStatusLayoutClickListener(new OnSimpleStatusLayoutClickListener() {
            @Override
            public void onEmptyClick(View view) {
                if (mOnStatusListener != null) {
                    mOnStatusListener.onEmpty();
                }
            }

            @Override
            public void onErrorClick(View view) {
                if (mOnStatusListener != null) {
                    mOnStatusListener.onError();
                }
            }

            @Override
            public void onNoNetWorkClick(View view) {
                if (mOnStatusListener != null) {
                    mOnStatusListener.onNoNetwork();
                }
            }

            @Override
            public void onCustomerClick(View view) {
                if (mOnStatusListener != null) {
                    mOnStatusListener.onCustomerClick(view);
                }
            }
        });

        if (enableLoading) {
            showLoadingView();
        }

    }

    /**
     * 初始化刷新布局
     */
    public void initSmartRefreshLayout() {
        setEnableRefresh(enableRefresh);
        setEnableLoadMore(enableLoadMore);
        setDisableContentWhenRefresh(mDisableContentWhenRefresh);
        setDisableContentWhenLoading(mDisableContentWhenLoading);

        mNestSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mOnStatusListener != null) {
                    mOnStatusListener.onLoadMore();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mOnStatusListener != null) {
                    mOnStatusListener.onRefresh();
                }
                setRefreshComplete();
            }

        });

    }

    //=================StatusLayoutManager=======================//

    /**
     * 设置自定义布局
     *
     * @param customLayout 自定义布局
     */
    public void setCustomLayout(@NonNull View customLayout) {
        if (mCustomerStatusLayoutManager == null) {
            return;
        }
        mCustomerStatusLayoutManager.showCustomLayout(customLayout);
    }

    /**
     * 设置自定义布局
     *
     * @param customLayoutID 自定义状态布局 ID
     */
    public void setCustomLayout(@LayoutRes int customLayoutID) {
        if (mCustomerStatusLayoutManager == null) {
            return;
        }
        mCustomerStatusLayoutManager.showCustomLayout(customLayoutID);
    }

    /**
     * 显示布局
     */
    public void showContentView() {
        if (mCustomerStatusLayoutManager == null) {
            return;
        }
        mCustomerStatusLayoutManager.showSuccessLayout();
    }

    /**
     * 没有数据的布局
     */
    public void showEmptyView() {
        if (mCustomerStatusLayoutManager == null) {
            return;
        }
        loadCompletePromptly();
        new Handler().postDelayed(() -> {
            if (mCustomerStatusLayoutManager != null)
                mCustomerStatusLayoutManager.showEmptyLayout();
        }, 20);

    }

    /**
     * 错误布局
     */
    public void showErrorView() {
        if (mCustomerStatusLayoutManager == null) {
            return;
        }
        loadCompletePromptly();
        new Handler().postDelayed(() -> {
            if (mCustomerStatusLayoutManager != null)
                mCustomerStatusLayoutManager.showErrorLayout();
        }, 20);

    }

    /**
     * 加载布局
     */
    public void showLoadingView() {
        if (mCustomerStatusLayoutManager == null) {
            return;
        }
        loadCompletePromptly();
        new Handler().postDelayed(() -> {
            if (mCustomerStatusLayoutManager != null)
                mCustomerStatusLayoutManager.showLoadingLayout();
        }, 20);

    }

    /**
     * 加载布局
     */
    public void showNoNetwork() {
        if (mCustomerStatusLayoutManager == null) {
            return;
        }
        loadCompletePromptly();
        new Handler().postDelayed(() -> {
            if (mCustomerStatusLayoutManager != null)
                mCustomerStatusLayoutManager.showNoNetwork();
        }, 20);

    }


    //=================RecyclerView=======================//

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (mRecyclerView == null) {
            return;
        }
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 获取列表组件~
     */
    public RecyclerView getCurrentRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 列表滚动到顶部
     */
    public void scrollToPosition() {
        if (mRecyclerView == null) {
            return;
        }
        mRecyclerView.scrollToPosition(0);
    }

    /**
     * 移除多的ItemDecoration
     *
     * @param mRecyclerView 需要移除的列表~
     */
    public void removeItemDecoration(RecyclerView mRecyclerView) {
        for (int i = 0; i < mRecyclerView.getItemDecorationCount(); i++) {
            mRecyclerView.removeItemDecorationAt(i);
        }
    }


    //=================NestSmartRefreshLayout=======================//

    /**
     * 获取刷新组件~
     */
    public NestSmartRefreshLayout getSmartRefreshLayout() {
        return mNestSmartRefreshLayout;
    }

    /**
     * 是否需要自动完成刷新操作~
     */
    public void setRefreshComplete() {
        if (isRefreshComplete) {
            // 自动完成刷新~
            mNestSmartRefreshLayout.finishRefresh(refreshCompleteDate);
        }
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        mNestSmartRefreshLayout.autoRefresh();
    }

    /**
     * 是否启用列表惯性滑动到底部时自动加载更多
     *
     * @param b bool
     */
    public void setEnableAutoLoadMore(boolean b) {
        mNestSmartRefreshLayout.setEnableAutoLoadMore(b);
    }

    /**
     * 是否需要刷新功能
     *
     * @param b bool
     */
    public void setEnableRefresh(boolean b) {
        mNestSmartRefreshLayout.setEnableRefresh(b);
        enableRefresh = b;
    }

    /**
     * 是否需要加载更多功能
     *
     * @param b bool
     */
    public void setEnableLoadMore(boolean b) {
        mNestSmartRefreshLayout.setEnableLoadMore(b);
        enableLoadMore = b;
    }

    /**
     * 是否需要刷新的时候禁用view操作
     *
     * @param b bool
     */
    public void setDisableContentWhenRefresh(boolean b) {
        mNestSmartRefreshLayout.setDisableContentWhenRefresh(b);
        mDisableContentWhenRefresh = b;
    }

    /**
     * 是否需要加载的时候禁用view操作
     *
     * @param b bool
     */
    public void setDisableContentWhenLoading(boolean b) {
        mNestSmartRefreshLayout.setDisableContentWhenLoading(b);
        mDisableContentWhenLoading = b;
    }

    /**
     * 设置刷新是否成功
     *
     * @param b bool
     */
    public void setRefreshStatus(boolean b) {
        mNestSmartRefreshLayout.finishRefresh(b);
    }

    /**
     * 设置加载更多是否成功
     *
     * @param b bool
     */
    public void setLoadMoreStatus(boolean b) {
        mNestSmartRefreshLayout.finishLoadMore(b);
    }

    /**
     * 没有更多了
     */
    public void resetNoMoreData() {
        mNestSmartRefreshLayout.resetNoMoreData();
    }

    /**
     * 操作完成
     */
    public void loadComplete() {
        if (mNestSmartRefreshLayout.getState() == RefreshState.Refreshing)
            mNestSmartRefreshLayout.finishRefresh();
        if (mNestSmartRefreshLayout.getState() == RefreshState.Loading)
            mNestSmartRefreshLayout.finishLoadMore();
    }

    /**
     * 操作完成立刻
     */
    public void loadCompletePromptly() {
        if (mNestSmartRefreshLayout.getState() == RefreshState.Refreshing)
            mNestSmartRefreshLayout.finishRefresh(0, true, Boolean.FALSE);
        if (mNestSmartRefreshLayout.getState() == RefreshState.Loading)
            mNestSmartRefreshLayout.finishLoadMore(0, true, Boolean.TRUE);
    }

    /**
     * 结束没有更多数据了~
     */
    public void finishLoadMoreWithNoMoreData() {
        mNestSmartRefreshLayout.postDelayed(() -> {
            if (mNestSmartRefreshLayout != null) {
                mNestSmartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }, 400);
    }

    public View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(getContext());
        }
        return inflater.inflate(resource, null);
    }


}
