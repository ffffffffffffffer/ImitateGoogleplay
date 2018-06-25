package com.googleplay.core.util.string;

import android.content.res.Resources;
import android.support.annotation.StringRes;

import com.googleplay.core.app.GooglePlay;

/**
 * @author TanJJ
 * @time 2018/6/21 22:49
 * @des 字符串工具
 */

public class StringUtils {

    /**
     * 获取string资源
     *
     * @param resId string资源id
     */
    public static String getString(@StringRes int resId) {
        return GooglePlay.getApplicationContext().getResources().getString(resId);
    }

    /**
     * 获取字符串数组
     * @param resId string资源id
     */
    public static String[] getStringArray(int resId) {
        return GooglePlay.getApplicationContext().getResources().getStringArray(resId);
    }

    /**
     * 获取resources资源对象
     */
    public static Resources getResources() {
        return GooglePlay.getApplicationContext().getResources();
    }
}
