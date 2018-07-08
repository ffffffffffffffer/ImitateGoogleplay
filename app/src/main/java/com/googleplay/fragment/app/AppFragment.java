package com.googleplay.fragment.app;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.googleplay.base.BaseFragment;
import com.googleplay.core.adapter.SuperAdapter;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.fragment.home.bean.AppInfo;
import com.googleplay.fragment.home.bean.HomeBean;
import com.googleplay.fragment.home.protocol.HomeProtocol;
import com.googleplay.fragment.load.LoadUI;

import java.io.IOException;
import java.util.List;

/**
 * @author TanJJ
 * @time 2018/7/8 11:01
 * @des 应用页面
 */

public class AppFragment extends BaseFragment {

    private List<AppInfo> mAppList;

    @Override
    public LoadUI.LoadState onStartLoadDate() {
        // 请求网络
        try {
            List<AppInfo> appBean = new AppProtocol().execute(0, "app");
            if (appBean == null || appBean.size() == 0) {
                return LoadUI.LoadState.EMPTY;
            }
            mAppList = appBean;
            return LoadUI.LoadState.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return LoadUI.LoadState.ERROR;
        }
    }

    @Override
    public View onSuccessView() {
        return initListView();
    }

    private View initListView() {
        ListView listView = new ListView(GooglePlay.getApplicationContext());
        // TODO: 2018/7/3 这里写死了,不灵活,以后有时间看看怎么优化.
        listView.setBackgroundColor(Color.parseColor("#15000000"));
        listView.setAdapter(new ListAdapter(mAppList));
        return listView;
    }

    private class ListAdapter extends SuperAdapter<AppInfo> {

        public ListAdapter(List<AppInfo> dates) {
            super(dates);
        }

        @Override
        public BaseHolder getItemHolder() {
            return new AppHolder();
        }

        @Override
        public boolean isLoadMore() {
            return true;
        }

        @Override
        protected List<AppInfo> onLoadMore() throws Exception {
            return loadMore(mAppList.size());
        }
    }

    private List<AppInfo> loadMore(int index) {
        // 请求网络
        try {
            // 休眠1.5秒
            Thread.sleep(1500);
            // 开始网络请求
            // 解析json数据
            HomeBean homeBean = new HomeProtocol().execute(index, "app");
            // 返回appInfo数据
            return homeBean.list;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
