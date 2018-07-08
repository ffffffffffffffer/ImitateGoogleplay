package com.googleplay.fragment.home;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.googleplay.R;
import com.googleplay.R2;
import com.googleplay.core.Constant;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.core.ui.image.GlideApp;
import com.googleplay.core.ui.image.GlideOption;
import com.googleplay.core.util.string.StringUtils;

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
            ImageView imageView = new ImageView(GooglePlay.getApplicationContext());
            imageView.setImageDrawable(StringUtils.getResources().getDrawable(R.drawable.item_dot));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(SizeUtils.px2dp(25), SizeUtils
                    .px2dp(25));
            if (i == 0) {
                // 设置选中状态
                imageView.setSelected(true);
            } else {
                layoutParams.leftMargin = SizeUtils.px2dp(15);
                layoutParams.bottomMargin = SizeUtils.px2dp(15);
            }
            mContainerIndicator.addView(imageView, layoutParams);
        }
        // 给viewpager设置数据
        mViewPager.setAdapter(new PickViewPager(mData));
        // 监听ViewPager事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int childCount = mContainerIndicator.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = mContainerIndicator.getChildAt(i);
                    if (position == i) {
                        view.setSelected(true);
                    }else {
                        view.setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class PickViewPager extends PagerAdapter {
        private final List<String> data;

        public PickViewPager(List<String> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 定义ViewPager的view
            ImageView imageView = new ImageView(GooglePlay.getApplicationContext());
            GlideApp.with(GooglePlay.getApplicationContext())
                    .load(Constant.IMAGE + data.get(position))
                    .apply(GlideOption.OPTIONS)
                    .into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
