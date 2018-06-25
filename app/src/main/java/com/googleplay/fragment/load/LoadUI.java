package com.googleplay.fragment.load;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.googleplay.R;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.util.string.StringUtils;

/**
 * @author TanJJ
 * @time 2018/6/24 10:15
 * @des 自定义Load加载
 */

public abstract class LoadUI extends RelativeLayout {

    private ImageView mEmptyImageView;
    private ImageView mErrorImageView;
    private ProgressBar mLoadProgressBar;
    // 设置默认状态
    private int mLoadState = STATE_NONE;
    // 定义View的状态
    private static final int STATE_NONE = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_EMPTY = 3;
    private static final int STATE_SUCCESS = 4;


    public LoadUI(Context context) {
        this(context, null);
    }

    public LoadUI(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadUI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化View
        initView();
    }

    private void initView() {
        // 定义空数据Image/错误Image
        if (mEmptyImageView == null) {
            mEmptyImageView = new ImageView(getContext());
            mEmptyImageView.setImageResource(R.drawable.ic_empty_page);
        }
        if (mErrorImageView == null) {
            mErrorImageView = new ImageView(getContext());
            mErrorImageView.setImageResource(R.drawable.ic_error_page);
        }
        // 加载中的View
        if (mLoadProgressBar == null) {
            mLoadProgressBar = new ProgressBar(getContext());
            mLoadProgressBar.setIndeterminateDrawable(StringUtils.getResources().getDrawable(R.drawable
                    .rotate_load_ui));
        }
        // 居中显示
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams
                .WRAP_CONTENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        // 添加ImageView到RelativeLayout里
        addView(mEmptyImageView, layoutParams);
        addView(mErrorImageView, layoutParams);
        addView(mLoadProgressBar, layoutParams);
        // 根据状态更改UI
        updateUI();
    }

    private void updateUI() {
        // 更新View的显示
        updateState();
    }

    /**
     * 可以在子线程中直接调用更新UI
     */
    private void safeUpdateUi() {
        GooglePlay.getHandler().post(new Runnable() {
            @Override
            public void run() {
                updateUI();
            }
        });
    }


    private void updateState() {
        // 根据state改变View的显式
        mLoadProgressBar.setVisibility(mLoadState == STATE_NONE || mLoadState == STATE_LOADING ? VISIBLE : GONE);
        mErrorImageView.setVisibility(mLoadState == STATE_ERROR ? VISIBLE : GONE);
        mEmptyImageView.setVisibility(mLoadState == STATE_EMPTY ? VISIBLE : GONE);
    }

    /**
     * 提供方法让外界调用获取数据
     */
    public void loadMore() {
        // 如果state是成功或者加载中不往下走
        if (mLoadState == STATE_LOADING || mLoadState == STATE_SUCCESS) {
            return;
        }

        // 更新state状态
        mLoadState = STATE_LOADING;

        // 开启子线程操作加载
        new Thread(new LoadDataTask()).start();
    }

    private class LoadDataTask implements Runnable {

        @Override
        public void run() {
            // 获取状态,让知道UI显示的类来实现
            mLoadState = getLoadDate().getState();

            // 更新UI
            safeUpdateUi();
        }
    }

    public enum LoadState {
        NONE(STATE_NONE),
        LOADING(STATE_LOADING),
        ERROR(STATE_ERROR),
        EMPTY(STATE_EMPTY),
        SUCCESS(STATE_SUCCESS);
        private int state;

        LoadState(int state) {
            this.state = state;
        }

        private int getState() {
            return state;
        }
    }

    public abstract LoadState getLoadDate();

}
