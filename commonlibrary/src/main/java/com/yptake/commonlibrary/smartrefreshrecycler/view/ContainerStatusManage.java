package com.yptake.commonlibrary.smartrefreshrecycler.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;

import com.jess.arms.utils.ArmsUtils;
import com.yptake.commonlibrary.R;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnSimpleStatusLayoutChildClickListener;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnSimpleStatusLayoutClickListener;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnStatusListener;

import java.lang.ref.WeakReference;

/**
 * 容器状态管理
 */
public class ContainerStatusManage {


    private WeakReference<Context> mContext;
    // 回调监听
    private OnStatusListener mOnStatusListener;
    //
    private StatusLayoutManager mStatusLayoutManager;


    public static final class SmartRefreshContainerBuilder {


        Context mContext;
        // 回调监听~
        OnStatusListener mOnStatusListener;

        /**
         * 创建 Build 对象
         */
        public SmartRefreshContainerBuilder(Context mContext) {
            this.mContext = mContext;
        }

        public SmartRefreshContainerBuilder setOnStatusListener(OnStatusListener mListener) {
            this.mOnStatusListener = mListener;
            return this;
        }

        public ContainerStatusManage build(@NonNull View mView) {
            return new ContainerStatusManage(this, mView);
        }
    }

    public ContainerStatusManage(SmartRefreshContainerBuilder builder, @NonNull View mView) {
        this.mContext = new WeakReference<>(builder.mContext);
        this.mOnStatusListener = builder.mOnStatusListener;
        initSmartRefreshContainer(mContext.get(), mView);
    }

    @SuppressLint("ResourceType")
    private void initSmartRefreshContainer(Context mContext, View mView) {
        if (mContext == null || mView == null) {
            return;
        }
        mStatusLayoutManager = new StatusLayoutManager.Builder(mView)
                .setDefaultLayoutsBackgroundColor(Color.TRANSPARENT)
                // 自定义布局
                .setLoadingLayout(View.inflate(mContext, R.layout.layout_status_layout_manager_loading, null))
                .setEmptyLayout(View.inflate(mContext, R.layout.layout_status_layout_manager_empty, null))
                .setErrorLayout(View.inflate(mContext, R.layout.layout_status_layout_manager_error, null))
                .setEmptyLayoutChildClickIds(R.id.ll_empty_view,
                        R.id.bt_status_empty_click,
                        R.id.iv_status_empty_img,
                        R.id.tv_status_empty_content
                )
                .setNoNetworkLayoutChildClickIds(R.id.ll_no_network_view,
                        R.id.bt_status_no_network_click
                )
                .setOnStatusLayoutClickListener(new OnSimpleStatusLayoutClickListener() {
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
                        super.onNoNetWorkClick(view);
                    }

                    @Override
                    public void onCustomerClick(View view) {
                        super.onCustomerClick(view);
                    }
                })
                .setOnStatusLayoutClickChildListener(new OnSimpleStatusLayoutChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        if (view.getId() == com.yptake.commonlibrary.R.id.ll_empty_view) {
                            if (mOnStatusListener != null) {
                                mOnStatusListener.onEmpty();
                            }
                            ArmsUtils.makeText(mContext, "-----------------------ll_empty_view");
                        } else
                            if (view.getId() == com.yptake.commonlibrary.R.id.bt_status_empty_click) {
                            ArmsUtils.makeText(mContext, "-----------------------bt_status_empty_click");
                        } else if (view.getId() == com.yptake.commonlibrary.R.id.iv_status_empty_img) {
                            ArmsUtils.makeText(mContext, "-----------------------iv_status_empty_img");
                        } else if (view.getId() == com.yptake.commonlibrary.R.id.tv_status_empty_content) {
                            ArmsUtils.makeText(mContext, "-----------------------tv_status_empty_content");
                        }

                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        super.onErrorChildClick(view);
                    }

                    @Override
                    public void onNoNetWorkChildClick(View view) {
                        if (view.getId() == R.id.ll_no_network_view) {
                            if (mOnStatusListener != null) {
                                mOnStatusListener.onNoNetwork();
                            }
                        }
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                        super.onCustomerChildClick(view);
                    }
                })
                .build();

    }

    public StatusLayoutManager getStatusLayoutManager() {
        return mStatusLayoutManager;
    }


}
