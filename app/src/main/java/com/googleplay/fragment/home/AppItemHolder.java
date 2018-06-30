package com.googleplay.fragment.home;

import android.widget.TextView;

import com.googleplay.R;
import com.googleplay.core.holder.BaseHolder;

/**
 * @author TanJJ
 * @time 2018/6/30 10:56
 * @des 主页Holder
 */

public class AppItemHolder extends BaseHolder<String> {
    @Override
    protected Object initView() {
        return R.layout.item_tmp;
    }

    @Override
    protected void refreshUI(String data) {
        TextView text1 = (TextView) mView.findViewById(R.id.tmp_tv_1);
        text1.setText(data);
    }
}
