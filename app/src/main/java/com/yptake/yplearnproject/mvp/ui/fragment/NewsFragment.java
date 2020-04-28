package com.yptake.yplearnproject.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnSimpleStatusListener;
import com.yptake.commonlibrary.smartrefreshrecycler.view.SmartRefreshBuilder;
import com.yptake.commonlibrary.smartrefreshrecycler.view.SmartRefreshRecyclerView;
import com.yptake.commonlibrary.utils.ContextUtil;
import com.yptake.commonlibrary.widget.CommonItemDecoration;
import com.yptake.yplearnproject.R;
import com.yptake.yplearnproject.di.component.DaggerNewsComponent;
import com.yptake.yplearnproject.mvp.contract.NewsContract;
import com.yptake.yplearnproject.mvp.model.api.Constants;
import com.yptake.yplearnproject.mvp.model.entity.ToutiaoEntity;
import com.yptake.yplearnproject.mvp.presenter.NewsPresenter;
import com.yptake.yplearnproject.utils.CommonUtils;
import com.yptake.yplearnproject.utils.JumpActivityManager;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 新闻
 * ================================================
 */
public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View {


    @BindView(R.id.srv_recycler_view)
    SmartRefreshRecyclerView mSrvRecyclerView;
    private String mType;

    private boolean isUserVisible;
    private boolean isCompleteCreateView;
    private boolean isLoadData;
    private BaseQuickAdapter mAdapter;
    private int page = 1;

    public static NewsFragment newInstance(String type) {
        NewsFragment fragment = new NewsFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("type", type);
        fragment.setArguments(mBundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNewsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = getArguments() != null ? getArguments().getString("type") : null;
        isCompleteCreateView = true;

        SmartRefreshBuilder builder = new SmartRefreshBuilder()
                .setEnableLoading(false)
                .setEnableRefresh(true)
                .setEnableLoadMore(true)
                .setDisableContentWhenRefresh(false)
                .setDisableContentWhenLoading(false)
                .setRefreshCompleteDate(2000)
                .setRefreshComplete(false)
                .setItemDecoration(new CommonItemDecoration(
                        0,
                        ArmsUtils.dip2px(ContextUtil.getContext(), 10),
                        0,
                        ArmsUtils.dip2px(ContextUtil.getContext(), 10),
                        0,
                        ArmsUtils.dip2px(ContextUtil.getContext(), 10)))
                .setLayoutManager(new LinearLayoutManager(getActivity()))
                .setOnStatusListener(new OnSimpleStatusListener() {

                    @Override
                    public void onRequest() {
                        page = 1;
                        getData(true);
                    }

                    @Override
                    public void onRefresh() {
                        page = 1;
                        getData(false);
                    }

                    @Override
                    public void onLoadMore() {
                        page = page + 1;
                        getData(false);
                    }

                    @Override
                    public void onEmpty() {
                        super.onEmpty();
                    }

                });
        builder.build(mSrvRecyclerView);

        mAdapter = new BaseQuickAdapter<ToutiaoEntity.DataBean, BaseViewHolder>(R.layout.item_news_layout) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, ToutiaoEntity.DataBean item) {
                helper.setText(R.id.tv_title, item.title)
                        .setText(R.id.tv_author, item.author_name)
                        .setText(R.id.tv_time, item.date);
                ImageView mIvImg = helper.getView(R.id.iv_img);
                CommonUtils.loadNormalImageView(mIvImg, item.thumbnail_pic_s03);
            }
        };

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.TITLE, "");
            bundle.putString(Constants.URL, ((ToutiaoEntity.DataBean) adapter.getItem(position)).url);
            bundle.putString(Constants.CONTENT, "");
            JumpActivityManager.JumpToPublicH5Activity(getActivity(), bundle);
        });

        mSrvRecyclerView.setAdapter(mAdapter);

        if (isUserVisible & !isLoadData) {
            // 获取数据
            page = 1;
            getData(true);
        }

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isUserVisible = false;//视图销毁将变量置为false
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isUserVisible = isVisibleToUser;
        if (!isLoadData && isUserVisible && isCompleteCreateView) {
            // 获取数据
            getData(true);
        }
    }

    public void getData(boolean isFirst) {
        if (mPresenter != null) {
            mPresenter.getToutiaoNews(mType, mSrvRecyclerView, mAdapter, isFirst, page);
        }
    }

    @Override
    public void loadDataSuccess() {
        isLoadData = true;
    }

}
