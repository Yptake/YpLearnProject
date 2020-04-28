package com.yptake.yplearnproject.mvp.presenter;

import android.app.Application;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.yptake.commonlibrary.smartrefreshrecycler.view.SmartRefreshRecyclerView;
import com.yptake.commonlibrary.utils.ContextUtil;
import com.yptake.yplearnproject.R;
import com.yptake.yplearnproject.mvp.contract.NewsContract;
import com.yptake.yplearnproject.mvp.model.api.Api;
import com.yptake.yplearnproject.mvp.model.api.Constants;
import com.yptake.yplearnproject.mvp.model.entity.BaseJson;
import com.yptake.yplearnproject.mvp.model.entity.ToutiaoEntity;
import com.yptake.yplearnproject.mvp.ui.receiver.NetStateChangeReceiver;
import com.yptake.yplearnproject.utils.CommonUtils;
import com.yptake.yplearnproject.utils.network.NetStateChangeObserver;
import com.yptake.yplearnproject.utils.network.NetworkType;
import com.yptake.yplearnproject.utils.observableManage.RecyclerViewHandleSubscriber;
import com.yptake.yplearnproject.utils.observableManage.TransformerObservable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/27/2020 10:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class NewsPresenter extends BasePresenter<NewsContract.Model, NewsContract.View> implements NetStateChangeObserver {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public NewsPresenter(NewsContract.Model model, NewsContract.View rootView) {
        super(model, rootView);
        registerOrUnregisterNetStatus(true);
    }

    /**
     * 获取数据
     *
     * @param type                      类型
     * @param mSmartRefreshRecyclerView 控件
     * @param mAdapter                  适配器
     * @param isFirst                   是否第一次
     * @param page                      页码
     */
    public void getToutiaoNews(String type, SmartRefreshRecyclerView mSmartRefreshRecyclerView,
                               BaseQuickAdapter mAdapter, boolean isFirst, int page) {
        mModel.getToutiaoNews(Constants.publicUrl.newsUrl, Constants.publicUrl.key, type)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 5))
                .compose(new TransformerObservable<>(mSmartRefreshRecyclerView, isFirst))
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, FragmentEvent.DESTROY_VIEW))
                .subscribe(new RecyclerViewHandleSubscriber<ToutiaoEntity>(mErrorHandler, mSmartRefreshRecyclerView, mAdapter, page) {
                    @Override
                    public List getList(BaseJson<ToutiaoEntity> mBaseJson) throws Exception {
                        return mBaseJson.getResult().data;
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        if (mRootView != null) {
                            mRootView.loadDataSuccess();
                        }
                    }
                });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        registerOrUnregisterNetStatus(false);
    }

    /**
     * 网络断开连接~
     */
    @Override
    public void onNetDisconnected() {
        CommonUtils.showToast("网络已断开");
    }

    @Override
    public void onNetConnected(NetworkType networkType) {
        CommonUtils.showToast("网络已连接");
    }

    public void registerOrUnregisterNetStatus(boolean isRegister) {
        if (isRegister) {
            NetStateChangeReceiver.registerObserver(this);
        } else {
            NetStateChangeReceiver.unregisterObserver(this);
        }
    }

}
