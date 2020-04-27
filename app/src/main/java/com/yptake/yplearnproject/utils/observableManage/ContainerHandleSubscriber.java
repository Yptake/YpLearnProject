package com.yptake.yplearnproject.utils.observableManage;

import com.yptake.commonlibrary.smartrefreshrecycler.view.ContainerStatusManage;
import com.yptake.commonlibrary.smartrefreshrecycler.view.SmartRefreshRecyclerView;
import com.yptake.yplearnproject.mvp.model.api.Api;
import com.yptake.yplearnproject.mvp.model.entity.BaseJson;
import com.yptake.yplearnproject.mvp.ui.receiver.NetStateChangeReceiver;
import com.yptake.yplearnproject.utils.network.NetStateChangeObserver;
import com.yptake.yplearnproject.utils.network.NetworkType;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandlerFactory;

/**
 * 对容器的状态进行统一处理~
 *
 * @param <T>
 */
public abstract class ContainerHandleSubscriber<T> implements Observer<BaseJson<T>> {


    public static final int REQUEST_STATUS_SUCCESS = 0;
    public static final int REQUEST_STATUS_EMPTY = 1;

    private ContainerStatusManage mContainerStatusManage;
    private boolean isFirst;

    public ContainerHandleSubscriber(ContainerStatusManage mContainerStatusManage) {
        this(mContainerStatusManage, false);
    }

    public ContainerHandleSubscriber(ContainerStatusManage mContainerStatusManage, boolean isFirst) {
        this.mContainerStatusManage = mContainerStatusManage;
        this.isFirst = isFirst;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public void onNext(BaseJson<T> mBaseJson) {
        if (mBaseJson.code == Api.REQUEST_SUCCESS) {
            int type = getDataStatus(mBaseJson);
            if (type == REQUEST_STATUS_SUCCESS) {
                mContainerStatusManage.getStatusLayoutManager().showSuccessLayout();
                resultData(mBaseJson);
            } else if (type == REQUEST_STATUS_EMPTY) {
                mContainerStatusManage.getStatusLayoutManager().showEmptyLayout();
                resultData(null);
            }
        } else {
            if (isFirst) {
                mContainerStatusManage.getStatusLayoutManager().showErrorLayout();
            } else {
                mContainerStatusManage.getStatusLayoutManager().showSuccessLayout();
            }
        }

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(@NonNull Throwable t) {
        t.printStackTrace();
        if (isFirst) {
            mContainerStatusManage.getStatusLayoutManager().showErrorLayout();
        }
    }

    public int getDataStatus(BaseJson<T> mBaseJson) {
        if (mBaseJson.getData() != null) {
            return REQUEST_STATUS_SUCCESS;
        } else {
            return REQUEST_STATUS_EMPTY;
        }
    }

    public void resultData(BaseJson<T> mBaseJson) {

    }

}
