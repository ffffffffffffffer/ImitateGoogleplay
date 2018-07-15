package com.googleplay.fragment.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.googleplay.R;
import com.googleplay.R2;
import com.googleplay.core.Constant;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.core.ui.image.GlideApp;
import com.googleplay.core.ui.image.GlideOption;
import com.googleplay.fragment.category.bean.CategoryBean;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/7/8 21:52
 * @des 分类页面的Holder
 */

public class CategoryItemNormalHolder extends BaseHolder<CategoryBean> {

    @BindView(R2.id.item_category_ll1)
    LinearLayout mLinearLayout1;
    @BindView(R2.id.item_category_ll2)
    LinearLayout mLinearLayout2;
    @BindView(R2.id.item_category_ll3)
    LinearLayout mLinearLayout3;
    @BindView(R2.id.item_category_img1)
    ImageView mImageView1;
    @BindView(R2.id.item_category_img2)
    ImageView mImageView2;
    @BindView(R2.id.item_category_img3)
    ImageView mImageView3;
    @BindView(R2.id.item_category_name1)
    TextView mTextView1;
    @BindView(R2.id.item_category_name2)
    TextView mTextView2;
    @BindView(R2.id.item_category_name3)
    TextView mTextView3;

    @Override
    protected Object initView() {
        return R.layout.item_category;
    }

    @Override
    protected void refreshUI(CategoryBean data) {
        mTextView1.setText(data.name1);
        mTextView2.setText(data.name2);
        mTextView3.setText(data.name3);
        // 如果服务器传回来的数据是空的,就隐藏对应的View
        display(mLinearLayout1,mImageView1,data.url1);
        display(mLinearLayout2,mImageView2,data.url2);
        display(mLinearLayout3,mImageView3,data.url3);
    }

    private void display(LinearLayout linearLayout, ImageView imageView, String url) {
        if (!StringUtils.isEmpty(url)) {
            GlideApp.with(GooglePlay.getApplicationContext())
                    .load(Constant.IMAGE + url)
                    .apply(GlideOption.OPTIONS)
                    .into(imageView);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.INVISIBLE);
        }
    }
}
