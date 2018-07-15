package com.googleplay.core.protocol;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.googleplay.core.Constant;
import com.googleplay.core.util.file.FileUtils;
import com.googleplay.core.util.log.GLogger;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
     *
     * @param key 加密key
     */
    public T execute(int index, String key) throws IOException {
        //1.首先从本地获取数据,没有就从网络获取
        T t = getDataFromLocal(index, key);
        if (t != null) {
            return t;
        }
        //2.从网络获取数据
        return getDataFromNet(index, key, true);

    }

    /**
     * 同步网络请求GET
     *
     * @param key      加密key
     * @param isConcat 是否合并字符串
     */
    public T execute(int index, String key, boolean isConcat) throws IOException {
        //1.首先从本地获取数据,没有就从网络获取
        T t = getDataFromLocal(index, key);
        if (t != null) {
            return t;
        }
        //2.从网络获取数据
        return getDataFromNet(index, key, isConcat);

    }

    /**
     * 从网络上获取数据
     */
    private T getDataFromNet(int index, String key, boolean isConcat) throws IOException {
        // 使用OkHttp
        OkHttpClient okHttpClient = new OkHttpClient();
        String url;
        // 根据传入的boolean值是否对index和getInterfacePath()进行合并
        if (isConcat) {
            url = getInterfacePath() + index;
        } else {
            url = getInterfacePath();
        }
        // 定义请求对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        // 请求网络
        Response execute = call.execute();
        if (execute.isSuccessful()) {
            String json = execute.body().string();
            // 储存数据
            writeJson(index, json, key);
            GLogger.e("getDataFrom", "从网络上获取数据");
            return parseJson(json);
        }
        return null;
    }

    /**
     * 从本地获取数据
     */
    private T getDataFromLocal(int index, String key) {
        // 本地文件储存
        // 1.确定储存位置
        //      ①.有sd卡:sdcard/Android/data/包名/json
        //      ②.无sd卡:getCacheDir() ------> /data/data/<application package>/cache
        // 2.确定命名规则
        //      ①.getInterfacePath()+index  ---> MD5加密
        File file = createMD5File(index, "json", key);
        // 现在是从本地取数据,没有文件就证明没数据,直接返回null,从网络获取
        if (!file.exists()) {
            return null;
        }
        // 3.文件内容
        //     ①.第一行储存时间戳,用来判断是否已过指定时间
        List<String> list = FileIOUtils.readFile2List(file);
        String timeStamp = list.get(0);
        // 转成long格式
        Long aLong = Long.valueOf(timeStamp);
        // 判断是否过期
        if (System.currentTimeMillis() - aLong > Constant.DELAYED_TIME) {
            // 超过时间,重新获取网络数据
            return null;
        } else {
            //     ②.第二行开始储存json数据
            // 组合在一起
            StringBuilder stringBuilder = new StringBuilder();
            int size = list.size();
            for (int i = 1; i < size; i++) {
                String s = list.get(i);
                stringBuilder.append(s);
            }
            // 让实现类去解析json返回实现类想要的Bean
            GLogger.e("getDataFrom", "从本地中获取数据");
            GLogger.e("getDataFrom", stringBuilder.toString());
            return parseJson(stringBuilder.toString());
        }

    }

    /**
     * 写数据到指定的文件中
     *
     * @param index
     * @param json
     */
    private void writeJson(int index, String json, String key) {
        // 本地文件储存
        // 1.确定储存位置
        //      ①.有sd卡:sdcard/Android/data/包名/json
        //      ②.无sd卡:getCacheDir() ------> /data/data/<application package>/cache
        // 2.确定命名规则
        //      ①.getInterfacePath()+index  ---> MD5加密
        File file = createMD5File(index, "json", key);
        FileIOUtils.writeFileFromString(file, String.valueOf(System.currentTimeMillis()) + "\n\r");
        FileIOUtils.writeFileFromString(file, json, true);
    }

    @NonNull
    private File createMD5File(int index, String directoryName, String key) {
        String hmacMD5FileName = EncryptUtils.encryptHmacMD5ToString(getInterfacePath() + index, key);
        String jsonPath = FileUtils.getDir(directoryName);
        // 定义成file对象
        return new File(jsonPath, hmacMD5FileName);
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
