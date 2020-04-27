package com.yptake.yplearnproject.utils.observableManage;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.yptake.commonlibrary.smartrefreshrecycler.view.ContainerStatusManage;
import com.yptake.commonlibrary.smartrefreshrecycler.view.SmartRefreshRecyclerView;
import com.yptake.commonlibrary.utils.ContextUtil;
import com.yptake.yplearnproject.mvp.ui.receiver.NetStateChangeReceiver;
import com.yptake.yplearnproject.utils.CommonUtils;
import com.yptake.yplearnproject.utils.network.NetStateChangeObserver;
import com.yptake.yplearnproject.utils.network.NetworkType;
import com.yptake.yplearnproject.utils.network.NetworkUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 处理订阅前需要的状态处理~
 *
 * @param <T>
 */
public class TransformerObservable<T> implements ObservableTransformer<T, T>, NetStateChangeObserver {

    private SmartRefreshRecyclerView mSmartRefreshRecyclerView;
    private ContainerStatusManage mContainerStatusManage;
    private boolean isFirst;
    private Disposable requestDisposable;


    public TransformerObservable() {
        registerOrUnregisterNetStatus(true);
    }

    public TransformerObservable(ContainerStatusManage mContainerStatusManage, boolean isFirst) {
        this.mContainerStatusManage = mContainerStatusManage;
        this.isFirst = isFirst;
        registerOrUnregisterNetStatus(true);
    }

    public TransformerObservable(SmartRefreshRecyclerView mSmartRefreshRecyclerView, boolean isFirst) {
        this.mSmartRefreshRecyclerView = mSmartRefreshRecyclerView;
        this.isFirst = isFirst;
        registerOrUnregisterNetStatus(true);
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream
                .doOnSubscribe(disposable -> {
                    requestDisposable = disposable;
                    if (isFirst) {
                        if (mSmartRefreshRecyclerView != null) {
                            mSmartRefreshRecyclerView.showLoadingView();
                        }
                        if (mContainerStatusManage != null) {
                            mContainerStatusManage.getStatusLayoutManager().showLoadingLayout();
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    registerOrUnregisterNetStatus(false);
                });
    }

    /**
     * 网络断开连接~
     */
    @Override
    public void onNetDisconnected() {
        if (!DeviceUtils.hasInternet(ContextUtil.getContext())) {
            if (mSmartRefreshRecyclerView != null) {
                mSmartRefreshRecyclerView.showNoNetwork();
            }
            if (mContainerStatusManage != null) {
                mContainerStatusManage.getStatusLayoutManager().showNoNetwork();
            }
            dismissDisposable();
        }
        CommonUtils.showToast("网络已断开");
    }

    @Override
    public void onNetConnected(NetworkType networkType) {
        if (mSmartRefreshRecyclerView != null) {
            mSmartRefreshRecyclerView.showErrorView();
        }
        if (mContainerStatusManage != null) {
            mContainerStatusManage.getStatusLayoutManager().showErrorLayout();
        }
        dismissDisposable();
        CommonUtils.showToast("网络已连接");
    }

    public void dismissDisposable() {
        if (requestDisposable != null && !requestDisposable.isDisposed()) {
            requestDisposable.dispose();
            requestDisposable = null;
        }
    }

    public void registerOrUnregisterNetStatus(boolean isRegister) {
        if (isRegister) {
            NetStateChangeReceiver.registerObserver(this);
        } else {
            NetStateChangeReceiver.unregisterObserver(this);
        }
    }

}
