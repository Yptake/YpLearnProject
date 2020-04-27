package com.yptake.yplearnproject.mvp.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yptake.commonlibrary.widget.IsScrollViewPager;
import com.yptake.yplearnproject.R;
import com.yptake.yplearnproject.di.component.DaggerHomeComponent;
import com.yptake.yplearnproject.mvp.contract.HomeContract;
import com.yptake.yplearnproject.mvp.presenter.HomePresenter;
import com.yptake.yplearnproject.mvp.ui.fragment.MainFragment;
import com.yptake.yplearnproject.mvp.ui.fragment.MineFragment;
import com.yptake.yplearnproject.mvp.ui.fragment.MusicFragment;
import com.yptake.yplearnproject.mvp.ui.fragment.VideoFragment;
import com.yptake.yplearnproject.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/26/2020 15:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {


    @BindView(R.id.vp_view_pager)
    IsScrollViewPager mViewPager;
    @BindView(R.id.tv_home)
    AppCompatTextView mTvHome;
    @BindView(R.id.tv_video)
    AppCompatTextView mTvVideo;
    @BindView(R.id.tv_music)
    AppCompatTextView mTvMusic;
    @BindView(R.id.tv_mine)
    AppCompatTextView mTvMine;

    private List<Fragment> mFragmentList = new ArrayList<>();


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        CommonUtils.setNavBarStatus(this, false, getResources().getColor(R.color.white100), true);
        initFragment();

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
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_home, R.id.tv_video, R.id.tv_music, R.id.tv_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_home: {
                mViewPager.setCurrentItem(0, false);
            }
            break;
            case R.id.tv_video: {
                mViewPager.setCurrentItem(1, false);
            }
            break;
            case R.id.tv_music: {
                mViewPager.setCurrentItem(2, false);
            }
            break;
            case R.id.tv_mine: {
                mViewPager.setCurrentItem(3, false);
            }
            break;
            default:
        }
    }

    /**
     * 初始化fragment
     */
    public void initFragment() {

        mFragmentList.add(MainFragment.newInstance());
        mFragmentList.add(VideoFragment.newInstance());
        mFragmentList.add(MusicFragment.newInstance());
        mFragmentList.add(MineFragment.newInstance());
        AdapterViewPager mViewpagerAdapter = new AdapterViewPager(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mViewpagerAdapter);
        mViewPager.setCurrentItem(0, false);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCustomSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 响应变换
     *
     * @param position position
     */
    private void setCustomSelected(int position) {
        switch (position) {
            case 0: {
                Drawable drawableFeatured = getResources().getDrawable(R.drawable.ic_home_blue_24dp);
                mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, drawableFeatured, null, null);
                mTvHome.setTextColor(getResources().getColor(R.color.color_2196F3));
                Drawable drawableSongLibrary = getResources().getDrawable(R.drawable.ic_ondemand_video_gray_24dp);
                mTvVideo.setCompoundDrawablesWithIntrinsicBounds(null, drawableSongLibrary, null, null);
                mTvVideo.setTextColor(getResources().getColor(R.color.color_6E6E6E));
                Drawable drawableCircle = getResources().getDrawable(R.drawable.ic_music_note_gray_24dp);
                mTvMusic.setCompoundDrawablesWithIntrinsicBounds(null, drawableCircle, null, null);
                mTvMusic.setTextColor(getResources().getColor(R.color.color_6E6E6E));
                Drawable drawableMine = getResources().getDrawable(R.drawable.ic_sentiment_satisfied_gray_24dp);
                mTvMine.setCompoundDrawablesWithIntrinsicBounds(null, drawableMine, null, null);
                mTvMine.setTextColor(getResources().getColor(R.color.color_6E6E6E));
            }
            break;
            case 1: {
                Drawable drawableFeatured = getResources().getDrawable(R.drawable.ic_home_gray_24dp);
                mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, drawableFeatured, null, null);
                mTvHome.setTextColor(getResources().getColor(R.color.color_6E6E6E));
                Drawable drawableSongLibrary = getResources().getDrawable(R.drawable.ic_ondemand_video_blue_24dp);
                mTvVideo.setCompoundDrawablesWithIntrinsicBounds(null, drawableSongLibrary, null, null);
                mTvVideo.setTextColor(getResources().getColor(R.color.color_2196F3));
                Drawable drawableCircle = getResources().getDrawable(R.drawable.ic_music_note_gray_24dp);
                mTvMusic.setCompoundDrawablesWithIntrinsicBounds(null, drawableCircle, null, null);
                mTvMusic.setTextColor(getResources().getColor(R.color.color_6E6E6E));
                Drawable drawableMine = getResources().getDrawable(R.drawable.ic_sentiment_satisfied_gray_24dp);
                mTvMine.setCompoundDrawablesWithIntrinsicBounds(null, drawableMine, null, null);
                mTvMine.setTextColor(getResources().getColor(R.color.color_6E6E6E));
            }
            break;
            case 2: {
                Drawable drawableFeatured = getResources().getDrawable(R.drawable.ic_home_gray_24dp);
                mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, drawableFeatured, null, null);
                mTvHome.setTextColor(getResources().getColor(R.color.color_6E6E6E));
                Drawable drawableSongLibrary = getResources().getDrawable(R.drawable.ic_ondemand_video_gray_24dp);
                mTvVideo.setCompoundDrawablesWithIntrinsicBounds(null, drawableSongLibrary, null, null);
                mTvVideo.setTextColor(getResources().getColor(R.color.color_6E6E6E));
                Drawable drawableCircle = getResources().getDrawable(R.drawable.ic_music_note_blue_24dp);
                mTvMusic.setCompoundDrawablesWithIntrinsicBounds(null, drawableCircle, null, null);
                mTvMusic.setTextColor(getResources().getColor(R.color.color_2196F3));
                Drawable drawableMine = getResources().getDrawable(R.drawable.ic_sentiment_satisfied_gray_24dp);
                mTvMine.setCompoundDrawablesWithIntrinsicBounds(null, drawableMine, null, null);
                mTvMine.setTextColor(getResources().getColor(R.color.color_6E6E6E));
            }
            break;
            case 3: {
                Drawable drawableFeatured = getResources().getDrawable(R.drawable.ic_home_gray_24dp);
                mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, drawableFeatured, null, null);
                mTvHome.setTextColor(getResources().getColor(R.color.color_6E6E6E));
                Drawable drawableSongLibrary = getResources().getDrawable(R.drawable.ic_ondemand_video_gray_24dp);
                mTvVideo.setCompoundDrawablesWithIntrinsicBounds(null, drawableSongLibrary, null, null);
                mTvVideo.setTextColor(getResources().getColor(R.color.color_6E6E6E));
                Drawable drawableCircle = getResources().getDrawable(R.drawable.ic_music_note_gray_24dp);
                mTvMusic.setCompoundDrawablesWithIntrinsicBounds(null, drawableCircle, null, null);
                mTvMusic.setTextColor(getResources().getColor(R.color.color_6E6E6E));
                Drawable drawableMine = getResources().getDrawable(R.drawable.ic_sentiment_satisfied_blue_24dp);
                mTvMine.setCompoundDrawablesWithIntrinsicBounds(null, drawableMine, null, null);
                mTvMine.setTextColor(getResources().getColor(R.color.color_2196F3));
            }
            break;
            default:
        }
    }


}
