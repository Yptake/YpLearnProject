package com.yptake.yplearnproject.mvp.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.yptake.commonlibrary.widget.ProgressWebView;
import com.yptake.yplearnproject.R;
import com.yptake.yplearnproject.mvp.model.api.Constants;
import com.yptake.yplearnproject.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 通用的h5
 */
public class PublicH5Activity extends BaseActivity {


    @BindView(R.id.wb_public)
    ProgressWebView wbPublic;
    @BindView(R.id.public_toolbar_title)
    TextView mTvTitle;

    private String title;
    private String url;
    private String content;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_public_h5;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        CommonUtils.setNavBarStatus(this, false, getResources().getColor(R.color.white100), true);
        init();

    }

    public void init() {
        Bundle bundle = getIntent().getBundleExtra(Constants.JUMP_BUNDLE);
        if (bundle == null) {
            title = "";
            content = "";
            url = "https://www.baidu.com";

        } else {
            title = bundle.getString(Constants.TITLE);
            content = bundle.getString(Constants.CONTENT);
            url = bundle.getString(Constants.URL);

        }
        setTitle(title);
        // 监听
        wbPublic.setOnWebViewListener(new ProgressWebView.onWebViewListener() {
            @Override
            public void onProgressChange(WebView view, int newProgress) {

            }

            @Override
            public void onPageFinish(WebView view) {

            }

            @Override
            public void getUrlTitle(String title) {
                if (mTvTitle != null) {
                    mTvTitle.setText(title);
                }
            }

            @Override
            public void getLoadUrl(String url) {

            }
        });

        wbPublic.loadUrl(url.trim());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wbPublic != null) {
            wbPublic.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            wbPublic.getSettings().setJavaScriptEnabled(false);
            wbPublic.clearHistory();
            wbPublic.clearView();
            wbPublic.removeAllViews();
            try {
                wbPublic.destroy();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    @OnClick(R.id.img_title_finish)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (wbPublic.canGoBack()) {
            wbPublic.goBack();
        } else {
            wbPublic.clearCache(true);
            super.onBackPressed();
        }
    }

}
