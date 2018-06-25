package com.googleplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.googleplay.R;
import com.googleplay.R2;
import com.googleplay.base.BaseFragment;
import com.googleplay.fragment.load.LoadUI;

import java.util.Random;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author TanJJ
 * @time 2018/6/24 8:24
 * @des 主页Fragment
 */

public class HomeFragment extends BaseFragment {

    @BindView(R2.id.home_list_view)
    ListView mListView;
    private Unbinder mBind;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initListView();
    }

    private void initListView() {
        mListView.setAdapter(new ListAdapter());
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

    private class ListAdapter extends BaseAdapter {
        private String[] contents = new String[]{"content: 1", "content: 2"};

        @Override
        public int getCount() {
            return contents.length * 100;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, android.view.View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            View view;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout
                        .list_view_item_home, parent, false);
                // 创建ViewHolder做缓存
                viewHolder = new ViewHolder();
                viewHolder.textView1 = (TextView) view.findViewById(R.id.text1);
                viewHolder.textView2 = (TextView) view.findViewById(R.id.text2);
                // 设置ViewHolder为view的tag
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.textView1.setText(contents[0]);
            viewHolder.textView2.setText(contents[1]);
            return view;
        }
    }

    private class ViewHolder {
        TextView textView1;
        TextView textView2;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) {
            mBind.unbind();
        }
    }
}
