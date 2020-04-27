package com.yptake.commonlibrary.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.jess.arms.utils.ArmsUtils;
import com.yptake.commonlibrary.R;

import java.util.HashMap;
import java.util.Map;

import me.jessyan.autosize.AutoSize;

public class ProgressWebView extends WebView {

    private ProgressBar progressbar;  //进度条

    private int progressHeight = ArmsUtils.dip2px(getContext(), 2);  //进度条的高度，默认10px
    private Context context;


    public ProgressWebView(Context context) {
        super(context);
        initView(context);
    }

    public ProgressWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(Context context) {

        this.context = context;
        //开启js脚本支持
        getSettings().setJavaScriptEnabled(true);
        //创建进度条
        progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        //设置加载进度条的高度
        progressbar.setLayoutParams(new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, progressHeight, 0, 0));
        Drawable drawable = context.getResources().getDrawable(R.drawable.seekbar_progress_drawable);
        progressbar.setProgressDrawable(drawable);
        //添加进度到WebView
        addView(progressbar);
        //适配手机大小
        WebSettings webSetting = getSettings();
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setAllowFileAccess(true);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(false);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
//        webSetting.setPluginState(com.tencent.smtt.sdk.WebSettings.PluginState.ON_DEMAND);

        setWebChromeClient(new WVChromeClient());
        setWebViewClient(new WVClient());
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollListener!=null){
            mScrollListener.getScroll(l, t, oldl, oldt);
        }
    }

    //进度显示
    private class WVChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (progressbar != null) {
                if (newProgress == 100) {
                    progressbar.setVisibility(GONE);
                } else {
                    progressbar.setVisibility(VISIBLE);
                    progressbar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mListener != null) {
                mListener.getUrlTitle(title);
                mListener.getLoadUrl(view.getUrl());
            }
        }
    }

    private void startAlipayActivity(String url) {
        Intent intent;
        try {
            intent = Intent.parseUri(url,
                    Intent.URI_INTENT_SCHEME);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class WVClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("platformapi/startApp")) {
                startAlipayActivity(url);
            } else if (url.contains("weixin://wap/pay?") || url.contains("http://weixin/wap/pay")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            } else if (url.startsWith("https://wx.tenpay.com/")) {
                Map<String, String> extraHeaders = new HashMap<String, String>();
                extraHeaders.put("Referer", "https://ffsm.d1xz.net/");
                view.loadUrl(url, extraHeaders);
            } else {
                view.loadUrl(url);
            }

            if (mListener != null) {
                mListener.getLoadUrl(url);
            }

            try {
                if (url.startsWith("weixin://") //微信
                        || url.startsWith("alipays://") //支付宝
                        || url.startsWith("mailto://") //邮件
                        || url.startsWith("tel://")//电话
                        || url.startsWith("dianping://")//大众点评
                    //其他自定义的scheme
                ) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent);
                    return true;
                }
            } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //https忽略证书问题
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            progressbar.setVisibility(GONE);
            if (mListener != null) {
                mListener.onPageFinish(view);
            }

            super.onPageFinished(view, url);

        }

    }

    private onWebViewListener mListener;

    public void setOnWebViewListener(onWebViewListener listener) {
        this.mListener = listener;
    }

    //进度回调接口
    public interface onWebViewListener {
        void onProgressChange(WebView view, int newProgress);

        void onPageFinish(WebView view);

        /**
         * 获取title
         *
         * @param title 标题
         */
        void getUrlTitle(String title);

        /**
         * 获取load地址
         *
         * @param url 地址
         */
        void getLoadUrl(String url);

    }

    private onWebViewScrollListener mScrollListener;

    public void setOnWebViewScrollListener(onWebViewScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    @Override
    public void setOverScrollMode(int mode) {
        super.setOverScrollMode(mode);
        AutoSize.autoConvertDensityOfGlobal((Activity) getContext());
    }

    /**
     * 滚动
     */
    public interface onWebViewScrollListener {
        /**
         * 滚动
         * @param l
         * @param t
         * @param oldl
         * @param oldt
         */
        void getScroll(int l, int t, int oldl, int oldt);
    }
}
