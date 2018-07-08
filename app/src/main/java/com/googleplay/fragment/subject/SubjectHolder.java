package com.googleplay.fragment.subject;

import android.widget.ImageView;
import android.widget.TextView;

import com.googleplay.R;
import com.googleplay.R2;
import com.googleplay.core.Constant;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.holder.BaseHolder;
import com.googleplay.core.ui.image.GlideApp;
import com.googleplay.core.ui.image.GlideOption;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/7/8 19:29
 * @des 专题的Holder
 */

public class SubjectHolder extends BaseHolder<SubjectBean> {

    @BindView(R2.id.item_subject_img)
    ImageView mSubjectImg;
    @BindView(R2.id.item_subject_des)
    TextView mSubjectDes;

    @Override
    protected Object initView() {
        return R.layout.item_subject;
    }

    @Override
    protected void refreshUI(SubjectBean data) {
        // 设置专题的des
        mSubjectDes.setText(data.des);
        // 设置专题的img
        GlideApp.with(GooglePlay.getApplicationContext())
                .load(Constant.IMAGE + data.url)
                .apply(GlideOption.OPTIONS)
                .into(mSubjectImg);
    }
}
