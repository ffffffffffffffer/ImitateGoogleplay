package com.googleplay.fragment.home;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.googleplay.base.BaseFragment;
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
        listView.setAdapter(new ListAdapter(mAppInfo, listView));
        // 轮播图Holder
        PickHolder pickHolder = new PickHolder();
        View headView = pickHolder.getRootView();
        pickHolder.setData(mPictures);
        listView.addHeaderView(headView);
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
        try {
            // 请求网络
            HomeBean homeBean = new HomeProtocol().execute(0, "home");
            // 判断解析出来的bean对象是否有数据
            if (homeBean == null || homeBean.list.size() == 0) {
                return LoadUI.LoadState.EMPTY;
            }
            // 获取ListView数据
            mAppInfo = homeBean.list;
            // 获取轮播图数据
            mPictures = homeBean.picture;
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

    private class ListAdapter extends AppListAdapter {

        private ListAdapter(List<AppInfo> dates, ListView listView) {
            super(dates, listView);
        }

        @Override
        public BaseHolder getItemHolder(int position) {
            return new AppItemHolder();
        }

        @Override
        public boolean isLoadMore() {
            return true;
        }

        @Override
        protected List<AppInfo> onLoadMore() throws Exception {
            return loadMore(mAppInfo.size());
        }
    }

    private List<AppInfo> loadMore(int index) {
        // 请求网络
        try {
            // 休眠1.5秒
            Thread.sleep(1500);
            // 开始网络请求
            // 解析json数据
            HomeBean homeBean = new HomeProtocol().execute(index, "home");
            // 返回appInfo数据
            return homeBean.list;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
