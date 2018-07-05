package com.googleplay.core.holder;


import com.googleplay.core.R;

/**
 * @author TanJJ
 * @time 2018/7/5 15:49
 * @des 加载更多的Holder
 */

public class LoadMoreHolder extends BaseHolder<String> {
    @Override
    protected Object initView() {
        return R.layout.item_load_more;
    }

    @Override
    protected void refreshUI(String data) {

    }
}
