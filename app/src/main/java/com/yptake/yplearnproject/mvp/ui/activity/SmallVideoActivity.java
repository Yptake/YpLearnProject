package com.yptake.yplearnproject.mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yptake.commonlibrary.smartrefreshrecycler.listener.OnSimpleStatusListener;
import com.yptake.commonlibrary.smartrefreshrecycler.view.SmartRefreshBuilder;
import com.yptake.commonlibrary.smartrefreshrecycler.view.SmartRefreshRecyclerView;
import com.yptake.commonlibrary.utils.ContextUtil;
import com.yptake.commonlibrary.widget.CommonItemDecoration;
import com.yptake.yplearnproject.R;
import com.yptake.yplearnproject.mvp.model.entity.VideoEntity;
import com.yptake.yplearnproject.utils.CommonUtils;
import com.yptake.yplearnproject.utils.JumpActivityManager;
import com.yptake.yplearnproject.widget.DraggableScrollView;
import com.yptake.yplearnproject.widget.DraggableView;
import com.yptake.yplearnproject.widget.interfaces.DraggableListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmallVideoActivity extends BaseActivity implements DraggableListener, DraggableScrollView.ScrollListener {


    public static final int width = ArmsUtils.getScreenWidth(ContextUtil.getContext());

    //    @BindView(R.id.iv_img)
//    ImageView mIvImg;
    @BindView(R.id.scroll_view)
    DraggableScrollView scrollView;
    @BindView(R.id.drag_view)
    DraggableView dragView;

    @BindView(R.id.srv_recycler_view)
    SmartRefreshRecyclerView mSrvRecyclerView;

    private BaseQuickAdapter mAdapter;

    private int page = 1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_small_video;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        DragTouchListener dragTouchListener =  new DragTouchListener();
//        mLlRootView.setOnTouchListener(dragTouchListener);
//        dragTouchListener.setCancelTouchDrag(false);
        CommonUtils.setNavBarStatus(this, false, getResources().getColor(R.color.transparent), true);
        dragView.setDraggableListener(this);
        scrollView.setOnScrollListener(this);

        dragView.setNeedInterceptHorizontal(true);

//        CommonUtils.loadNormalImageView(mIvImg, "http://pic1.win4000.com/mobile/2018-08-23/5b7e707de739c.jpg");


        SmartRefreshBuilder builder = new SmartRefreshBuilder()
                .setEnableLoading(false)
                .setEnableRefresh(true)
                .setEnableLoadMore(true)
                .setDisableContentWhenRefresh(false)
                .setDisableContentWhenLoading(false)
                .setRefreshCompleteDate(2000)
                .setRefreshComplete(false)
                .setItemDecoration(new CommonItemDecoration(
                        ArmsUtils.dip2px(ContextUtil.getContext(), 5),
                        ArmsUtils.dip2px(ContextUtil.getContext(), 5)))
                .setLayoutManager(new GridLayoutManager(SmallVideoActivity.this, 2))
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
                layoutParams.height = ((width - ArmsUtils.dip2px(ContextUtil.getContext(), 5)) / 2) * 16 / 9;
                mIvImg.requestLayout();
                helper.setText(R.id.tv_title, item.title)
                        .setText(R.id.tv_play_num, String.valueOf(item.playNum))
                        .setText(R.id.tv_play_like, String.valueOf(item.likeNum));
                CommonUtils.loadNormalImageView(mIvImg, item.imgCoverUrl);
            }

        };

        mSrvRecyclerView.setAdapter(mAdapter);

        // 获取数据
        page = 1;
        getData();


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void isOnTop(boolean isTop) {
        dragView.setScrollToTop(isTop);
    }

    @Override
    public void onScrollChanged(int tY) {

    }

    @Override
    public void onClosedToBottom() {
        Toast.makeText(this, "关闭页面", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                onBackPressed();
            }
        }, 100);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClosedToLeft() {
        Toast.makeText(SmallVideoActivity.this, "向左切换，加载下一个视频...", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dragView.show();
                Toast.makeText(SmallVideoActivity.this, "视频数据加载完成", Toast.LENGTH_SHORT).show();
            }
        }, 500);
    }

    @Override
    public void onClosedToRight() {
        Toast.makeText(SmallVideoActivity.this, "向右切换，加载上一个视频...", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                dragView.show();
                Toast.makeText(SmallVideoActivity.this, "视频数据加载完成", Toast.LENGTH_SHORT).show();
            }
        }, 500);
    }

    @Override
    public void onBackgroundChanged(int top) {
        int newAlpha = 255 - (int) (255 * ((float) top / (float) dragView.getRootView().getHeight()));

        dragView.getBackground().setAlpha(newAlpha);
        if (newAlpha < 51) { //达到子控件缩放最小值，原大小的0.2倍
            scrollView.setScaleX(0.2f);
            scrollView.setScaleY(0.2f);
        } else {// newAlpha >= 204 平滑缩放
            scrollView.setScaleX(1 - (255.0f - (float) newAlpha) / 255);
            scrollView.setScaleY(1 - (255.0f - (float) newAlpha) / 255);
        }

        Log.e("----newAlpha----", newAlpha + "");
        Log.e("----top----", top + "");

    }

    @Override
    public boolean isForbidden() {
        return false;
    }


}
