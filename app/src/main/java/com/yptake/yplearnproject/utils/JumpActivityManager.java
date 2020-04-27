package com.yptake.yplearnproject.utils;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jess.arms.utils.ArmsUtils;
import com.yptake.yplearnproject.mvp.model.api.Constants;
import com.yptake.yplearnproject.mvp.ui.activity.PublicH5Activity;


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



}
