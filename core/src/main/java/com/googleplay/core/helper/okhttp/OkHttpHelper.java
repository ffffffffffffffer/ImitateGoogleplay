package com.googleplay.core.helper.okhttp;

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

public class OkHttpHelper {

    public static String execute(String url) throws IOException {
        // 使用OkHttp
        OkHttpClient okHttpClient = new OkHttpClient();
        // 定义请求对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        // 请求网络
        Response execute = call.execute();
        if (execute.isSuccessful()) {
            return execute.body().string();
        }
        return null;
    }

    public static void enqueue(String url, Callback responseCallback) throws IOException {
        // 使用OkHttp
        OkHttpClient okHttpClient = new OkHttpClient();
        // 定义请求对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        // 请求网络
        call.enqueue(responseCallback);
    }
}
