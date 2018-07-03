package com.googleplay.core.holder;

import android.view.LayoutInflater;
import android.view.View;

import com.googleplay.core.app.GooglePlay;

import butterknife.ButterKnife;
/**
 * @author TanJJ
 * @time 2018/6/29 19:52
 * @des Holder基类
 */

public abstract class BaseHolder<T> {
    // 可以让实现类直接调用
    protected View mView;
    // 数据类型由提供者决定
    protected T mData;

    public BaseHolder() {
        // 在创建Holder时就要获取View
        mView = checkView();
        if (mView != null) {
            // 绑定View时,要使用两个参数的,不然会绑定不到
            ButterKnife.bind(this, mView);
        }
    }

    private View checkView() {
        Object object = initView();
        if (object instanceof Integer) {
            return LayoutInflater.from(GooglePlay.getApplicationContext()).inflate((int) object, null, false);
        } else if (object instanceof View) {
            return (View) object;
        } else if (object == null) {
            throw new RuntimeException(this.getClass().getSimpleName() + ".initView() is return null,please provide " +
                    "layout or layoutId");
        } else {
            return null;
        }
    }

    /**
     * 提供方法供外界调用
     */
    public View getRootView() {
        if (mView != null) {
            return mView;
        }
        return null;
    }

    /**
     * 让外界提供数据
     *
     * @param data 类型由提供者确定
     */
    public void setData(T data) {
        mData = data;
        refreshUI(data);
    }

    /**
     * 让实现类提供要显示的根View
     */
    protected abstract Object initView();

    /**
     * 由实现类去处理数据的显示
     */
    protected abstract void refreshUI(T data);

}
