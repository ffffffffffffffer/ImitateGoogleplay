package com.googleplay.core.app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import java.util.WeakHashMap;

/**
 * @author TanJJ
 * @time 2018/5/4 22:57
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.app
 * @des 专门用来存储和获取配置信息的
 */

public class Configurator {
    // 定义储存配置信息的集合
    // 有多种类型的配置信息,就用Object
    private WeakHashMap<Object, Object> CONFIGS = new WeakHashMap<>();
    // 定义全局的Handler
    private Handler mHandler = new Handler();

    // 使用单例的方式调用
    private Configurator() {
        // 将配置完成信息默认置为false
        CONFIGS.put(ConfigType.CONFIG_READY, false);
        CONFIGS.put(ConfigType.HANDLER, mHandler);
    }

    private static class Holder {
        private static final Configurator CONFIGURATOR = new Configurator();
    }

    static Configurator getInstance() {
        return Holder.CONFIGURATOR;
    }

    public void configure() {
        // 将配置完成信息置为true
        CONFIGS.put(ConfigType.CONFIG_READY, true);
    }


    /**
     * 需要取出配置时都要先检查一下配置完成了没有
     */
    private void checkConfiguration() {
        boolean isReady = (boolean) CONFIGS.get(ConfigType.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("configuration is not ready,call configure() please.");
        }
    }

    /**
     * 取出配置信息
     *
     * @param key 要取出配置的key(限制传入的一定时ConfigType)
     * @param <T> 返回传入的key对应的value(通过泛型的形式规定,可以减少调用时的强制转换)
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getConfigurate(Enum<ConfigType> key) {
        // 配置完成后才能取出
        checkConfiguration();
        return (T) CONFIGS.get(key);
    }

    /**
     * 获取配置集合
     *
     * @return 返回配置集合
     */
    public WeakHashMap<Object, Object> getConfigurations() {
        return CONFIGS;
    }

    Configurator withApplicationContext(Context context) {
        CONFIGS.put(ConfigType.APPLICATION_CONTEXT, context);
        return this;
    }

   public Configurator withMainLooper(Looper looper) {
        CONFIGS.put(ConfigType.LOOPER, looper);
        return this;
    }
}
