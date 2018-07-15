package com.googleplay.fragment.subject;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.googleplay.base.BaseFragment;
import com.googleplay.core.adapter.SuperAdapter;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.fragment.load.LoadUI;

import java.io.IOException;
import java.util.List;

/**
 * @author TanJJ
 * @time 2018/7/8 19:28
 * @des 专题页面Fragment
 */

public class SubjectFragment extends BaseFragment {

    private List<SubjectBean> mSubject;

    @Override
    public LoadUI.LoadState onStartLoadDate() {
        // 网络请求
        SubjectProtocol subjectProtocol = new SubjectProtocol();
        try {
            // 请求网络
            mSubject = subjectProtocol.execute(0, "subject");
            if (mSubject == null || mSubject.size() == 0) {
                return LoadUI.LoadState.EMPTY;
            }
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
        listView.setAdapter(new ListAdapter(mSubject));
        return listView;
    }

    private class ListAdapter extends SuperAdapter<SubjectBean> {

        public ListAdapter(List<SubjectBean> dates) {
            super(dates);
        }

        @Override
        public BaseHolder getItemHolder(int position) {
            return new SubjectHolder();
        }
    }
}
