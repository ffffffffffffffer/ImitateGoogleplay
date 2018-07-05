package com.googleplay.core.holder;


import android.view.View;
import android.widget.RelativeLayout;

import com.googleplay.core.R;
import com.googleplay.core.R2;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/7/5 15:49
 * @des 加载更多的Holder
 */

public class LoadMoreHolder extends BaseHolder<Integer> {

    @BindView(R2.id.item_load_more)
    RelativeLayout mLoadMore;
    @BindView(R2.id.item_load_more_error)
    RelativeLayout mLoadMoreError;

    // 加载更多的状态
    public static final int LOADING = 0;
    public static final int LOAD_MORE_ERROR = 1;
    public static final int LOAD_NOT_MORE = 2;


    @Override
    protected Object initView() {
        return R.layout.item_load_more;
    }

    @Override
    protected void refreshUI(Integer state) {
        switch (state) {
            case LOADING:
                mLoadMore.setVisibility(View.VISIBLE);
                mLoadMoreError.setVisibility(View.GONE);
                break;
            case LOAD_MORE_ERROR:
                mLoadMore.setVisibility(View.GONE);
                mLoadMoreError.setVisibility(View.VISIBLE);
                break;
            case LOAD_NOT_MORE:
                mLoadMore.setVisibility(View.GONE);
                mLoadMoreError.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
