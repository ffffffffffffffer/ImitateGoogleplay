package com.googleplay.core.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.googleplay.core.Constant;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.core.holder.LoadMoreHolder;
import com.googleplay.core.manager.ThreadPoolManager;

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
            if (isLoadMore()) {
                return mDates.size() + 1;// 这里还要

            } else {
                return mDates.size();
            }
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
            // 并且实现类是实现了加载更多的方法才返回对应的itemType
            if (isLoadMore()) {
                // 如果是最后一个就使用加载更多的那个ItemType
                return LOAD_MORE;
            } else {
                return LOAD_NORMAL;
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, android.view.View convertView, ViewGroup parent) {
        BaseHolder viewHolder = null;
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
            // 加载更多的Holder
            performLoadMore();
        }
        return view;
    }

    private void performLoadMore() {
        // 将UI置为加载加载中
        mLoadMoreHolder.setData(LoadMoreHolder.LOADING);

        // 加载数据
        ThreadPoolManager.getLongPool().execute(new LoadMoreTask());
    }

    private class LoadMoreTask implements Runnable {

        @Override
        public void run() {
            // 获取要加载的数据,数据来源于实现类
            int state;
            List<T> data = null;
            try {
                // 获取要加载的数据,数据来源于实现类
                data = onLoadMore();
                // 根据数据改变state
                if (data == null || data.size() == 0) {
                    state = LoadMoreHolder.LOAD_MORE_ERROR;
                }
                // 翻页--》加载一页的数据量--> 20
                // 当服务器按照约定返回时,返回的数据数量必定等于或大于20
                if (data.size() >= Constant.PAGER_SIZE) {
                    // 20-->服务器还有数据---> UI操作-- 加载更多holder的显示--->加载更多
                    state = LoadMoreHolder.LOADING;
                } else {
                    // 5--->服务器没有数据了--> UI操作-- 加载更多holder的显示--->空
                    state = LoadMoreHolder.LOAD_NOT_MORE;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // UI操作-- 加载更多holder的显示--->错误
                state = LoadMoreHolder.LOAD_MORE_ERROR;
            }

            // UI操作
            // 在主线程更新UI
            final int currentState = state;
            final List<T> currentData = data;
            GooglePlay.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    // 加载更多holder的显示
                    mLoadMoreHolder.setData(currentState);
                    // 设置点击事件
                    mLoadMoreHolder.setOnClickRetryListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 重新加载
                            performLoadMore();
                        }
                    });
                    // 这里需要在主线程中操作
                    if (currentData != null) {
                        // 更新集合中的数据
                        mDates.addAll(currentData);
                        // 刷新
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    /**
     * 如果需要加载更多,就要复写这个方法
     */
    protected List<T> onLoadMore() throws Exception {
        return null;
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
