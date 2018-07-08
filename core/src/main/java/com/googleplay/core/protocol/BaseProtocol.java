package com.googleplay.core.protocol;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author TanJJ
 * @time 2018/7/5 18:05
 * @des 对OkHttp进行简单封装
 */

public abstract class BaseProtocol<T> {


    protected abstract String getInterfacePath();

    protected abstract T parseJson(String json);

    /**
     * 同步网络请求GET
     */
    public T execute(int index) throws IOException {
        // 使用OkHttp
        OkHttpClient okHttpClient = new OkHttpClient();
        // 定义请求对象
        Request request = new Request.Builder()
                .url(getInterfacePath() + index)
                .build();
        Call call = okHttpClient.newCall(request);
        // 请求网络
        Response execute = call.execute();
        if (execute.isSuccessful()) {
            String json = execute.body().string();
            return parseJson(json);
        }
        return null;
    }

//    /**
//     * 同步网络请求GET
//     *
//     * @param t 希望将json数据转换成的对象Bean
//     *
//     * @return 返回转入的对象Bean
//     */
//    public <T> T execute(Class<T> t) throws IOException {
//        // 使用OkHttp
//        OkHttpClient okHttpClient = new OkHttpClient();
//        // 定义请求对象
//        Request request = new Request.Builder()
//                .url(getInterfacePath())
//                .build();
//        Call call = okHttpClient.newCall(request);
//        // 请求网络
//        Response execute = call.execute();
//        if (execute.isSuccessful()) {
//            String json = execute.body().string();
//            // 解析json数据
//            return new Gson().fromJson(json, t);
//        }
//        return null;
//    }

    /**
     * 异步请求GET
     *
     * @param responseCallback 异步接口回调
     */
    public void enqueue(int index, Callback responseCallback) throws IOException {
        // 使用OkHttp
        OkHttpClient okHttpClient = new OkHttpClient();
        // 定义请求对象
        Request request = new Request.Builder()
                .url(getInterfacePath() + index)
                .build();
        Call call = okHttpClient.newCall(request);
        // 请求网络
        call.enqueue(responseCallback);
    }
}
