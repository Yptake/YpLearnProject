package com.yptake.commonlibrary.smartrefreshrecycler.listener;


import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.jess.arms.utils.ArmsUtils;

public abstract class OnSimpleStatusListener implements OnStatusListener {

    @Override
    public void onError() {
        onRequest();
    }

    @Override
    public void onRequest() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onEmpty() {
        onRequest();
    }

    @Override
    public void onNoNetwork() {
        ArmsUtils.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    }

    @Override
    public void onCustomerClick(@NonNull View view) {

    }
}
