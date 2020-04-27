package com.yptake.yplearnproject.mvp.ui.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;


/**
 * 初始化服务，防止在application的主线程中做耗时初始化
 */
public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.jz.jzaudio.mvp.ui.service.action.INIT";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        //数据库初始化
//        LitePal.initialize(this);
        // 并且对当前数据库的表单的进行初始化
//        CutoverNowDbTableNameHelp.getInstance().startCutoverDbTable();
        // 图片选择框架初始化选项
//        initImagePicker();
        // 初始化友盟
//        initUmeng();

    }

//    private void initImagePicker() {
//        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(new GlideImageLoader());
//        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
//        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(500);//保存文件的宽度。单位像素
//        imagePicker.setOutPutY(500);//保存文件的高度。单位像素
//    }
//
//    private void initUmeng() {
//        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
//        UMConfigure.setProcessEvent(true);//支持多进程打点
//        //友盟分享
//        UMConfigure.init(this, BuildConfig.YOUMENG_APP_KEY, "umeng", UMConfigure.DEVICE_TYPE_PHONE, BuildConfig.YOUMENG_APP_SECRET);
//        PlatformConfig.setWeixin(BuildConfig.WEIXIN_APP_ID, BuildConfig.WEIXIN_APP_SECRET);
//        PlatformConfig.setSinaWeibo(BuildConfig.SINA_APP_ID, BuildConfig.SINA_APP_SECRET, "http://sns.whalecloud.com");
//        PlatformConfig.setQQZone(BuildConfig.QQ_APP_ID, BuildConfig.QQ_APP_SECRET);
//
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
//    }

}