package com.googleplay.fragment.home;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.googleplay.activity.AppDetailActivity;
import com.googleplay.core.adapter.SuperAdapter;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.fragment.home.bean.AppInfo;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/7/16 20:04
 * @des 对superAdapter进行封装, 实现了点击事件
 */

public class AppListAdapter extends SuperAdapter<AppInfo> implements AdapterView.OnItemClickListener {
    public AppListAdapter(List<AppInfo> dates, ListView listView) {
        super(dates);
        listView.setOnItemClickListener(this);
    }

    @Override
    public BaseHolder getItemHolder(int position) {
        return new AppItemHolder();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 跳转到详情页面
        Intent intent = new Intent(GooglePlay.getApplicationContext(), AppDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        GooglePlay.getApplicationContext().startActivity(intent);
    }
}
