package com.googleplay.fragment.app;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.format.Formatter;

import com.googleplay.R;
import com.googleplay.R2;
import com.googleplay.core.Constant;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.core.ui.image.GlideApp;
import com.googleplay.core.ui.image.GlideOption;
import com.googleplay.fragment.home.bean.AppInfo;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/7/8 11:10
 * @des 应用界面Holder
 */

public class AppHolder extends BaseHolder<AppInfo> {
    @BindView(R2.id.item_img)
    AppCompatImageView mItemImg;
    @BindView(R2.id.item_app_title)
    AppCompatTextView mItemAppTitle;
    @BindView(R2.id.item_ratingbar_star)
    AppCompatRatingBar mRatingBar;
    @BindView(R2.id.item_app_size)
    AppCompatTextView mItemAppSize;
    @BindView(R2.id.item_download_ll)
    LinearLayoutCompat mDownloadLL;
    @BindView(R2.id.item_download_img)
    AppCompatImageView mDownloadImg;
    @BindView(R2.id.item_app_des)
    AppCompatTextView mItemAppDes;

    @Override
    protected Object initView() {
        return R.layout.item_app_info;
    }

    @Override
    protected void refreshUI(AppInfo data) {
        // 设置item内容数据
        mItemAppTitle.setText(data.name);
        mRatingBar.setRating(data.stars);
        mItemAppSize.setText(Formatter.formatFileSize(GooglePlay.getApplicationContext(), data.size));
        mItemAppDes.setText(data.des);
        // 设置app图片
        GlideApp.with(GooglePlay.getApplicationContext())
                .load(Constant.IMAGE + data.iconUrl)
                .apply(GlideOption.OPTIONS)
                .into(mItemImg);
    }
}
