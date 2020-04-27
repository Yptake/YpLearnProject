package com.yptake.yplearnproject.utils.observableManage;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.jess.arms.utils.ArmsUtils;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnSimpleStatusLayoutChildClickListener;
import com.yptake.commonlibrary.smartrefreshrecycler.view.StatusLayoutManager;
import com.yptake.yplearnproject.R;

import java.lang.ref.WeakReference;

public class CommonStatusLayoutManager {

    public static StatusLayoutManager getNewStatusLayoutManager(Context mContext, View mView) {
        WeakReference<Context> mWeakReferenceContext = new WeakReference<>(mContext);
        if (mWeakReferenceContext.get() == null || mView == null) {
            return null;
        }
        return new StatusLayoutManager.Builder(mView)
                .setDefaultLayoutsBackgroundColor(Color.TRANSPARENT)
                // 自定义布局
                .setLoadingLayout(View.inflate(mWeakReferenceContext.get(), R.layout.layout_status_layout_manager_loading, null))
                .setEmptyLayout(View.inflate(mWeakReferenceContext.get(), R.layout.layout_status_layout_manager_empty, null))
                .setErrorLayout(View.inflate(mWeakReferenceContext.get(), R.layout.layout_status_layout_manager_error, null))
                .setEmptyLayoutChildClickIds(
                        com.yptake.commonlibrary.R.id.ll_empty_view,
                        com.yptake.commonlibrary.R.id.bt_status_empty_click,
                        com.yptake.commonlibrary.R.id.iv_status_empty_img,
                        com.yptake.commonlibrary.R.id.tv_status_empty_content
                )
                .setErrorLayoutChildClickIds(
                        com.yptake.commonlibrary.R.id.ll_error_view,
                        com.yptake.commonlibrary.R.id.bt_status_error_click,
                        com.yptake.commonlibrary.R.id.iv_status_error_image,
                        com.yptake.commonlibrary.R.id.tv_status_error_content
                )
                .setOnStatusLayoutClickChildListener(new OnSimpleStatusLayoutChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {

                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        super.onErrorChildClick(view);
                    }

                    @Override
                    public void onNoNetWorkChildClick(View view) {
                        super.onNoNetWorkChildClick(view);
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                        super.onCustomerChildClick(view);
                    }
                })
                .build();
    }


}
