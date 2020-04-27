package com.yptake.yplearnproject.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jess.arms.base.AdapterViewPager;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yptake.commonlibrary.widget.IsScrollViewPager;
import com.yptake.yplearnproject.R;
import com.yptake.yplearnproject.di.component.DaggerMainComponent;
import com.yptake.yplearnproject.mvp.contract.MainContract;
import com.yptake.yplearnproject.mvp.model.api.Constants;
import com.yptake.yplearnproject.mvp.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 主页
 * ================================================
 */
public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View {

    @BindView(R.id.tl_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_view_pager)
    IsScrollViewPager mViewPager;


    private String[] titles = {"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private List<Fragment> mFragmentList = new ArrayList<>();


    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        for (int i = 0; i < titles.length; i++) {
            mFragmentList.add(NewsFragment.newInstance(Constants.publicUrl.types[i]));
        }

        AdapterViewPager mViewpagerAdapter = new AdapterViewPager(getChildFragmentManager(), mFragmentList, titles);
        mViewPager.setAdapter(mViewpagerAdapter);
        mViewPager.setCurrentItem(0, false);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mTabLayout.setupWithViewPager(mViewPager);

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

}
