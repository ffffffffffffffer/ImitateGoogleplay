package com.googleplay.core.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author TanJJ
 * @time 2018/7/3 19:51
 * @des 线程池代理类
 */

public class ThreadPoolProxy {
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;
    // 线程池
    ThreadPoolExecutor mThreadExecutor;

    public ThreadPoolProxy(int corePoolSize,
                           int maximumPoolSize,
                           long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    private void checkPool() {
        if (mThreadExecutor == null || mThreadExecutor.isShutdown()) {
            // 线程存活时间单位
            TimeUnit unit = TimeUnit.MILLISECONDS;
            // 工作队列
            // 不固定大小
            BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
            // 线程工厂
            ThreadFactory threadFactory = Executors.defaultThreadFactory();
            // 错误捕获器
            // handler = new ThreadPoolExecutor.AbortPolicy();// 抛异常---》调用者
            // handler = new ThreadPoolExecutor.CallerRunsPolicy();// 直接在当前线程执行
            // handler = new ThreadPoolExecutor.DiscardOldestPolicy();// 取出第一个，将task加到最后
            RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();// 空实现
            // 创建线程池
            mThreadExecutor = new ThreadPoolExecutor(
                    corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    unit,
                    workQueue,
                    threadFactory,
                    handler);
        }
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        // 执行线程前先检查一下
        checkPool();
        // 开始执行
        mThreadExecutor.execute(runnable);
    }

    /**
     * 执行任务
     */
    public Future<?> submit(Runnable runnable) {
        if (runnable == null) {
            return null;
        }
        // 执行线程前先检查一下
        checkPool();
        // 开始执行
        return mThreadExecutor.submit(runnable);
    }

    /**
     * 移除任务
     */
    public void remove(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        mThreadExecutor.remove(runnable);
    }
}
