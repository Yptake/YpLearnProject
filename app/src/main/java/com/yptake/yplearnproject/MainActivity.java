package com.yptake.yplearnproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.jess.arms.utils.ArmsUtils;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnSimpleStatusListener;
import com.yptake.commonlibrary.smartrefreshrecycler.view.ContainerStatusManage;
import com.yptake.yplearnproject.mvp.model.api.service.UserService;
import com.yptake.yplearnproject.mvp.model.entity.BaseJson;
import com.yptake.yplearnproject.mvp.ui.receiver.NetStateChangeReceiver;
import com.yptake.yplearnproject.utils.network.NetStateChangeObserver;
import com.yptake.yplearnproject.utils.network.NetworkType;
import com.yptake.yplearnproject.utils.observableManage.ContainerHandleSubscriber;
import com.yptake.yplearnproject.utils.observableManage.TransformerObservable;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private ContainerStatusManage statusManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusManage = new ContainerStatusManage
                .SmartRefreshContainerBuilder(this)
                .setOnStatusListener(new OnSimpleStatusListener() {
                    @Override
                    public void onError() {
                        statusManage.getStatusLayoutManager().showNoNetwork();
                    }

                    @Override
                    public void onRequest() {
                        getData(true);
                    }

                    @Override
                    public void onEmpty() {
                        getData(true);
                    }

                })
                .build(getWindow().getDecorView());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void getData(boolean isFirst) {
        Map<String, Object> map = new HashMap<>();
        map.put("favoritesId", "2012117465");

    }


}
