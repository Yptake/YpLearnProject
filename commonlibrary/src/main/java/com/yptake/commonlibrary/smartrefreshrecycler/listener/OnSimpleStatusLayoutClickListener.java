package com.yptake.commonlibrary.smartrefreshrecycler.listener;

import android.content.Intent;
import android.view.View;

import com.jess.arms.utils.ArmsUtils;

/**
 * 默认的view点击事件回调
 */
public abstract class OnSimpleStatusLayoutClickListener implements OnStatusLayoutClickListener {

    @Override
    public void onEmptyClick(View view) {

    }

    @Override
    public void onErrorClick(View view) {

    }

    @Override
    public void onNoNetWorkClick(View view) {
        ArmsUtils.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    }

    @Override
    public void onCustomerClick(View view) {

    }


}
