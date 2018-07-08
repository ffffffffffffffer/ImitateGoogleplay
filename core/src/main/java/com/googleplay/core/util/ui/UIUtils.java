package com.googleplay.core.util.ui;

import com.googleplay.core.app.GooglePlay;

/**
 * @author TanJJ
 * @time 2018/6/21 23:07
 */

public class UIUtils {

    /**
     * 在主线程中执行任务
     */
    public static void post(Runnable runnable) {
        GooglePlay.getHandler().post(runnable);
    }

    /**
     * 执行延时任务
     */
    public static void delayedTask(Runnable runnable, long delayMillis) {
        GooglePlay.getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 从Handler中移除任务
     */
    public static void removeTask(Runnable runnable) {
        GooglePlay.getHandler().removeCallbacks(runnable);
    }
}
