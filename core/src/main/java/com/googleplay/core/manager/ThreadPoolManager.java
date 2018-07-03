package com.googleplay.core.manager;

/**
 * @author TanJJ
 * @time 2018/7/3 19:50
 * @des 线程池管理
 */

public class ThreadPoolManager {
    // 这种做法比加锁更好,并且不影响性能
    private static class ThreadPool {
        private static final ThreadPoolProxy THREAD_POOL_PROXY = new ThreadPoolProxy(3, 3, 0L);
    }

    public static ThreadPoolProxy getLongPool() {
        return ThreadPool.THREAD_POOL_PROXY;
    }

}
