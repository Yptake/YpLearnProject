package com.yptake.yplearnproject.mvp.model.api;


/**
 * 常量存放处
 */
public class Constants {

    // 跳转的bundle
    public static final String JUMP_BUNDLE = "jump_bundle";
    // TITLE
    public static final String TITLE = "title";
    // CONTENT
    public static final String CONTENT = "content";
    // url
    public static final String URL = "url";




    public interface publicUrl {
        // 新闻头条
        String newsUrl = "http://v.juhe.cn/toutiao/index";
        String key = "ef7d16c079f23e32b8d51fe6509cbc14";
        String[] types = {"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};
    }


}
