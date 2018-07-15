package com.googleplay.fragment.category;

import android.graphics.Color;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.fragment.category.bean.CategoryBean;

/**
 * @author TanJJ
 * @time 2018/7/9 8:35
 * @des 分类标题Holder
 */

public class CategoryItemTitleHolder extends BaseHolder<CategoryBean> {

    private TextView mTextView;

    @Override
    protected Object initView() {
        mTextView = new TextView(GooglePlay.getApplicationContext());
        mTextView.setTextColor(Color.BLACK);
        mTextView.setTextSize(22);
        mTextView.setPadding(SizeUtils.px2dp(8), SizeUtils.px2dp(8), SizeUtils.px2dp(8), SizeUtils.px2dp(8));
        mTextView.setBackgroundColor(Color.WHITE);
        return mTextView;
    }

    @Override
    protected void refreshUI(CategoryBean data) {
        mTextView.setText(data.title);
    }
}
