package com.googleplay.fragment.recommend;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.googleplay.base.BaseFragment;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.view.ShakeListener;
import com.googleplay.core.view.StellarMap;
import com.googleplay.fragment.load.LoadUI;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author TanJJ
 * @time 2018/7/15 20:02
 * @des 推荐页面
 */

public class RecommendFragment extends BaseFragment {

    private RecommendProtocol mProtocol;
    private List<String> mDataList;

    private Random mRdm = new Random();
    private ShakeListener mShake;


    @Override
    public LoadUI.LoadState onStartLoadDate() {
        // 网络请求
        mProtocol = new RecommendProtocol();
        try {
            // 请求网络
            mDataList = mProtocol.execute(0, "recommend", false);
            if (mDataList == null || mDataList.size() == 0) {
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
        final StellarMap mRootView = new StellarMap(GooglePlay.getApplicationContext());

        // 设置分区
        mRootView.setRegularity(16, 16);

        // 设置padding
        mRootView.setInnerPadding(SizeUtils.dp2px(8), SizeUtils.dp2px(8), SizeUtils.dp2px(8), SizeUtils.dp2px(8));

        // 设置数据adapter
        mRootView.setAdapter(new RecommendAdapter());

        // 设置第一个页面显示
        mRootView.setGroup(0, true);

        mShake = new ShakeListener(GooglePlay.getApplicationContext());
        // 监听摇一摇
        mShake.setOnShakeListener(new ShakeListener.OnShakeListener() {

            @Override
            public void onShake() {
                int currentGroup = mRootView.getCurrentGroup();
                mRootView.setGroup(++currentGroup, true);
            }
        });

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mShake != null) {
            mShake.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mShake != null) {
            mShake.pause();
        }
    }

    private class RecommendAdapter implements StellarMap.Adapter {
        private int PAGER_SIZE = 15;

        // 有几个页面
        @Override
        public int getGroupCount() {
            if (mDataList != null) {
                int group = mDataList.size() / PAGER_SIZE;

                if (mDataList.size() % PAGER_SIZE > 0) {
                    group++;
                }
                return group;
            }

            return 0;
        }

        // 第group页面有几个数据
        @Override
        public int getCount(int group) {
            if (mDataList.size() % PAGER_SIZE > 0 && group == (getGroupCount() - 1)) {
                return mDataList.size() % PAGER_SIZE;
            }
            return PAGER_SIZE;
        }

        // 第group个页面中的第position个view是什么view
        @Override
        public View getView(int group, int position, View convertView) {
            int location = group * PAGER_SIZE + position;
            String text = mDataList.get(location);

            TextView view = new TextView(GooglePlay.getApplicationContext());
            view.setText(text);
            view.setTextSize(mRdm.nextInt(12) + 14);

            int red = mRdm.nextInt(200) + 20;
            int green = mRdm.nextInt(200) + 20;
            int blue = mRdm.nextInt(200) + 20;
            int color = Color.argb(255, red, green, blue);
            view.setTextColor(color);

            return view;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            // TODO Auto-generated method stub
            return 0;
        }

    }

}
