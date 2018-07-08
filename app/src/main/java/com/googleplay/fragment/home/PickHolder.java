package com.googleplay.fragment.home;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
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
import com.googleplay.core.util.ui.UIUtils;

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
    private AutoSwitchPicTask mSwitchPicTask;

    @Override
    protected Object initView() {
        return R.layout.item_home_picture;
    }

    @Override
    protected void refreshUI(List<String> data) {
        // 给容器添加点
        int size = addDotToLayout(data);
        // 给viewpager设置数据
        mViewPager.setAdapter(new PickViewPager(mData));
        // 设置viewpager选中中间页面
        changeSelected(size);
        // 监听ViewPager事件
        pageChangeListener();
        // 自动轮播,使用Handler来实现延时调用,也可以使用计数器
        autoSwitchPicTask();
        // 设置ViewPager的touch事件
        viewPagerTouch();
    }

    private void viewPagerTouch() {
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mSwitchPicTask.stop();
                        break;
                    case MotionEvent.ACTION_UP:
                        mSwitchPicTask.start();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mSwitchPicTask.start();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void autoSwitchPicTask() {
        if (mSwitchPicTask == null) {
            mSwitchPicTask = new AutoSwitchPicTask();
        }
        // 开始轮播
        mSwitchPicTask.start();
    }

    private void changeSelected(int size) {
        int middle = Integer.MAX_VALUE / 2;
        int extra = middle % size;
        int item = middle - extra;
        mViewPager.setCurrentItem(item);
    }

    private int addDotToLayout(List<String> data) {
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
        return size;
    }

    private void pageChangeListener() {
        // 监听ViewPager事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int childCount = mContainerIndicator.getChildCount();
                int realPosition = position % childCount;
                for (int i = 0; i < childCount; i++) {
                    View view = mContainerIndicator.getChildAt(i);
                    if (realPosition == i) {
                        view.setSelected(true);
                    } else {
                        view.setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class AutoSwitchPicTask implements Runnable {

        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            // 加一
            mViewPager.setCurrentItem(++currentItem);
            // 2秒后再次重新调用
            UIUtils.delayedTask(this, 2000);
        }

        public void start() {
            // 2秒后调用
            UIUtils.delayedTask(this, 2000);
        }

        public void stop() {
            // 移除任务
            UIUtils.removeTask(this);
        }
    }

    private class PickViewPager extends PagerAdapter {
        private final List<String> data;

        public PickViewPager(List<String> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 定义ViewPager的view
            ImageView imageView = new ImageView(GooglePlay.getApplicationContext());
            int size = data.size();
            GlideApp.with(GooglePlay.getApplicationContext())
                    .load(Constant.IMAGE + data.get(position % size))
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
