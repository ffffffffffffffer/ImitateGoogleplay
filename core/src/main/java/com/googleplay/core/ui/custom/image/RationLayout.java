package com.googleplay.core.ui.custom.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.googleplay.core.R;

/**
 * @author TanJJ
 * @time 2018/7/8 20:11
 * @des 宽高比布局
 */

public class RationLayout extends FrameLayout {

    private static final int RELATIVE_WIDTH = 1;
    private static final int RELATIVE_HEIGHT = 2;

    private float mRatio;
    private int mRelative;

    public RationLayout(@NonNull Context context) {
        this(context, null);
    }

    public RationLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RationLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RationLayout);
        if (typedArray != null) {
            mRatio = typedArray.getFloat(R.styleable.RationLayout_ratio, 0);
            mRelative = typedArray.getInt(R.styleable.RationLayout_relative, 1);
            typedArray.recycle();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 获取宽高和mode
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 根据宽高比，宽度，计算高度,测量孩子
        if (widthMode == MeasureSpec.EXACTLY && mRelative != 0 && mRelative == RELATIVE_WIDTH) {

            // 根据width按照比例算出高度
            int width = widthSize - getPaddingLeft() - getPaddingRight();
            int height = (int) (width / mRatio + 0.5f);

            // 期望孩子的宽高
            measureChildren(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            );

            // 设置自己的高度
            setMeasuredDimension(widthSize, height + getPaddingTop() + getPaddingBottom());
        } else if (heightMode == MeasureSpec.EXACTLY && mRelative != 0 && mRelative == RELATIVE_HEIGHT) {
            // 根据width按照比例算出高度
            int height = heightSize - getPaddingTop() - getPaddingBottom();
            int width = (int) (height * mRatio + 0.5f);

            // 期望孩子的宽高
            measureChildren(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            );

            // 设置自己的高度
            setMeasuredDimension(widthSize + getPaddingLeft() + getPaddingRight(), height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
