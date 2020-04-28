package com.yptake.yplearnproject.utils.observableManage;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yptake.commonlibrary.smartrefreshrecycler.view.SmartRefreshRecyclerView;
import com.yptake.yplearnproject.mvp.model.api.Api;
import com.yptake.yplearnproject.mvp.model.entity.BaseJson;
import com.yptake.yplearnproject.mvp.ui.receiver.NetStateChangeReceiver;
import com.yptake.yplearnproject.mvp.ui.service.NetWorkStatusService;
import com.yptake.yplearnproject.utils.network.NetStateChangeObserver;
import com.yptake.yplearnproject.utils.network.NetworkType;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 对recyclerview的数据进行统一处理~
 *
 * @param <T>
 */
public abstract class RecyclerViewHandleSubscriber<T> extends ErrorHandleSubscriber<BaseJson<T>> implements Observer<BaseJson<T>> {


    private SmartRefreshRecyclerView mSmartRefreshRecyclerView;
    private BaseQuickAdapter mAdapter;
    private int page;

    public RecyclerViewHandleSubscriber(RxErrorHandler rxErrorHandler, SmartRefreshRecyclerView mSmartRefreshRecyclerView, BaseQuickAdapter mAdapter, int page) {
        super(rxErrorHandler);
        this.mSmartRefreshRecyclerView = mSmartRefreshRecyclerView;
        this.mAdapter = mAdapter;
        this.page = page;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public void onNext(BaseJson<T> mBaseJson) {
        if (mBaseJson.code == Api.REQUEST_SUCCESS) {
            List list = null;
            try {
                list = getList(mBaseJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (list == null && mBaseJson.getData() instanceof List) {
                list = (List) mBaseJson.getData();
            }

            if (list != null && list.size() > 0) {
                mSmartRefreshRecyclerView.showContentView();
                if (page == 1) {
                    mAdapter.setNewData(list);
                    mSmartRefreshRecyclerView.setRefreshStatus(true);
                } else {
                    mAdapter.addData(list);
                    mSmartRefreshRecyclerView.setLoadMoreStatus(true);
                }
            } else {
                setViewStatus();
            }

        } else {
            if (page == 1) {
                mSmartRefreshRecyclerView.showErrorView();
                mSmartRefreshRecyclerView.setRefreshStatus(false);
            } else {
                mSmartRefreshRecyclerView.showContentView();
                mSmartRefreshRecyclerView.setLoadMoreStatus(false);
            }
        }

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(@NonNull Throwable t) {
        super.onError(t);
        if (page == 1 && mSmartRefreshRecyclerView != null) {
            mSmartRefreshRecyclerView.showErrorView();
        }
    }

    public List getList(BaseJson<T> mBaseJson) throws Exception {
        return (List) mBaseJson.getData();
    }

    /**
     * 设置view的状态~
     */
    public void setViewStatus() {
        if (page == 1) {
            mSmartRefreshRecyclerView.showEmptyView();
            mSmartRefreshRecyclerView.setRefreshStatus(true);
            mSmartRefreshRecyclerView.setEnableLoadMore(false);
        } else {
            mSmartRefreshRecyclerView.showContentView();
            mSmartRefreshRecyclerView.finishLoadMoreWithNoMoreData();
        }
    }

}
