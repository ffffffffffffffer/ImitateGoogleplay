package com.googleplay.fragment.home;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.googleplay.base.BaseFragment;
import com.googleplay.core.Constant;
import com.googleplay.core.adapter.SuperAdapter;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.fragment.home.bean.AppInfo;
import com.googleplay.fragment.home.bean.HomeBean;
import com.googleplay.fragment.load.LoadUI;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author TanJJ
 * @time 2018/6/24 8:24
 * @des 主页Fragment
 */

public class HomeFragment extends BaseFragment {

    // ListView数据
    // 轮播图数据
    private List<String> mPictures;
    private List<AppInfo> mAppInfo;

    private View initListView() {
        ListView listView = new ListView(GooglePlay.getApplicationContext());
        // TODO: 2018/7/3 这里写死了,不灵活,以后有时间看看怎么优化.
        listView.setBackgroundColor(Color.parseColor("#15000000"));
        listView.setAdapter(new ListAdapter(mAppInfo));
        return listView;
    }

    // 子线程执行
    @Override
    public LoadUI.LoadState onStartLoadDate() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 网络请求
        // 使用OkHttp
        OkHttpClient okHttpClient = new OkHttpClient();
        // 定义请求对象
        Request request = new Request.Builder()
                .url(Constant.HOME + "0")
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            // 请求网络
            Response execute = call.execute();
            if (execute.isSuccessful()) {
                String responseData = execute.body().string();
                // 解析json数据
                HomeBean homeBean = new Gson().fromJson(responseData, HomeBean.class);
                // 判断解析出来的bean对象是否有数据
                if (homeBean == null || homeBean.list.size() == 0) {
                    return LoadUI.LoadState.EMPTY;
                }

                // 获取ListView数据
                mAppInfo = homeBean.list;
                // 获取轮播图数据
                mPictures = homeBean.picture;
                return LoadUI.LoadState.SUCCESS;
            } else {
                return LoadUI.LoadState.ERROR;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return LoadUI.LoadState.ERROR;
        }
    }

    @Override
    public View onSuccessView() {
        return initListView();
    }

    private class ListAdapter extends SuperAdapter<AppInfo> {

        private ListAdapter(List<AppInfo> dates) {
            super(dates);
        }

        @Override
        public BaseHolder getItemHolder() {
            return new AppItemHolder();
        }

        @Override
        public boolean isLoadMore() {
            return true;
        }
    }
}
