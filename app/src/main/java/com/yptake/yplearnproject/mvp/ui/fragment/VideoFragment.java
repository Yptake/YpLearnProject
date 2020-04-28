package com.yptake.yplearnproject.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnSimpleStatusListener;
import com.yptake.commonlibrary.smartrefreshrecycler.view.SmartRefreshBuilder;
import com.yptake.commonlibrary.smartrefreshrecycler.view.SmartRefreshRecyclerView;
import com.yptake.commonlibrary.utils.BarUtils;
import com.yptake.commonlibrary.utils.ContextUtil;
import com.yptake.commonlibrary.widget.CommonItemDecoration;
import com.yptake.yplearnproject.R;
import com.yptake.yplearnproject.di.component.DaggerVideoComponent;
import com.yptake.yplearnproject.mvp.contract.VideoContract;
import com.yptake.yplearnproject.mvp.model.entity.VideoEntity;
import com.yptake.yplearnproject.mvp.presenter.VideoPresenter;
import com.yptake.yplearnproject.utils.CommonUtils;
import com.yptake.yplearnproject.utils.JumpActivityManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 视频
 * ================================================
 */
public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View {

    public static final int width = ArmsUtils.getScreenWidth(ContextUtil.getContext());


    @BindView(R.id.srv_recycler_view)
    SmartRefreshRecyclerView mSrvRecyclerView;

    private BaseQuickAdapter mAdapter;

    private boolean isUserVisible;
    private boolean isCompleteCreateView;
    private boolean isLoadData;
    private int page = 1;


    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerVideoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(mSrvRecyclerView);
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
                        ArmsUtils.dip2px(ContextUtil.getContext(), 2),
                        ArmsUtils.dip2px(ContextUtil.getContext(), 2)))
                .setLayoutManager(new GridLayoutManager(getActivity(), 2))
                .setOnStatusListener(new OnSimpleStatusListener() {
                    @Override
                    public void onRequest() {
                        page = 1;
                        getData();
                    }

                    @Override
                    public void onRefresh() {
                        page = 1;
                        getData();
                    }

                    @Override
                    public void onLoadMore() {
                        page = page + 1;
                        getData();
                    }

                    @Override
                    public void onEmpty() {
                        super.onEmpty();
                    }

                });
        builder.build(mSrvRecyclerView);

        mAdapter = new BaseQuickAdapter<VideoEntity, BaseViewHolder>(R.layout.item_video_layout) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, VideoEntity item) {
                ImageView mIvImg = helper.getView(R.id.iv_img);
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mIvImg.getLayoutParams();
                layoutParams.height = ((width - ArmsUtils.dip2px(ContextUtil.getContext(), 2)) / 2) * 16 / 9;
                mIvImg.requestLayout();
                helper.setText(R.id.tv_title, item.title)
                        .setText(R.id.tv_play_num, String.valueOf(item.playNum))
                        .setText(R.id.tv_play_like, String.valueOf(item.likeNum));
                CommonUtils.loadNormalImageView(mIvImg, item.imgCoverUrl);
            }

        };

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ImageView imageView = view.findViewById(R.id.iv_img);
            Bundle bundle = new Bundle();
            JumpActivityManager.JumpToSmallVideoActivity(getActivity(), bundle, imageView);
        });

        mSrvRecyclerView.setAdapter(mAdapter);

        if (isUserVisible & !isLoadData) {
            // 获取数据
            page = 1;
            getData();
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
            page = 1;
            getData();
        }
    }

    private void getData() {
        if (page == 1) {
            mAdapter.setNewData(getList());
            mSrvRecyclerView.setRefreshStatus(true);
        } else if (1 < page && page < 5) {
            mAdapter.addData(getList());
            mSrvRecyclerView.setLoadMoreStatus(true);
        } else {
            mSrvRecyclerView.finishLoadMoreWithNoMoreData();
        }

    }

    public ArrayList<VideoEntity> getList() {

        ArrayList<VideoEntity> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            VideoEntity entity = new VideoEntity();
            entity.id = 1;
            entity.imgCoverUrl = "http://pic1.win4000.com/mobile/2018-08-23/5b7e707de739c.jpg";
            entity.videoUrl = "https://image-test.jiangzihezi.com/talent/android1000026471587780195272.mp4";
            entity.playNum = 11231;
            entity.likeNum = 100;
            list.add(entity);
        }
        return list;

    }

}
