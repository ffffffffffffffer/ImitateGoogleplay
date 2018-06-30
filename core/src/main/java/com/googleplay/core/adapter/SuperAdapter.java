package com.googleplay.core.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.googleplay.core.holder.BaseHolder;
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
    @SuppressWarnings("unchecked")
    public View getView(int position, android.view.View convertView, ViewGroup parent) {
        BaseHolder<T> viewHolder;
        View view;
        if (convertView == null) {
            // 获取Holder
            viewHolder = getItemHolder();
            // 通过Holder获取View
            view = viewHolder.getRootView();
            // 设置ViewHolder为view的tag
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (BaseHolder) view.getTag();
        }
        // 传递数据给Holder的实现类
        viewHolder.setData(mDates.get(position));
        return view;
    }

    /**
     * 要求实现类提供实现BaseHolder的类
     */
    public abstract BaseHolder getItemHolder();
}
