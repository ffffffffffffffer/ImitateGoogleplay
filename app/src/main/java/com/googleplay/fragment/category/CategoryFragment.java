package com.googleplay.fragment.category;

import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.googleplay.base.BaseFragment;
import com.googleplay.core.adapter.SuperAdapter;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.fragment.category.bean.CategoryBean;
import com.googleplay.fragment.load.LoadUI;

import java.io.IOException;
import java.util.List;

/**
 * @author TanJJ
 * @time 2018/7/8 21:51
 * @des 分类页面的Fragment
 */

public class CategoryFragment extends BaseFragment {


    private List<CategoryBean> mCategory;
    private SparseArray<View> mViewSparseArray = new SparseArray<>();

    @Override
    public LoadUI.LoadState onStartLoadDate() {
        // 网络请求
        try {
            CategoryProtocol categoryProtocol = new CategoryProtocol();
            mCategory = categoryProtocol.execute(0, "category");
            if (mCategory == null || mCategory.size() == 0) {
                return LoadUI.LoadState.EMPTY;
            }
            // 返回成功,并且加载view
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
        listView.setAdapter(new ListAdapter(mCategory));
        // 一定要自己手动写LayoutParams,否则ListView就不会全屏显示
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(layoutParams);
        return listView;
    }

    private class ListAdapter extends SuperAdapter<CategoryBean> {

        public ListAdapter(List<CategoryBean> dates) {
            super(dates);
        }

        @Override
        public BaseHolder getItemHolder(int position) {
            CategoryBean categoryBean = mCategory.get(position);
            if (categoryBean.type == CategoryBean.TYPE_TITLE) {
                return new CategoryItemTitleHolder();
            } else if (categoryBean.type == CategoryBean.TYPE_ITEM) {
                return new CategoryItemNormalHolder();
            }
            return null;
        }

        @Override
        @SuppressWarnings("unchecked")
        public View getView(int position, android.view.View convertView, ViewGroup parent) {
            BaseHolder viewHolder;
            View view;
            // 最原始的方法
//            // 获取当前position的类型
//            // 获取Holder
//            viewHolder = getItemHolder(position);
//            // 通过Holder获取View
//            view = viewHolder.getRootView();

            // 获取当前position的类型
            if (mViewSparseArray.get(position) == null) {
                // 获取Holder
                viewHolder = getItemHolder(position);
                // 通过Holder获取View
                view = viewHolder.getRootView();
                // 设置ViewHolder为view的tag
                view.setTag(viewHolder);
                mViewSparseArray.append(position, view);
                view.setTag(viewHolder);
            } else {
                view = mViewSparseArray.get(position);
                viewHolder = (BaseHolder) view.getTag();
            }
            // 传递数据给Holder的实现类
            viewHolder.setData(mCategory.get(position));
            return view;
        }
    }
}
