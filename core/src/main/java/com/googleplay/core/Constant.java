package com.googleplay.core;

/**
 * @author TanJJ
 * @time 2018/6/25 20:25
 */

public class Constant {
    // http://10.0.2.2:8080/GooglePlayServer/home?index=0
    public static final String HOST = "http://192.168.43.156/GooglePlayServer/";
    public static final String HOME = HOST.concat("home?index=");
    public static final String IMAGE = HOST.concat("image?name=");
    // 加载更多的页面数量
    public static final int PAGER_SIZE = 20;
}
