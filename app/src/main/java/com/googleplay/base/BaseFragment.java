package com.googleplay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.googleplay.fragment.load.LoadUI;

/**
 * @author TanJJ
 * @time 2018/6/23 9:06
 */

public abstract class BaseFragment extends Fragment {

    private LoadUI mLoadUI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        // 防止多次创建
        if (mLoadUI == null) {
            mLoadUI = new LoadUI(getContext()) {
                @Override
                public LoadState getLoadDate() {
                    return onStartLoadDate();
                }

                @Override
                public View getSuccessView() {
                    return onSuccessView();
                }
            };
        }
        return mLoadUI;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 加载数据
        mLoadUI.loadMore();
    }

    /**
     * 获取数据
     */
    public void loadMore() {
        if (mLoadUI != null) {
            mLoadUI.loadMore();
        }
    }

    /**
     * 加载数据时回调
     */
    public abstract LoadUI.LoadState onStartLoadDate();

    /**
     * 加载成功时回调
     */
    public abstract View onSuccessView();
}
