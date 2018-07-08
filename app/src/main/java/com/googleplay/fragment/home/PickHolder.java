package com.googleplay.fragment.home;

import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.googleplay.R;
import com.googleplay.R2;
import com.googleplay.core.holder.BaseHolder;

import java.util.List;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/7/8 14:43
 * @des 轮播图片Holder
 */

public class PickHolder extends BaseHolder<List<String>> {

    @BindView(R2.id.item_home_view_pager)
    ViewPager mViewPager;
    @BindView(R2.id.item_home_picture_container_indicator)
    LinearLayout mContainerIndicator;

    @Override
    protected Object initView() {
        return R.layout.item_home_picture;
    }

    @Override
    protected void refreshUI(List<String> data) {
        // 给容器添加点
        int size = data.size();
        for (int i = 0; i < size; i++) {

            mContainerIndicator.addView(null);
        }
        // 给viewpager设置数据
        mViewPager.setAdapter(null);

    }
}
