package com.googleplay.fragment.home;

import android.view.View;
import android.widget.ListView;

import com.googleplay.base.BaseFragment;
import com.googleplay.core.adapter.SuperAdapter;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.fragment.load.LoadUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author TanJJ
 * @time 2018/6/24 8:24
 * @des 主页Fragment
 */

public class HomeFragment extends BaseFragment {
    private List<String> contents = new ArrayList<>();

    private View initListView() {
        ListView listView = new ListView(GooglePlay.getApplicationContext());
        listView.setAdapter(new ListAdapter(contents));
        return listView;
    }

    private void imitateData() {
        for (int i = 0; i < 60; i++) {
            contents.add("content: " + i);
        }
    }

    @Override
    public LoadUI.LoadState onStartLoadDate() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LoadUI.LoadState[] states = new LoadUI.LoadState[]{LoadUI.LoadState.SUCCESS, LoadUI.LoadState.ERROR, LoadUI
                .LoadState.EMPTY};
        Random random = new Random();
        int i = random.nextInt(states.length);
        return states[i];

    }

    @Override
    public View onSuccessView() {
        // 模拟假数据
        imitateData();
        return initListView();
    }

    private class ListAdapter extends SuperAdapter<String> {

        private ListAdapter(List<String> dates) {
            super(dates);
        }

        @Override
        public BaseHolder getItemHolder() {
            return new AppItemHolder();
        }
    }
}
