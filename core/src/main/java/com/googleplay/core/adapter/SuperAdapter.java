package com.googleplay.core.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.googleplay.core.holder.BaseHolder;
import com.googleplay.core.holder.LoadMoreHolder;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/6/29 16:37
 * @des ListView的adapter基类
 */

public abstract class SuperAdapter<T> extends BaseAdapter {
    // 外界传入的数据,类型由外界决定
    private final List<T> mDates;
    private static final int LOAD_NORMAL = 0;
    private static final int LOAD_MORE = 1;
    private LoadMoreHolder mLoadMoreHolder;

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
    public int getViewTypeCount() {
        // 如果实现类没有复写isLoadMore方法就证明它不需要显示加载更多
        if (isLoadMore()) {
            return super.getViewTypeCount() + 1;// 普通类型 + 加载更多的
        } else {
            return super.getViewTypeCount();
        }

    }

    // 获得itemView的type类型，类型从0开始，不能断层
    @Override
    public int getItemViewType(int position) {
        if (position != getCount() - 1) {
            return LOAD_NORMAL;
        } else {
            // 如果是最后一个就使用加载更多的那个ItemType
            return LOAD_MORE;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, android.view.View convertView, ViewGroup parent) {
        BaseHolder<T> viewHolder = null;
        View view;
        // 获取当前position的类型
        int itemViewType = getItemViewType(position);
        if (convertView == null) {
            if (itemViewType == LOAD_NORMAL) {
                // 获取Holder
                viewHolder = getItemHolder();
            } else if (itemViewType == LOAD_MORE) {
                // 获取加载更多的Holder
                viewHolder = getLoadMore();
            }

            // 通过Holder获取View
            view = viewHolder.getRootView();
            // 设置ViewHolder为view的tag
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (BaseHolder) view.getTag();
        }
        if (itemViewType == LOAD_NORMAL) {
            // 传递数据给Holder的实现类
            viewHolder.setData(mDates.get(position));
        } else if (itemViewType == LOAD_MORE) {
            // 加载更多的Holder TODO
        }
        return view;
    }

    public boolean isLoadMore() {
        return false;
    }

    /**
     * 要求实现类提供实现BaseHolder的类
     */
    public abstract BaseHolder getItemHolder();

    /**
     * 加载更多的Holder
     */
    private BaseHolder getLoadMore() {
        // 防止多次创建
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }
}
