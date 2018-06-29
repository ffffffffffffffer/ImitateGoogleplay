package com.googleplay.core.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/6/29 16:37
 * @des ListView的adapter基类
 */

public abstract class SuperAdapter<T> extends BaseAdapter {
    // 外界传入的数据,类型由外界决定
    private final List<T> mDates;

    public SuperAdapter(List<T> dates) {
        this.mDates = dates;
    }

    @Override
    public int getCount() {
        if (mDates != null) {
            return mDates.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (mDates != null) {
            return mDates.get(position);
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
