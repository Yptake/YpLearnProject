package com.yptake.yplearnproject.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.yptake.commonlibrary.utils.BarUtils;
import com.yptake.commonlibrary.utils.ContextUtil;
import com.yptake.yplearnproject.R;

public class CommonUtils {

    /**
     * @param activity    activity
     * @param isVisible   是否隐藏状态栏
     * @param color       状态栏颜色
     * @param isLightMode 是否是深色字体
     */
    public static void setNavBarStatus(Activity activity, boolean isVisible, @ColorInt int color, boolean isLightMode) {
        BarUtils.setNavBarVisibility(activity, isVisible);
        BarUtils.setStatusBarColor(activity, color);
        BarUtils.setStatusBarLightMode(activity, isLightMode);
    }


    /**
     * 加载普通图片
     *
     * @param imageView
     * @param url
     */
    public static void loadNormalImageView(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .error(R.drawable.default_cover)
                .placeholder(R.drawable.default_cover);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载普通图片
     *
     * @param imageView
     * @param uri
     */
    public static void loadNormalImageView(ImageView imageView, Uri uri) {
        DrawableTransitionOptions mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade();
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .error(R.drawable.default_cover)
                .placeholder(R.drawable.default_cover);
        Glide.with(imageView.getContext())
                .load(uri)
                .apply(requestOptions)
                .transition(mDrawableTransitionOptions)
                .into(imageView);
    }

    /**
     * 加载普通图片跳过所有缓存
     *
     * @param imageView
     * @param uri
     */
    public static void loadNormalImageViewSkipMemory(ImageView imageView, Uri uri) {
        DrawableTransitionOptions mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade();
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(imageView.getContext())
                .load(uri)
                .apply(options)
                .transition(mDrawableTransitionOptions)
                .into(imageView);
    }


    private static Toast mToast;

    /**
     * 单例 toast
     *
     * @param string
     */
    @SuppressLint("ShowToast")
    public static void showToast(String string) {
        if (mToast == null) {
            mToast = Toast.makeText(ContextUtil.getContext(), string, Toast.LENGTH_SHORT);
        } else {
            mToast.cancel();
            mToast = Toast.makeText(ContextUtil.getContext(), string, Toast.LENGTH_SHORT);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}
