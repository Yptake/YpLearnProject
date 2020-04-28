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
public class TransformerObservable<T> implements ObservableTransformer<T, T> {

    private SmartRefreshRecyclerView mSmartRefreshRecyclerView;
    private ContainerStatusManage mContainerStatusManage;
    private boolean isFirst;

    public TransformerObservable() {
    }

    public TransformerObservable(ContainerStatusManage mContainerStatusManage, boolean isFirst) {
        this.mContainerStatusManage = mContainerStatusManage;
        this.isFirst = isFirst;
    }

    public TransformerObservable(SmartRefreshRecyclerView mSmartRefreshRecyclerView, boolean isFirst) {
        this.mSmartRefreshRecyclerView = mSmartRefreshRecyclerView;
        this.isFirst = isFirst;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream
                .doOnSubscribe(disposable -> {
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
                });
    }

}
