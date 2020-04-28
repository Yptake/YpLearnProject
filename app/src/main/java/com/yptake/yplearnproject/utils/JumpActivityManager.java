package com.yptake.yplearnproject.utils;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.jess.arms.utils.ArmsUtils;
import com.yptake.yplearnproject.mvp.model.api.Constants;
import com.yptake.yplearnproject.mvp.ui.activity.PublicH5Activity;
import com.yptake.yplearnproject.mvp.ui.activity.SmallVideoActivity;


/**
 * 跳转帮助类
 */
public class JumpActivityManager {

    /**
     * 一般H5
     *
     * @param mContext
     * @param mBundle
     */
    public static void JumpToPublicH5Activity(Context mContext, Bundle mBundle) {
        Intent mIntent = new Intent(mContext, PublicH5Activity.class);
        mIntent.putExtra(Constants.JUMP_BUNDLE, mBundle);
        ArmsUtils.startActivity(mIntent);
    }

    /**
     * 一般小视频
     *
     * @param mContext
     * @param mBundle
     */
    public static void JumpToSmallVideoActivity(Context mContext, Bundle mBundle, ImageView mIv) {
        Intent mIntent = new Intent(mContext, SmallVideoActivity.class);
        mIntent.putExtra(Constants.JUMP_BUNDLE, mBundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mIv != null) {
            mContext.startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, mIv, "small_cover").toBundle());
        } else {
            ArmsUtils.startActivity(mIntent);
        }
    }

}
